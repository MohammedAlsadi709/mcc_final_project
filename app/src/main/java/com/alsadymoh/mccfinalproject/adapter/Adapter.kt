package com.alsadymoh.mccfinalproject.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alsadymoh.mccfinalproject.R
import com.alsadymoh.mccfinalproject.model.Model
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_item.view.*

class Adapter(var activity: Activity, var data:MutableList<Model>) : RecyclerView.Adapter<Adapter.MyViewHolder>(){

    var onItemClick : ((Model)->Unit)? = null

    class MyViewHolder (root: View) : RecyclerView.ViewHolder(root){
       var txtTitle = root.txtTitle
        var txtDetails = root.txtDetails
        var itemImg = root.itemImg
        var item = root.item
        var layoutDate = root.layoutDate
        var txtDate = root.txtDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(activity).inflate(R.layout.adapter_item ,parent,false)
        return  MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(data[position].dateOfPublish =="aaa"){
            holder.layoutDate.visibility = View.GONE
        }else{
            holder.layoutDate.visibility = View.VISIBLE
            holder.txtDate.text = data[position].dateOfPublish
        }

        if(data[position].title.length>30){
            holder.txtTitle.text = data[position].title.substring(0,29)+"..."
        }else{
            holder.txtTitle.text = data[position].title
        }


        if(data[position].description.length>=50) {
            val cutDesc = data[position].description.substring(0,49)+"..."
            holder.txtDetails.text = cutDesc
        }else{
            holder.txtDetails.text = data[position].description
        }

        Glide.with(activity).load(data[position].image).into(holder.itemImg)

        holder.item.setOnClickListener {
            onItemClick?.invoke(data[position])
        }

    }

    override fun getItemCount(): Int {
       return data.size
    }
}