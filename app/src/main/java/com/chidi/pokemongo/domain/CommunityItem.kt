package com.chidi.pokemongo.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "community")
@Parcelize
class CommunityItem(
    val trainerName: String,
    @PrimaryKey val pokemonID: Int,
    val pokemonName: String,
    val capturedAt: String,
    val isFriend: Boolean
) : Parcelable