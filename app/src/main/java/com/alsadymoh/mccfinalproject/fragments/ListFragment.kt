package com.alsadymoh.mccfinalproject.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alsadymoh.mccfinalproject.MySingleton
import com.alsadymoh.mccfinalproject.R
import com.alsadymoh.mccfinalproject.adapter.Adapter
import com.alsadymoh.mccfinalproject.model.Constants
import com.alsadymoh.mccfinalproject.model.Model
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ListFragment : Fragment() {

    var p: ProgressDialog? = null
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)

        val mySaherd = requireActivity().getSharedPreferences("myShared", Context.MODE_PRIVATE)
        root.titleOfPage.textSize = mySaherd.getFloat("titleSize",24f)



        p = ProgressDialog(activity)
        p!!.setTitle("جار جلب البيانات")
        p!!.setMessage("جاري جلب البيانات لن يستغرق الكثير من الوقت")
        p!!.show()

        db = Firebase.firestore

        val b = arguments
        val categoryId = b?.getInt("category_id") ?: Constants.newsCategory

        var category = Constants.txtNews
        when (categoryId) {
            Constants.newsCategory -> category = Constants.txtNews
            Constants.landmarksCategory -> category = Constants.landmarks
            Constants.gateCategory -> category = Constants.txtGate
            Constants.generalViewCategory -> category = Constants.txtGeneralView
            Constants.historyCategory -> category = Constants.txtHistory
            Constants.religiousStatusCategory -> category = Constants.txtStatus
        }

        root.titleOfPage.text = category

        if (categoryId != 0) {
            getFromFirebase(category)
        } else {

            val x = SimpleDateFormat("yyyy-MM-dd", Locale.US)

            val dateOfNow = x.format(Date())

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -2)

           val dateOf2DaysAgo = x.format(calendar.time)

            Log.e("moh", dateOfNow.toString())
            Log.e("moh", dateOf2DaysAgo.toString())


            val newsAPI = "https://newsapi.org/v2/everything?q=%D8%A7%D9%84%D9%82%D8%AF%D8%B3&from=$dateOf2DaysAgo&to=$dateOfNow&sortBy=popularity&apiKey=b8b9e7d567e14c72800bc36c7eda6049"

            getFromAPI(newsAPI)
        }


        return root
    }

    private fun getFromAPI(url: String) {
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
                url,
            null,
            Response.Listener { response ->
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<Model>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)

                    val id = "$i"
                    val cId = "0"
                    val title = newsJsonObject.getString("title")
                    val details = newsJsonObject.getString("description")
                    val image = newsJsonObject.getString("urlToImage")
                    val publishedAt = newsJsonObject.getString("publishedAt").replace("T", " || ").replace("Z", " ")

                    newsArray.add(Model(id,cId,title,details,image,publishedAt))
                }

                val adapter = Adapter(activity!!, newsArray)
                RV1.adapter = adapter
                RV1.layoutManager = LinearLayoutManager(requireActivity())
                adapter.onItemClick = {model ->
                   val  viewReportFragment = ViewReportFragment()
                    val b= Bundle()
                    b.putString("title",model.title)
                    b.putString("details",model.description)
                    b.putString("date",model.dateOfPublish)
                    b.putString("image",model.image)
                    viewReportFragment.arguments = b
                    requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,viewReportFragment).addToBackStack("null").commit()
                }


                p!!.cancel()

            },
            Response.ErrorListener { error ->
                Log.e("moh",error.message.toString())
                p!!.cancel()
            }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance()!!.addRequestQueue(jsonObjectRequest)
    }

    private fun getFromFirebase(cId: String) {
        val data = mutableListOf<Model>()
        db.collection("reports").whereEqualTo("cId", cId).get()
            .addOnSuccessListener { querySnapshot ->
                for (doc in querySnapshot) {
                    val title = doc.getString("title")
                    val details = doc.getString("details")
                    val imageURL = doc.getString("imageURL")
                    data.add(Model(doc.id, cId, title!!, details!!, imageURL!!))
                }

                val adapter = Adapter(activity!!, data)
                RV1.adapter = adapter
                RV1.layoutManager = LinearLayoutManager(requireActivity())
                adapter.onItemClick = {model ->
                    val  viewReportFragment = ViewReportFragment()
                    val b= Bundle()
                    b.putString("title",model.title)
                    b.putString("details",model.description)
                    b.putString("image",model.image)
                    b.putString("date",model.dateOfPublish)
                    viewReportFragment.arguments = b
                    requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,viewReportFragment).addToBackStack("null").commit()
                }

                p!!.cancel()
            }.addOnFailureListener { exception ->
            Log.e("moh", exception.message.toString())
            p!!.cancel()
        }
    }
}