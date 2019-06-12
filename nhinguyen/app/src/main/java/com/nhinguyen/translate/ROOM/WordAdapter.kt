package com.nhinguyen.translate.ROOM

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nhinguyen.translate.R
import kotlinx.android.synthetic.main.word_item.view.*
import java.util.*

class WordAdapter(var items: ArrayList<Word>, val context: Context) : RecyclerView.Adapter<WordViewHolder>(){

    lateinit var mListener: WordItemClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.word_item,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: WordViewHolder, p1: Int) {
        p0.tvEnglish.text = items[p1].content_language1
        p0.tvVietnamese.text = items[p1].content_language2
        p0.language1.text = items[p1].language1
        p0.language2.text = items[p1].language2

        // Set background
        if(p1 % 2 != 0)
        {
            p0.btnRemove.setBackgroundResource(R.drawable.backgroung2)
        }

        p0.btnRemove.setOnClickListener{
            mListener.onItemTrashCLicked(p1)
        }

        p0.itemView.setOnClickListener{
            mListener.onItemCLicked(p1)


        }
        p0.itemView.setOnLongClickListener {
            mListener.onItemLongCLicked(p1)
            true
        }
    }

    fun setListener(listener: WordItemClickListener) {
        this.mListener = listener
    }

    fun removeItem(userRemove: Word, i: Int) {
        this.items.remove(userRemove)
        notifyItemRemoved(i)
    }
}

class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvEnglish = view.tvEnglish
    var tvVietnamese = view.tvVietnamese
    var btnRemove = view.btnRemove
    var language1 = view.language1
    var language2 = view.language2
    var background = view.iBackground
}