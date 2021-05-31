package com.chidi.pokemongo.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chidi.pokemongo.BuildConfig
import com.chidi.pokemongo.data.local.SharedPreferenceManager
import com.chidi.pokemongo.data.remote.response.ActivityResponse
import com.chidi.pokemongo.data.repository.GoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: GoRepository, private val pref: SharedPreferenceManager) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
        getToken()
    }

    private val _activity = MutableLiveData<ActivityResponse>()
    val activity: LiveData<ActivityResponse>
        get() = _activity

    private fun getToken(email: String = BuildConfig.EMAIL) {
        repository.getToken(email).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                pref.setUserToken(response.token)
            }, {
                Timber.d(it)
            }).let { compositeDisposable.add(it) }
    }

    fun getActivity() {
        repository.getActivity().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.enqueue(object : Callback<ActivityResponse> {
                    override fun onResponse(call: Call<ActivityResponse>, response: Response<ActivityResponse>) {
                        if (response.code() == 401) {
                            getToken(); getActivity()
                        }
                        _activity.postValue(response.body())
                    }

                    override fun onFailure(call: Call<ActivityResponse>, t: Throwable) {
                        Timber.d(t)
                    }

                })
            }, {
                Timber.d(it)
                // if errorCode is 401 get token and try again
                getToken()
                getActivity()
            }).let { compositeDisposable.add(it) }
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}