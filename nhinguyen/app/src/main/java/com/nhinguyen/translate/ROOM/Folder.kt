package com.nhinguyen.translate.ROOM


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
@Entity
data class Folder(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var folderName: String =""
): Parcelable {
    constructor(): this(null, "")
}