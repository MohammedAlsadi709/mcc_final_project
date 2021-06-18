package com.alsadymoh.mccfinalproject.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alsadymoh.mccfinalproject.R
import com.alsadymoh.mccfinalproject.model.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_settings.view.*
import kotlinx.android.synthetic.main.fragment_view_report.view.*
import kotlinx.android.synthetic.main.fragment_view_report.view.titleOfPage


class ViewReportFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_view_report, container, false)


        val mySaherd = requireActivity().getSharedPreferences("myShared", Context.MODE_PRIVATE)
        root.titleOfPage.textSize = mySaherd.getFloat("titleSize",24f)
        root.txtDetailsOfPage.textSize = mySaherd.getFloat("reportSize",15f)



        val b = arguments
        if (b != null) {
            root.titleOfPage.text = b.getString("title")
            root.txtDetailsOfPage.text = b.getString("details")
            Glide.with(activity).load(b.getString("image")).into(root.imgViewReport)
            val date = b.getString("date")
            if (date == "aaa") {
                root.layoutDate.visibility = View.GONE
            } else {
                root.layoutDate.visibility = View.VISIBLE
                root.txtDate.text = date
            }

            if ( b.getInt("category_id")==10){
                root.titleOfPage.text = Constants.txtAbout
                root.txtDetailsOfPage.text = Constants.detailsAbout
                root.layoutDate.visibility = View.GONE
                Glide.with(activity).load(R.drawable.aa9).into(root.imgViewReport)
            }

        }
        root.btnShare.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            val title = "مشتركة الموضوع"
            val body = root.txtDetailsOfPage.text.toString()
            i.putExtra(Intent.EXTRA_SUBJECT,title)
            i.putExtra(Intent.EXTRA_TEXT,body)
            startActivity(Intent.createChooser(i,"مشاركة بإستخدام "))
        }
        return root
    }
}