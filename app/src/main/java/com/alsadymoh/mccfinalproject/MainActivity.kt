package com.alsadymoh.mccfinalproject

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.alsadymoh.mccfinalproject.fragments.*
import com.alsadymoh.mccfinalproject.model.Constants
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mySaherd = getSharedPreferences("myShared", Context.MODE_PRIVATE)
        setTheme(mySaherd.getInt("fontType",R.style.Theme_MCCFinalProject_NoActionBar))

        supportFragmentManager.beginTransaction().replace(R.id.container_layout,ListFragment()).commit()



        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        drower_lay1.setNavigationItemSelectedListener(this)

    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val b = Bundle()
        val list = ListFragment()
        when (item.itemId) {
            R.id.videos->{
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,VideoPlayFragment()).addToBackStack("null").commit()

            }

            R.id.news -> {
                b.putInt("category_id",Constants.newsCategory)
                list.arguments = b
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,list).addToBackStack("null").commit()
            }

            R.id.generalView -> {
                b.putInt("category_id",Constants.generalViewCategory)
                list.arguments = b
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,list).addToBackStack("null").commit()
            }

            R.id.religiousStatus -> {
                b.putInt("category_id",Constants.religiousStatusCategory)
                list.arguments = b
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,list).addToBackStack("null").commit()
            }

            R.id.history -> {
                b.putInt("category_id",Constants.historyCategory)
                list.arguments = b
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,list).addToBackStack("null").commit()
            }

            R.id.landmarks -> {
                b.putInt("category_id",Constants.landmarksCategory)
                list.arguments = b
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,list).addToBackStack("null").commit()
            }

            R.id.gates -> {
                b.putInt("category_id",Constants.gateCategory)
                list.arguments = b
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,list).addToBackStack("null").commit()
            }

            R.id.addReport -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,AddNowReportFragment()).addToBackStack("null").commit()
            }

            R.id.share -> {
                val i = Intent(Intent.ACTION_SEND)
                i.setType("text/plain")
                val title = "مشتركة التطبيق"
                val body = "تطبيق دليل مدينة القدس "
                i.putExtra(Intent.EXTRA_SUBJECT,title)
                i.putExtra(Intent.EXTRA_TEXT,body)
                startActivity(Intent.createChooser(i,"مشاركة بإستخدام "))
            }

            R.id.about -> {
                val viewReportFragment = ViewReportFragment()
                b.putInt("category_id",10)
                viewReportFragment.arguments = b
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,viewReportFragment).addToBackStack("null").commit()
            }
            R.id.settings -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.container_layout,SettingsFragment()).addToBackStack("null").commit()

            }
            R.id.close -> {
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}