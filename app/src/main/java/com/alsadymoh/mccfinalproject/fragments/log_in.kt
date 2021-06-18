package  com.alsadymoh.mccfinalproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alsadymoh.mccfinalproject.MainActivity
import com.alsadymoh.mccfinalproject.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_log_in.view.*


class log_in : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_log_in, container, false)

        val auth = Firebase.auth


        root.btnLog.setOnClickListener {



            if (root.txtEmail.text.isNotEmpty()&&root.txtPassword.text.isNotEmpty()){
                val email = root.txtEmail.text.toString()
                val password = root.txtPassword.text.toString()

                if (password.length>= 6){
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(requireActivity()){task ->

                        if (task.isSuccessful){
                            Toast.makeText(activity,"تم التسجيل بنجاح", Toast.LENGTH_LONG).show()

                            startActivity(Intent(activity, MainActivity::class.java))
                            activity!!.finish()

                        }else{
                            Toast.makeText(activity,"لم يتم التسجيل بنجاح", Toast.LENGTH_LONG).show()
                        }

                    }

                }else{

                    Toast.makeText(activity,"يجب ادخال كلمة مرور تتكون من 6 احرف او اكثر", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(activity,"يجب التأكد من ادخال كلا من البريد وكلمة المرور", Toast.LENGTH_LONG).show()
            }


        }


        root.dontHave.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frameSign,
                sign_up()
            ).commit()
        }
        return root
    }

}