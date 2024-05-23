package com.example.githubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Favorite (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "ava_url")
    var ava_url: String? = null,

) : Parcelable