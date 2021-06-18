package com.alsadymoh.mccfinalproject

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MySingleton :Application() {

    val TAG = "moh"
    private var mRequestQueue : RequestQueue? =null

    companion object{
        private var mInstance : MySingleton? = null

        fun getInstance() : MySingleton?{
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance =this
    }

    private fun getRequestQueue(): RequestQueue?{

        if (mRequestQueue==null){
            mRequestQueue = Volley.newRequestQueue(this)
        }
        return mRequestQueue
    }

    fun <T> addRequestQueue(r:Request<T>){
        r.tag = TAG
        getRequestQueue()!!.add(r)
    }

    fun <T> addRequestQueue(r:Request<T>,tag : String?){

        if (TextUtils.isEmpty(tag)){
            r.tag = TAG
        }else{
            r.tag = tag
        }

        getRequestQueue()!!.add(r)
    }


    fun cancelPendingRequest(tag: Any?){
        if(mRequestQueue!=null){
            mRequestQueue!!.cancelAll(tag)
        }
    }

}