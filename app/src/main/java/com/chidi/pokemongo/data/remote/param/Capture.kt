package com.chidi.pokemongo.data.remote.param

data class Capture(
    val pokemon: Pokemon
) {
    data class Pokemon(
        val id: Int, // 1
        val name: String, // Bulbasaur
        val lat: Double, // 23.567775654
        val long: Double // 23.498484802
    )
}