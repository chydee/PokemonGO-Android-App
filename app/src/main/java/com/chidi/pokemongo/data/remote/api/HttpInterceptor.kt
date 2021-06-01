package com.chidi.pokemongo.data.remote.api

import com.chidi.pokemongo.BuildConfig
import com.chidi.pokemongo.data.local.SharedPreferenceManager
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


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

        val response = chain.proceed(original)
        // execute original request
        // if the request fails, due to 401 its most likely
        // an authentication issue, then request for new token

        return response
    }

    @Throws(JSONException::class, IOException::class)
    private fun makeTokenRefreshCall(req: Request, chain: Interceptor.Chain): Response {
        /* fetch refreshed token, some synchronous API call, whatever */
        val newTokenResponse = fetchToken(req, chain)
        //only if token refresh is successful
        if (newTokenResponse.code == 200) {
            val body = newTokenResponse.body
            val jsonObject = JSONObject(body?.string())
            val newToken = jsonObject.getString("token")
            // Update new token in Preferences
            sharedPref.setUserToken(newToken)
            token = sharedPref.getUserToken()
            /* make a new request which is same as the original one,
                except that its headers now contain a refreshed token */
            val updatedRequest: Request = req.newBuilder().header("Authorization", "Bearer $token").build()
            return chain.proceed(updatedRequest).also { it.close() }
        }
        //if token refresh is not successful,
        //this is most likely a network error or some other, just return back to user
        return newTokenResponse.also { it.close() }
    }

    private fun fetchToken(req: Request, chain: Interceptor.Chain): Response {
        val body = FormBody.Builder().add("email", BuildConfig.EMAIL).build()
        val builder = req.newBuilder().url(BuildConfig.BASE_URL)
        token?.let { builder.header("Authorization", it) }
        builder.post(body)
        val updatedRequest = builder.build()
        return chain.proceed(updatedRequest).also { it.close() }
    }
}