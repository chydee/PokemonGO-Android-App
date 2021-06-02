package com.chidi.pokemongo.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "my_team")
@Parcelize
data class TeamItem(
    val name: String, // Squirtle
    val captured_at: String, // 2021-05-03T15:09:22.701Z
    val captured_long_at: Double, // 21.51259421447304
    val captured_lat_at: Double, // 1.6735856304167953
    @PrimaryKey val id: Int // 7
) : Parcelable
