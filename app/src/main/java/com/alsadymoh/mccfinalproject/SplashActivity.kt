package com.alsadymoh.mccfinalproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.alsadymoh.mccfinalproject.fragments.SplashFragment
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val mySaherd = getSharedPreferences("myShared", Context.MODE_PRIVATE)
        val editor = mySaherd.edit()

        setTheme(mySaherd.getInt("fontType",R.style.Theme_MCCFinalProject_NoActionBar))

        if (mySaherd.getBoolean("isNightMode",false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }



        val isFirstTime = mySaherd.getBoolean("isFirstTime",true)
        if (isFirstTime) {

            Thread{
                Thread.sleep(2000)
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.container_layout,SplashFragment()).commit()

            }.start()

            editor.putFloat("reportSize", 15f)
            editor.putFloat("titleSize", 24f)
            editor.putBoolean("isFirstTime", false)
            editor.apply()

        }else{
            Thread{
                Thread.sleep(3000)
                val i =Intent(this,Sign_in_up_Activity::class.java)
                startActivity(i)
                finish()
            }.start()
        }

    }
}