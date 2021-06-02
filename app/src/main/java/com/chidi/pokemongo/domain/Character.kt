package com.chidi.pokemongo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int, // 7
    val name: String, // Squirtle
    val rand_long: Double, // 21.51259421447304
    val rand_lat: Double // 1.6735856304167953
) : Parcelable
