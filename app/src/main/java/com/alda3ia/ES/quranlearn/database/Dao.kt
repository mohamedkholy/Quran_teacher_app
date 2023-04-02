package com.alda3ia.ES.quranlearn.database

import androidx.room.Query
import com.alda3ia.ES.quranlearn.model.VideoItem
import androidx.room.Dao
import androidx.room.Update
import com.alda3ia.ES.quranlearn.model.Sura

@Dao
interface Dao {

    @Query("SELECT * FROM VideoItem where name = :itemName")
    fun getItem(itemName:String):VideoItem



    @Query("select * from Sura where level = :level")
    fun getSurahByLevel(level:Int):List<Sura>

    @Query("select aya from VideoItem where name like :suraName || '%' ")
    fun getAllAyas(suraName:String):List<String>

}