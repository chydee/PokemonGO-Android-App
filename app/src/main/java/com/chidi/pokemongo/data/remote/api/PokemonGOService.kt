package com.chidi.pokemongo.data.remote.api

import com.chidi.pokemongo.data.remote.response.ActivityResponse
import com.chidi.pokemongo.data.remote.response.TokenResponse
import com.chidi.pokemongo.presentation.utils.Constants
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PokemonGOService {
    /**
     * POST Request to fetch a token
     * @param email takes the authorized email address and it returns
     * @return Call<TokenResponse>
     */
    @POST(Constants.TOKEN)
    fun requestForToken(
        @Query("email") email: String
    ): Observable<TokenResponse>

    /**
     *
     *
     */
    @GET(Constants.ACTIVITY)
    fun getActivity(): Observable<Call<ActivityResponse>>
}