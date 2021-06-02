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


    //INTENT_EXTRAS
    const val EXTRA_POKEMON_TYPE = "POKEMON_TYPE"
    const val POKEMON_LAT = "LATITUDE"
    const val POKEMON_LONG = "LONGITUDE"
    const val EXTRA_POKEMON = "POKEMON"
    const val EXTRA_CAPTURED = "CAPTURED"
    const val EXTRA_TEAM = "MY_TEAM"
    const val EXTRA_COMMUNITY = "COMMUNITY"
    const val EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "POKEMON_TRANSITION_NAME"

    //POKEMON TYPES
    const val TYPE_WILD = "WILD"
    const val TYPE_CAPTURED = "CAPTURED"
    const val TYPE_CAPTURED_BY_OTHER = "CAPTURED_BY_OTHER"


    const val lat = 20.10099206786478
    const val long = 32.5910273441159

    val randomCharacters = listOf<Character>(
        Character(1, "Bulbasaur", 20.10099206786478, 32.5910273441159),
        Character(10, "Caterpie", -7.558541622012861, 44.741980750732765),
        Character(4, "Charmander", 43.486976716307126, 47.03270976831398),
        Character(7, "Squirtle", 20.10099206786478, 21.51259421447304),
        Character(3, "Keith", 2.6735856304167953, 31.5910273441159),
    )
}