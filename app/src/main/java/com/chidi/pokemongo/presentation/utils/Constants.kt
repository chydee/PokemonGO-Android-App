package com.chidi.pokemongo.presentation.utils

import com.chidi.pokemongo.domain.Character

object Constants {

    const val APP_DB_NAME = "PokemonGO_DB"

    //START HTTP Request Endpoints
    const val TOKEN = "token"
    const val ACTIVITY = "activity"
    const val MY_TEAM = "my-team"
    const val CAPTURED = "captured"
    const val CAPTURE = "capture"
    const val RELEASE = "release"
    // END HTTP Request Endpoints

    val randomCharacters = listOf<Character>(
        Character(1, "Bulbasaur", 20.10099206786478, 32.5910273441159),
        Character(10, "Caterpie", -7.558541622012861, 44.741980750732765),
        Character(4, "Charmander", 43.486976716307126, 47.03270976831398),
        Character(7, "Squirtle", 20.10099206786478, 21.51259421447304),
        Character(3, "Keith", 2.6735856304167953, 31.5910273441159),
    )
}