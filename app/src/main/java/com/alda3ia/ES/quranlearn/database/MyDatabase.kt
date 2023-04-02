package com.alda3ia.ES.quranlearn.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.alda3ia.ES.quranlearn.model.Sura
import com.alda3ia.ES.quranlearn.model.VideoItem

@Database(entities = [VideoItem::class,Sura::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

   companion object {

       private val db_Name: String="at_tanzil_database"

       private var instance: MyDatabase? = null

       fun getInstance(context: Context): MyDatabase {
          return instance ?:buildDatabase(context).also { instance=it }
       }

       private fun buildDatabase(context: Context): MyDatabase {
          return databaseBuilder(context,MyDatabase::class.java,db_Name).createFromAsset("databases/at_tanzil_database.db")
              .allowMainThreadQueries()
              .build()
       }


   }

   abstract fun dao():Dao



}