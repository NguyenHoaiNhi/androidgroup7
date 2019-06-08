package com.nhinguyen.translate.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhinguyen.translate.R
import com.nhinguyen.translate.ROOM.Folder
import kotlinx.android.synthetic.main.folder_item.view.*

@SuppressLint("SetTextI18n")

class FolderAdapter (var items: ArrayList<Folder>, val context: Context) : RecyclerView.Adapter<FolderViewHolder>() {
    lateinit var mListener: FolderItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FolderViewHolder {
        return FolderViewHolder(LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(movieViewHolder: FolderViewHolder, position: Int) {
        movieViewHolder.txtFolder.text = items[position].folderName
        movieViewHolder.itemView.setOnClickListener{
            Log.d("msg", "chuyen tab")
            mListener.onItemCLicked(position)
        }
    }

    fun appenData(newFolderAdded: Folder){
        this.items.add(newFolderAdded)
        notifyItemInserted(items.size-1)
    }

    fun setListener(listener: FolderItemClickListener) {
        this.mListener = listener
    }


}


class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var txtFolder = view.textFolder
}