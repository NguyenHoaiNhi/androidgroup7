package com.nhinguyen.translate.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.arch.persistence.room.Room
import android.content.Context
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
import com.nhinguyen.translate.FolderActivity
import kotlinx.android.synthetic.main.fragment_folder.*
import kotlin.collections.ArrayList


class FoldersFragment: Fragment() {

    var folder: ArrayList<Folder> = ArrayList()
    lateinit var folderAdapter: FolderAdapter
    lateinit var daofolder: FolderDAO


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_folder, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRoomDatabase()
        loadFolder()
        setupRecycleView()
        createFolder.setOnClickListener{createNewFolder()}
    }


    private fun initRoomDatabase() {
        val dbfolder = Room.databaseBuilder(
            getContext()!!
            , AppDatabase::class.java, DATABASE_NAME).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        daofolder = dbfolder.folderDAO()
    }

    private fun setupRecycleView() {
        rvFolder.layoutManager = LinearLayoutManager(getContext()!!)

        folderAdapter = FolderAdapter(folder, getContext()!!)

        rvFolder.adapter = folderAdapter

        folderAdapter.setListener(FolderItemClickListener)
    }

    private fun createNewFolder(){
        //val context = this
        val builder = AlertDialog.Builder(getContext())
        builder.setTitle("New Folder")
        val view = layoutInflater.inflate(R.layout.new_folder_dialog,null)
        val folderEditText = view.findViewById(R.id.newFolder) as EditText
        val createButton = view.findViewById(R.id.confirm) as Button
        createButton.setOnClickListener{createDataFolder(folderEditText)}
        builder.setView(view)
        builder.show()
    }
    private fun createDataFolder(textFolder: EditText? ){
        //Log.d("msg", textFolder?.text.toString())
        // var compareFolder = daofolder.finByName(textFolder?.text.toString())
        // Log.i("msg",compareFolder.folderName)
//        if(compareFolder.folderName) {
//            Toast.makeText(this@FoldersFragment.context, "Folder already existed" , Toast.LENGTH_SHORT).show()
//            return
//        }
        val text = textFolder?.text.toString()
        val folder = Folder()
        if(text == "") Log.d("msg", "Nothing input")
        else{
            folder.folderName = text
            daofolder.insert(folder)
            folderAdapter.appenData(folder)
        }
    }
    private fun loadFolder(){
        folder = ArrayList(daofolder.getAll())
    }
    private val FolderItemClickListener = object : FolderItemClickListener {
        override fun onItemCLicked(position: Int) {
            Log.i("Top Rate", "Hi")
            val intent = Intent(activity, FolderActivity::class.java)
            intent.putExtra("idFolder",folder[position].id)
            intent.putExtra("nameFolder", folder[position].folderName)
            Log.i("intent", intent.toString())
            startActivity(intent)
        }

    }
}