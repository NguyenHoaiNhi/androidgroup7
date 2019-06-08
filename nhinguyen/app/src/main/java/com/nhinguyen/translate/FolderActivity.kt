package com.nhinguyen.translate

import com.nhinguyen.translate.R
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.nhinguyen.translate.Fragment.FolderAdapter
import com.nhinguyen.translate.ROOM.*
import kotlinx.android.synthetic.main.folder_activity.*


class FolderActivity: AppCompatActivity() {
    lateinit var wordAdapter: WordAdapter
    lateinit var dao: WordDAO
    private var idFolder: Int? = null
    private var nameFolder: String = ""
    var words: ArrayList<Word> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.folder_activity)
        val data = intent.extras
        idFolder = data.getInt("idFolder")
        nameFolder = data.getString("nameFolder")
        initRoomDatabase()
        getDataWord(nameFolder)

    }
    private fun initRoomDatabase() {
        val db = Room.databaseBuilder(
            applicationContext
            , AppDatabase::class.java, DATABASE_NAME).allowMainThreadQueries()
            .build()
        dao = db.wordDAO()
    }
    private fun getDataWord(name: String){
        words = ArrayList(dao.findByNameFolder(name))
        rvFavorite.layoutManager = LinearLayoutManager(this)
        wordAdapter = WordAdapter(words, this)
        rvFavorite.adapter = wordAdapter
    }
}