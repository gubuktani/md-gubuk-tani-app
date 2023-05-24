package com.futureengineerdev.gubugtani.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
class RemoteKeys (
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
