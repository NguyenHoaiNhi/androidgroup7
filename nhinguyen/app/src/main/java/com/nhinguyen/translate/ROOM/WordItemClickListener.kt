package com.nhinguyen.translate.ROOM

interface WordItemClickListener {
    fun onItemCLicked(position: Int)
    fun onItemLongCLicked(position: Int)
    fun onItemTrashCLicked(position: Int)
}