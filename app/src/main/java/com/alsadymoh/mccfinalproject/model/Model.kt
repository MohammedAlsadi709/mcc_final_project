package com.alsadymoh.mccfinalproject.model

data class Model(var id: String, var cId: String, var title: String, var description: String, var image: String,var dateOfPublish :String = "aaa") {

    companion object {

         const val COL_ID = "id"
         const val COL_CID = "cId"
         const val COL_TITLE = "title"
         const val COL_DESCRIPTION = "description"
         const val COL_IMAGE = "image"

         const val TABLE_NAME = "Reports"

        const val TABLE_CREATE = "create table $TABLE_NAME ($COL_ID integer primary key autoincrement," +
                "$COL_CID text not null," +
                "$COL_TITLE text not null," +
                "$COL_DESCRIPTION text not null," +
                "$COL_IMAGE text)"


    }

}