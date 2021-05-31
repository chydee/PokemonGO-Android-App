package com.chidi.pokemongo

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Character(
    val id: Int, // 7
    val name: String, // Squirtle
    val rand_long: Double, // 21.51259421447304
    val rand_lat: Double // 1.6735856304167953
) : ClusterItem {
    override fun getPosition(): LatLng =
        LatLng(rand_lat, rand_long)

    override fun getTitle(): String =
        name

    override fun getSnippet(): String =
        id.toString()
}
