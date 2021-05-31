package com.chidi.pokemongo.data.repository

import com.chidi.pokemongo.data.remote.api.PokemonGOService
import javax.inject.Inject

class GoRepository @Inject constructor(private val remote: PokemonGOService) {
    fun getToken(email: String) = remote.requestForToken(email)
    fun getActivity() = remote.getActivity()
}