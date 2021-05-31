package com.chidi.pokemongo.data.remote.api

import com.chidi.pokemongo.data.local.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


//Method 1, without using rest adapter
class HttpInterceptor(private val sharedPref: SharedPreferenceManager) : Interceptor {
    private var token: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        ///if you want to use this class to add the token to the header so that
        // you don,t have to use two interceptors
        // add it before executing the original request something like you did before,
        // below :
        val requestBuilder = original.newBuilder()
        token = sharedPref.getUserToken()

        if (token != null) {
            requestBuilder.header("Authorization", "Bearer $token")
        }

        requestBuilder.method(original.method, original.body)
        // override original request with a new request because the header has been updated
        original = requestBuilder.build()
        // execute original request
        // if the request fails, due to 401 its most likely
        // an authentication issue, then request for new token
        return chain.proceed(original)
    }
}