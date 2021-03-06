package com.chidi.pokemongo.data.remote.api

import com.chidi.pokemongo.data.remote.param.Capture
import com.chidi.pokemongo.data.remote.response.*
import com.chidi.pokemongo.presentation.utils.Constants
import io.reactivex.Observable
import retrofit2.http.*

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

    @POST(Constants.CAPTURE)
    fun capturePokemon(@Body capture: Capture): Observable<CaptureResponse>

    @DELETE(Constants.RELEASE)
    fun releasePokemon(@Query("id") id: Int): Observable<ReleaseResponse>
}