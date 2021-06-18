package com.alsadymoh.mccfinalproject.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewSwitcher
import com.alsadymoh.mccfinalproject.MainActivity
import com.alsadymoh.mccfinalproject.R
import com.alsadymoh.mccfinalproject.Sign_in_up_Activity
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment : Fragment() {

    private val images = arrayOf(R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4)
    private val numberOfImages = images.size
    var currentImage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_splash, container, false)

        val inAnimation = AnimationUtils.loadAnimation(activity, R.anim.enter_from_right)
        val outAnimation = AnimationUtils.loadAnimation(activity, R.anim.exit_to_left)

        root.imageSwitcher.setFactory {
            val imageView = ImageView(activity)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            imageView
        }

        root.imageSwitcher.inAnimation = inAnimation
        root.imageSwitcher.outAnimation = outAnimation
        root.imageSwitcher.setImageResource(images[currentImage])

        root.btnSwitchImage.setOnClickListener {
            if (currentImage != numberOfImages-1) {
                currentImage++
                root.imageSwitcher.setImageResource(images[currentImage])
                if (currentImage == numberOfImages-1){
                    root.btnSwitchImage.text = "ابدأ"
                }
            } else {

                startActivity(Intent(activity, Sign_in_up_Activity::class.java))
                requireActivity().finish()
            }
        }

        return root
    }
}