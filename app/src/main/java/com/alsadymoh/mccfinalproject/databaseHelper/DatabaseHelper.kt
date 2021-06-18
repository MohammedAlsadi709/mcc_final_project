package com.alsadymoh.mccfinalproject.databaseHelper

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.alsadymoh.mccfinalproject.model.Model

class DatabaseHelper(var activity: Activity):SQLiteOpenHelper(activity, DATABASE_NAME,null,
        DATABASE_VERSION) {
    //-------------------------------------
    companion object {
        const val DATABASE_NAME = "reports"
        const val DATABASE_VERSION = 1
    }
    //-----------------------------------

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Model.TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table if exists ${Model.TABLE_NAME}")
        onCreate(db)
    }
//-----------------------------------

    private val db: SQLiteDatabase = this.writableDatabase

    fun insertReport(cId: String, title: String, description: String, image: ByteArray):Boolean{
        var cv = contentValuesOf()
        cv.put(Model.COL_CID,cId)
        cv.put(Model.COL_TITLE,title)
        cv.put(Model.COL_DESCRIPTION,description)
        cv.put(Model.COL_IMAGE,image)
        return db.insert(Model.TABLE_NAME,null,cv)>0
    }


    
    fun getReports(cId:String):MutableList<Model>{
        val data = mutableListOf<Model>()

        val c =
                db.rawQuery("select * from ${Model.TABLE_NAME} where ${Model.COL_CID} = $cId order by ${Model.COL_ID} desc ", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val report = Model(c.getString(0),c.getString(1), c.getString(2),c.getString(3),c.getString(4))
            data.add(report)
            c.moveToNext()
        }
        c.close()

        return data
    }
}