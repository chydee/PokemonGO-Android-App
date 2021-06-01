package com.chidi.pokemongo.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chidi.pokemongo.data.repository.DBHelperImpl
import com.chidi.pokemongo.domain.CapturedItem
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.domain.TeamItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LocalStorageViewModel @Inject constructor(private val dbHelperImpl: DBHelperImpl) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _captured = MutableLiveData<List<CapturedItem>>()
    val captured: LiveData<List<CapturedItem>>
        get() = _captured

    private val _myTeam = MutableLiveData<List<TeamItem>>()
    val myTeam: LiveData<List<TeamItem>>
        get() = _myTeam

    private val _community = MutableLiveData<List<CommunityItem>>()
    val community: LiveData<List<CommunityItem>>
        get() = _community

    private val _friends = MutableLiveData<List<CommunityItem>>()
    val friends: LiveData<List<CommunityItem>>
        get() = _friends

    private val _foes = MutableLiveData<List<CommunityItem>>()
    val foes: LiveData<List<CommunityItem>>
        get() = _foes

    //[START] local storage transactions

    fun saveTeamsToLocalStorage(vararg teams: TeamItem) {
        dbHelperImpl.insertTeam(*teams).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.e("operation successful") }, { Timber.e(it) }).let { compositeDisposable.add(it) }
    }

    fun saveCapturedPokemonToLocalStorage(vararg captured: CapturedItem) {
        dbHelperImpl.insertCapturedItem(*captured).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {}).let { compositeDisposable.add(it) }
    }

    fun saveCommunity(vararg communityItem: CommunityItem) {
        dbHelperImpl.insertCommunityItem(*communityItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {}).let { compositeDisposable.add(it) }
    }

    fun getAllCaptured() {
        dbHelperImpl.getCaptured().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { captured ->
                    if (captured.isNotEmpty()) {
                        _captured.postValue(captured)
                    } else {
                        _captured.postValue(listOf())
                    }
                },
                { Timber.e(it) }
            ).let { compositeDisposable.add(it) }
    }

    fun getMyTeam() {
        dbHelperImpl.getMyTeam().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { myTeam ->
                    if (myTeam.isNotEmpty()) {
                        _myTeam.postValue(myTeam)
                    } else {
                        _myTeam.postValue(listOf())
                    }
                },
                { Timber.e(it) }
            ).let { compositeDisposable.add(it) }
    }

    fun getFoes() {
        dbHelperImpl.getFoes().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { foes ->
                    if (foes.isNotEmpty()) {
                        _foes.postValue(foes)
                    } else {
                        _foes.postValue(listOf())
                    }
                },
                { Timber.e(it) }
            ).let { compositeDisposable.add(it) }
    }

    fun getFriends() {
        dbHelperImpl.getFriends().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { friends ->
                    if (friends.isNotEmpty()) {
                        _friends.postValue(friends)
                    } else {
                        _friends.postValue(listOf())
                    }
                },
                { Timber.e(it) }
            ).let { compositeDisposable.add(it) }
    }


    fun deleteAllTeams() {
        dbHelperImpl.deleteAllTeams().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.e("operation completed") }, { Timber.e(it) }).let { compositeDisposable.add(it) }
    }

    fun deleteAllCommunity() {
        dbHelperImpl.deleteAllCommunity().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.e("operation completed") }, { Timber.e(it) }).let { compositeDisposable.add(it) }
    }

    fun releaseAllCaptured() {
        dbHelperImpl.releaseAllCaptured().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.e("operation completed") }, { Timber.e(it) }).let { compositeDisposable.add(it) }
    }

    fun releaseCaptured(capturedItem: CapturedItem) {
        dbHelperImpl.releaseCaptured(capturedItem)
    }
    //[END] local storage transactions

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}