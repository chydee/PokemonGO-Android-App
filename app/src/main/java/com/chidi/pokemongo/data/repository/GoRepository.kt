package com.chidi.pokemongo.data.repository

import com.chidi.pokemongo.data.remote.api.PokemonGOService
import com.chidi.pokemongo.data.remote.param.Capture
import javax.inject.Inject

class GoRepository @Inject constructor(private val remote: PokemonGOService) {
    fun getToken(email: String) = remote.requestForToken(email)
    fun getActivity() = remote.getActivity()
    fun getMyTeam() = remote.myTeam()
    fun getCapturedPokemons() = remote.getCaptured()
    fun capturePokemon(capture: Capture) = remote.capturePokemon(capture)
    fun releasePokemon(id: Int) = remote.releasePokemon(id)
}