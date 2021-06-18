package com.alsadymoh.mccfinalproject.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alsadymoh.mccfinalproject.R
import com.alsadymoh.mccfinalproject.model.Constants
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_add_now_report.*
import kotlinx.android.synthetic.main.fragment_add_now_report.view.*
import java.io.ByteArrayOutputStream


class AddNowReportFragment : Fragment() {

    var p : ProgressDialog?=null

    lateinit var db: FirebaseFirestore
    private val rq = 100
    private var URI :Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_now_report, container, false)

        val mySaherd = requireActivity().getSharedPreferences("myShared", Context.MODE_PRIVATE)
        root.titleOfPage.textSize = mySaherd.getFloat("titleSize",24f)


        db = Firebase.firestore

        val storage = Firebase.storage
        val storageRef = storage.reference
        val folderRef =  storageRef.child("images")

        root.btnChooseImage.setOnClickListener {
        val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent,rq)
        }

        root.btnAddReport.setOnClickListener {
            if(root.txtTitleAdd.text.isNotEmpty()){
                if (root.txtDetailsAdd.text.isNotEmpty()){
                    val category = root.spCategory.selectedItem.toString()
                    if (category == Constants.txtChooseCategory){
                        Toast.makeText(activity,"يرجى اختيار التصنيف",Toast.LENGTH_SHORT).show()
                    }else{
                        if (root.cardImage.visibility == View.VISIBLE){


                              p = ProgressDialog(activity)
                            p!!.setTitle("اضافة الموضوع")
                             p!!.setMessage("يعتمد اضافة الموضوع على سرعة الانترنت في الرفع وحجم الصورة لذا من الممكن أن يأخذ بعض الوقت")
                              p!!.show()

                            Thread {
                                val bitmap = (root.img1.drawable as BitmapDrawable).bitmap
                                val baos = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                                val data = baos.toByteArray()

                                val imageRef = folderRef.child(System.currentTimeMillis().toString() + "Image.png")
                                val uploadTask = imageRef.putBytes(data)
                                uploadTask.addOnFailureListener { exception ->
                                    root.cardImage.visibility = View.GONE
                                    p!!.cancel()
                                    Toast.makeText(activity, "فشل رفع الصورة", Toast.LENGTH_LONG).show()
                                    Log.e("moh", "faild to upload image")
                                }.addOnSuccessListener {

                                    Log.e("moh", "upload Image Successfully")

                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                        val txtTitle = root.txtTitleAdd.text.toString()
                                        val txtDetails = root.txtDetailsAdd.text.toString()
                                        val category = root.spCategory.selectedItem.toString()
                                        val URL = uri.toString()

                                        addReportsToFirebase(txtTitle,txtDetails,category,URL)
                                    }
                                }
                            }.start()

                        }else{
                            Toast.makeText(activity,"اختيار الصورة",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(activity,"يرجى ادخال الموضوع",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activity,"يرجى ادخال عنوان الموضوع",Toast.LENGTH_SHORT).show()
            }

        }


        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == rq){
            btnChooseImage.setImageResource(R.drawable.uploaded)
            URI= data!!.data
            cardImage.visibility = View.VISIBLE
            img1.setImageURI(URI)
        }
    }


    private fun addReportsToFirebase( cId: String, title: String, details: String, imageURL: String) {
        val document = hashMapOf(
                "cId" to cId,
                "title" to title,
                "details" to details,
                "imageURL" to imageURL
        )
        db.collection("reports")
                .add(document)
                .addOnSuccessListener { documentReference ->
                    cardImage.visibility = View.GONE
                    txtTitleAdd.text.clear()
                    txtDetailsAdd.text.clear()

                    p!!.cancel()
                    Toast.makeText(requireActivity(), "تمت الاضافة", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { exception ->
                    Toast.makeText(
                            requireActivity(),
                            "فشل في الاضافة",
                            Toast.LENGTH_SHORT
                    ).show()
                    p!!.cancel()
                    cardImage.visibility = View.GONE
                }
    }
}