package com.chidi.pokemongo

data class Community(
    val friends: List<Friend>,
    val foes: List<Foe>
) {
    data class Friend(
        val name: String, // Misty
        val pokemon: Pokemon
    ) {
        data class Pokemon(
            val id: Int, // 175
            val name: String, // Togepi
            val captured_at: String // 1997-04-01T13:43:27-06:00
        )
    }

    data class Foe(
        val name: String, // Jessie
        val pokemon: Pokemon
    ) {
        data class Pokemon(
            val id: Int, // 202
            val name: String, // Wobbuffet
            val captured_at: String // 2000-03-24T10:23:42-06:00
        )
    }
}