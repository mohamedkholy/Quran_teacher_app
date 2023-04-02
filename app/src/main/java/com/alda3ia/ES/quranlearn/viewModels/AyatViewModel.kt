package com.alda3ia.ES.quranlearn.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alda3ia.ES.quranlearn.database.Dao
import com.alda3ia.ES.quranlearn.database.MyDatabase
import com.alda3ia.ES.quranlearn.model.Sura
import com.alda3ia.ES.quranlearn.model.VideoItem

class AyatViewModel(application: Application) : AndroidViewModel(application) {

    private val context=application.applicationContext
    private  val  db: Dao = MyDatabase.getInstance(context).dao()

    fun getItem(itemName:String):VideoItem{
        return db.getItem(itemName)
    }

}