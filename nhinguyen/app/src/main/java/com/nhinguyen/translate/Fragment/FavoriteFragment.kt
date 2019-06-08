package com.nhinguyen.translate.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.nhinguyen.translate.DATABASE_NAME
import com.nhinguyen.translate.R
import com.nhinguyen.translate.ROOM.*
import com.nhinguyen.translate.WORD_KEY
import kotlinx.android.synthetic.main.fragment_favorite.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import android.content.DialogInterface
//import android.R



//import android.R



class FavoriteFragment: Fragment() {

    var words: ArrayList<Word> = ArrayList()
    var folder: ArrayList<Folder> = ArrayList()
    lateinit var wordAdapter: WordAdapter
    lateinit var dao_favorite: WordDAO
    lateinit var daoFolder: FolderDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorite, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRoomDatabase()

        setupRecycleView()

        getWords()
        getFolder()
    }


    private fun initRoomDatabase() {
        val db = Room.databaseBuilder(
            getContext()!!
            , AppDatabase::class.java, DATABASE_NAME).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        dao_favorite = db.wordDAO()
        daoFolder = db.folderDAO()
    }

    private fun setupRecycleView() {
        rvFavorite.layoutManager = LinearLayoutManager(getContext()) as RecyclerView.LayoutManager?

        wordAdapter = WordAdapter(words, getContext()!!)

        rvFavorite.adapter = wordAdapter

        wordAdapter.setListener(wordItemClickListener)
    }

    private fun getWords()
    {
        val words = dao_favorite.getAll()
        //Log.i("Get word: ", words.toString())
        this.words.addAll(words)
        wordAdapter.notifyDataSetChanged()

    }
    private fun getFolder(){
        folder = ArrayList(daoFolder.getAll())

    }
    //    private val wordItemClickListener = object : WordItemClickListener {
//
//        override fun onTrashIconClicked(position: Int) {
//            dao_favorite.delete(words[position])
//            wordAdapter.removeItem(words[position], position)
//            wordAdapter.notifyDataSetChanged()
//        }
//
//        override fun onLongItemClick(position: Int) {
//            Log.i("msg", words[position].content_language1)
//        }
//    }
    private val wordItemClickListener = object : WordItemClickListener {
        override fun onItemCLicked(position: Int) {

            dao_favorite.delete(words[position])
            wordAdapter.removeItem(words[position], position)
            wordAdapter.notifyDataSetChanged()

        }

        override fun onItemLongCLicked(position: Int) {

            Log.i("msg", "thanh cong")
            //val mDialogView = LayoutInflater.from(this@FavoriteFragment.context).inflate(R.layout.menu_dialog, null)
            var wordNow = dao_favorite.findById(position+1)
            val builder = AlertDialog.Builder(getContext())
            var delete = arrayOf("Delete")
            var folder = daoFolder.getAll()
            var arrayFolderName: ArrayList<String> = ArrayList()
            for(i in folder){
                arrayFolderName.add(i.folderName)
            }
            for(i in arrayFolderName){
                Log.i("msg", i)
            }
            //val array = arrayOf(arrayFolderName)
            var array = arrayFolderName.toTypedArray()
            //val array = arrayOf("xanh", "do", "tim", "vang")
            //builder.setView(mDialogView)
            with(builder)
            {
                setTitle("Add to")
                setItems(array) { dialog, which ->
                    //updateWord(array[which], position)
                    wordNow.nameFolder = array[which]
                    dao_favorite.update(wordNow)
                    Toast.makeText(this@FavoriteFragment.context, "Added to "+array[which] , Toast.LENGTH_SHORT).show()
                }
//            setItems(delete){dialog, which ->
//                Toast.makeText(this@FavoriteFragment.context, array[which] + " is clicked", Toast.LENGTH_SHORT).show()
//            }
                show()
            }
        }
    }
//    private fun updateWord(folderName : String, position : Int){
//
//    }
}