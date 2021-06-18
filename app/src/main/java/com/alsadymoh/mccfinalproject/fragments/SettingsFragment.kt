package com.alsadymoh.mccfinalproject.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.alsadymoh.mccfinalproject.R
import com.alsadymoh.mccfinalproject.Sign_in_up_Activity
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_settings, container, false)



        val mySaherd = requireActivity().getSharedPreferences("myShared", Context.MODE_PRIVATE)
        val editor = mySaherd.edit()


        root.titleOfPage.textSize = mySaherd.getFloat("titleSize",24f)



            if (mySaherd.getBoolean("isNightMode",false)){
            root.switchNightMode.isChecked = true
        }

        root.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                turnOnNightMode()
                editor.putBoolean("isNightMode",true)
                editor.apply()
            }else{
                turnOffNightMode()
                editor.putBoolean("isNightMode",false)
                editor.apply()
            }
        }


        //edit Title Size
        root.btnEditTitleSize.setOnClickListener {
            if (root.layoutTitleSize.visibility ==View.VISIBLE){
                root.layoutTitleSize.visibility =View.GONE
            }else{
                root.layoutTitleSize.visibility =View.VISIBLE
            }
        }

        root.btnCancelTitleSize.setOnClickListener {
            root.layoutTitleSize.visibility =View.GONE
        }

        var choice = 0f
        root.rgTitleSizes.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                root.rbSmall.id ->{
                    root.txtTestTitleSize.textSize = 16f
                    choice = 16f
                }
                root.rbNormal.id ->{
                    root.txtTestTitleSize.textSize = 24f
                    choice = 24f
                }
                root.rbBig.id ->{
                    root.txtTestTitleSize.textSize = 30f
                    choice = 30f
                }
            }
        }
        root.btnSaveTitleSize.setOnClickListener {
            if (choice == 0f){
                Toast.makeText(activity,"يرجى اختيار الحجم قبل الحفظ",Toast.LENGTH_SHORT).show()
            }else{
                editor.putFloat("titleSize",choice)
                editor.apply()
                root.layoutTitleSize.visibility =View.GONE
            }
        }



        //edit report Size

        root.btnEditReportSize.setOnClickListener {
            if (root.layoutReportSize.visibility ==View.VISIBLE){
                root.layoutReportSize.visibility =View.GONE
            }else{
                root.layoutReportSize.visibility =View.VISIBLE
            }
        }

        root.btnCancelReportSize.setOnClickListener {
            root.layoutReportSize.visibility =View.GONE
        }

        var choiceReport = 0f
        root.rgReportSizes.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                root.rbSmallReport.id ->{
                    root.txtTestReportSize.textSize = 12f
                    choiceReport = 12f
                }
                root.rbNormalReport.id ->{
                    root.txtTestReportSize.textSize = 15f
                    choiceReport = 15f
                }
                root.rbBigReport.id ->{
                    root.txtTestReportSize.textSize = 19f
                    choiceReport = 19f
                }
            }
        }
        root.btnSaveReportSize.setOnClickListener {
            if (choiceReport == 0f){
                Toast.makeText(activity,"يرجى اختيار الحجم قبل الحفظ",Toast.LENGTH_SHORT).show()
            }else{
                editor.putFloat("reportSize",choiceReport)
                editor.apply()
                root.layoutReportSize.visibility =View.GONE
            }
        }

        //edit font type


        root.btnEditFontType.setOnClickListener {
            if (root.layoutFontType.visibility ==View.VISIBLE){
                root.layoutFontType.visibility =View.GONE
            }else{
                root.layoutFontType.visibility =View.VISIBLE
            }
        }

        root.btnCancelFontType.setOnClickListener {
            root.layoutFontType.visibility =View.GONE
        }


        var font = R.style.Theme_MCCFinalProject_NoActionBar

        root.rgFontTypes.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                root.rgFont1.id ->{
                    font = R.style.Theme_MCCFinalProject_NoActionBar
                }
                root.rgFont2.id ->{
                    font = R.style.font1
                }
                root.rgFont3.id ->{
                    font = R.style.font2
                }
                root.rgFont4.id ->{
                    font = R.style.font3
                }
            }
        }

        root.btnSaveFontType.setOnClickListener {

                editor.putInt("fontType",font)
                editor.apply()
                root.layoutFontType.visibility =View.GONE



            val alert = AlertDialog.Builder(activity!!)
            alert.setMessage("تم تغيير نوع الخط لكن سيتم تطبيق التغييرات بعد تسجيل الخروج")
            alert.setTitle("تغيير نوع الخط")
            alert.setCancelable(false)

            alert.setPositiveButton("--> تسجيل الخروج") { _, _ ->
                startActivity(Intent(activity,Sign_in_up_Activity::class.java))
                activity!!.finish()
            }


            alert.setNeutralButton("--> تخطي") { _, _ ->
                Toast.makeText(activity, "لن يتم تطبيق التغيير إلا في حال اعادة التشغيل", Toast.LENGTH_SHORT).show()
            }

              alert.create().show()


        }



        return root
    }
  private  fun turnOnNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    private  fun turnOffNightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}