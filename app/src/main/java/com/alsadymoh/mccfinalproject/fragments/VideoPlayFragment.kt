package com.alsadymoh.mccfinalproject.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alsadymoh.mccfinalproject.R
import com.alsadymoh.mccfinalproject.model.VideoModel
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.fragment_video_play.view.*


class VideoPlayFragment : Fragment() {

    var playerView: PlayerView? = null
    var player: SimpleExoPlayer? = null

    var playWhenReady = true
    var currentWindow = 0
    var playpackPosition: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_video_play, container, false)
        playerView = root.videoView

        val videos = mutableListOf<VideoModel>()
        videos.add(
            VideoModel(
                "شاهد أبواب القدس",
                "https://firebasestorage.googleapis.com/v0/b/mohammed123-d6e07.appspot.com/o/vidoes%2F%D8%A3%D8%A8%D9%88%D8%A7%D8%A8%20%D8%A7%D9%84%D9%82%D8%AF%D8%B3%20%D8%A7%D9%84%D8%B9%D8%A7%D9%85%D9%88%D8%AF%20%D9%88%D8%A7%D9%84%D9%86%D8%A8%D9%8A%20%D8%AF%D8%A7%D9%88%D8%AF%20%D9%88%D8%A7%D9%84%D8%B1%D8%AD%D9%85%D8%A9.mp4?alt=media&token=2eab858e-ddf4-4468-9f3e-d67237159062"
            )
        )
        videos.add(
            VideoModel(
                "شاهد تاريخ القدس",
                "https://firebasestorage.googleapis.com/v0/b/mohammed123-d6e07.appspot.com/o/vidoes%2F%D8%AA%D8%A7%D8%B1%D9%8A%D8%AE%20%D8%A7%D9%84%D9%82%D8%AF%D8%B3.mp4?alt=media&token=979addde-4506-4c3e-a363-ef1af98bb926"
            )
        )
        videos.add(
            VideoModel(
                "شاهد جمال القدس في دقيقة",
                "https://firebasestorage.googleapis.com/v0/b/mohammed123-d6e07.appspot.com/o/vidoes%2F%D8%AC%D9%85%D8%A7%D9%84%20%D8%A7%D9%84%D9%82%D8%AF%D8%B3.mp4?alt=media&token=c128c8a6-9646-472c-895b-e8b212a9b1f4"
            )
        )
        videos.add(
            VideoModel(
                "جمال خيالي لمدينة القدس",
                "https://firebasestorage.googleapis.com/v0/b/mohammed123-d6e07.appspot.com/o/vidoes%2F%D8%AC%D9%85%D8%A7%D9%84%20%D8%AE%D9%8A%D8%A7%D9%84%D9%8A%20%D9%84%D9%84%D9%82%D8%AF%D8%B3.mp4?alt=media&token=feb2704d-83a9-43c1-9648-9671ad9624ed"
            )
        )
        videos.add(
            VideoModel(
                "جولة رائعة في القدس",
                "https://firebasestorage.googleapis.com/v0/b/mohammed123-d6e07.appspot.com/o/vidoes%2F%D8%AC%D9%88%D9%84%D8%A9%20-%20%D8%AC%D9%85%D8%A7%D9%84%20%D9%85%D8%AF%D9%8A%D9%86%D8%A9%20%D8%A7%D9%84%D9%82%D8%AF%D8%B3.mp4?alt=media&token=2f73216a-2d98-4308-a010-f26eeaea86de"
            )
        )
        videos.add(
            VideoModel(
                "شاهد نظرة عامة عن القدس",
                "https://firebasestorage.googleapis.com/v0/b/mohammed123-d6e07.appspot.com/o/vidoes%2F%D9%86%D8%B8%D8%B1%D8%A9%20%D8%B9%D8%A7%D9%85%D8%A9%20%D8%B9%D9%86%20%D9%85%D8%AF%D9%8A%D9%86%D8%A9%20%D8%A7%D9%84%D9%82%D8%AF%D8%B3.mp4?alt=media&token=fb58c911-91e4-42f8-bf6e-71d8a8dc4b96"
            )
        )

        var counter = 0

        root.txtVideoName.text = videos[counter].name

        initVideo(videos[counter].URL)

        if (counter ==0){
            root.btnBack.visibility = View.GONE
        }else{
            root.btnBack.visibility = View.VISIBLE
        }
        if (counter ==videos.size-1){
            root.btnNext.visibility = View.GONE
        }else{
            root.btnNext.visibility = View.VISIBLE
        }

        root.btnNext.setOnClickListener {
             counter++
            root.txtVideoName.text = videos[counter].name
            stopVideo()
            initVideo(videos[counter].URL)
            if (counter ==0){
                root.btnBack.visibility = View.GONE
            }else{
                root.btnBack.visibility = View.VISIBLE
            }
            if (counter ==videos.size-1){
                root.btnNext.visibility = View.GONE
            }else{
                root.btnNext.visibility = View.VISIBLE
            }
        }
        root.btnBack.setOnClickListener {
            counter--
            root.txtVideoName.text = videos[counter].name
            stopVideo()
            initVideo(videos[counter].URL)
            if (counter ==0){
                root.btnBack.visibility = View.GONE
            }else{
                root.btnBack.visibility = View.VISIBLE
            }
            if (counter ==videos.size-1){
                root.btnNext.visibility = View.GONE
            }else{
                root.btnNext.visibility = View.VISIBLE
            }
        }


        return root
    }

    fun initVideo(L: String) {
        //player
        player = ExoPlayerFactory.newSimpleInstance(activity)

        //connect Player with player view
        playerView!!.player = player

        // media source
        val uri = Uri.parse(L)
        val dataSourceFactory = DefaultDataSourceFactory(activity, "exoplayer-codelab")
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)

        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playpackPosition)
        player!!.prepare(mediaSource, false, false)

    }

    fun stopVideo() {
        player!!.stop()
    }


    override fun onPause() {
        super.onPause()
        stopVideo()
    }


    override fun onStop() {
        super.onStop()
        stopVideo()
    }
}