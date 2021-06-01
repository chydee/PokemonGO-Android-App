package com.chidi.pokemongo.data.remote.api

import com.chidi.pokemongo.data.remote.response.ActivityResponse
import com.chidi.pokemongo.data.remote.response.Captured
import com.chidi.pokemongo.data.remote.response.MyTeam
import com.chidi.pokemongo.data.remote.response.TokenResponse
import com.chidi.pokemongo.presentation.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PokemonGOService {
    /**
     * POST Request to fetch a token
     * @param email takes the authorized email address and it returns
     * @return Observable<TokenResponse>
     */
    @POST(Constants.TOKEN)
    fun requestForToken(
        @Query("email") email: String
    ): Observable<TokenResponse>


    /**
     *  Gets  the activity related to all the trainers that use the,
     *  it is grouped into two categories
     *  Friends & Foe
     */
    @GET(Constants.ACTIVITY)
    fun getActivity(): Observable<ActivityResponse>

    /**
     * Gets the actual team of the trainer.
     */
    @GET(Constants.MY_TEAM)
    fun myTeam(): Observable<MyTeam>

    /**
     * Gets all the captured pokemons thar are stored in your cloud.
     */
    @GET(Constants.CAPTURED)
    fun getCaptured(): Observable<Captured>
}