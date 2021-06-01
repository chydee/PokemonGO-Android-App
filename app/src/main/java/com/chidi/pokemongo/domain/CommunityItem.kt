package com.chidi.pokemongo.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "community")
class CommunityItem(
    val trainerName: String,
    @PrimaryKey val pokemonID: Int,
    val pokemonName: String,
    val capturedAt: String,
    val isFriend: Boolean
)