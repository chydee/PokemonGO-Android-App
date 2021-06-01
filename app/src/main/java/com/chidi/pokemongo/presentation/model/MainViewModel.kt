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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: GoRepository, private val pref: SharedPreferenceManager) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _activity = MutableLiveData<ActivityResponse>()
    val activity: LiveData<ActivityResponse>
        get() = _activity

    fun getToken(email: String = BuildConfig.EMAIL) {
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
                _activity.postValue(response)
            }, {
                Timber.d(it)
                //  errorCode is 401 get token and try again
                //retryGetActivity()
            }).let { compositeDisposable.add(it) }
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}