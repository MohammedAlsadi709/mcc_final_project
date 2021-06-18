package com.alsadymoh.mccfinalproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alsadymoh.mccfinalproject.fragments.log_in

class Sign_in_up_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_up_)
        supportFragmentManager.beginTransaction().replace(R.id.frameSign, log_in()).commit()

        val mySaherd = getSharedPreferences("myShared", Context.MODE_PRIVATE)
        setTheme(mySaherd.getInt("fontType",R.style.Theme_MCCFinalProject_NoActionBar))
    }
}