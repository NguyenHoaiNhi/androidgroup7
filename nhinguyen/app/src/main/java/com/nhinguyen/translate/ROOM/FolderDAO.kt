package com.nhinguyen.translate.ROOM

import android.arch.persistence.room.*


@Dao
interface FolderDAO {
    @Query("SELECT * FROM folder")
    fun getAll():List<Folder>

    @Query("SELECT * FROM folder WHERE id=:id")
    fun findById(id: Int): Folder

    @Query("SELECT * FROM folder WHERE folderName=:folder_name")
    fun finByName(folder_name: String): Folder
    @Insert
    fun insertAll(vararg todo: Folder) : List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: Folder):Long

    @Delete
    fun delete(toto: Folder)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task : Folder)

    @Query("DELETE FROM folder")
    fun deleteAllTask()
}