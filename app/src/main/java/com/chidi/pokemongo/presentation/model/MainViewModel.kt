package com.chidi.pokemongo.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chidi.pokemongo.BuildConfig
import com.chidi.pokemongo.data.local.SharedPreferenceManager
import com.chidi.pokemongo.data.remote.param.Capture
import com.chidi.pokemongo.data.remote.response.*
import com.chidi.pokemongo.data.repository.GoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GoRepository,
    private val pref: SharedPreferenceManager,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _activity = MutableLiveData<ActivityResponse>()
    val activity: LiveData<ActivityResponse>
        get() = _activity

    private val _team = MutableLiveData<MyTeam>()
    val team: LiveData<MyTeam>
        get() = _team

    private val _captured = MutableLiveData<Captured>()
    val captured: LiveData<Captured>
        get() = _captured

    private val _token = MutableLiveData<TokenResponse>()
    val token: LiveData<TokenResponse>
        get() = _token

    private val _capture = MutableLiveData<CaptureResponse>()
    val capturePokemon: LiveData<CaptureResponse>
        get() = _capture

    private val _release = MutableLiveData<ReleaseResponse>()
    val releasePokemon: LiveData<ReleaseResponse>
        get() = _release


    fun getToken(email: String = BuildConfig.EMAIL) {
        pref.clearAllStoredData()
        repository.getToken(email).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                pref.setUserToken(response.token)
                _token.postValue(response)
            }, {
                Timber.d(it)
            }).let { compositeDisposable.add(it) }
    }

    /**
     *  Get All Activities from cloud
     */
    fun getActivity() {
        repository.getActivity().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _activity.postValue(response)
            }, {
                Timber.d(it)
            }).let { compositeDisposable.add(it) }
    }

    // Get My Team from cloud
    fun myTeam() {
        repository.getMyTeam().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _team.postValue(response)
            }, {
                Timber.d(it)
            }).let { compositeDisposable.add(it) }
    }

    // Get all captured pokemon from cloud
    fun getCapturePokemons() {
        repository.getCapturedPokemons().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _captured.postValue(response)
            }, {
                Timber.d(it)
                //  errorCode is 401 get token and try again
                //retryGetActivity()
            }).let { compositeDisposable.add(it) }
    }


    fun capturePokemon(capture: Capture) {
        repository.capturePokemon(capture).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _capture.postValue(response)
            }, {
                Timber.d(it)
            }).let { compositeDisposable.add(it) }
    }

    fun releasePokemon(id: Int) {
        repository.releasePokemon(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _release.postValue(response)
            }, {
                Timber.d(it)
            }).let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}