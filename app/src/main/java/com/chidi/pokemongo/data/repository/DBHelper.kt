package com.chidi.pokemongo.data.repository

import com.chidi.pokemongo.domain.CapturedItem
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.domain.TeamItem
import io.reactivex.Completable
import io.reactivex.Single

interface DBHelper {
    fun insertTeam(vararg teams: TeamItem): Completable

    fun insertCapturedItem(vararg capturedItems: CapturedItem): Completable

    fun insertCommunityItem(vararg communityItem: CommunityItem): Completable

    fun deleteTeam(teamItem: TeamItem): Completable

    fun deleteAllTeams(): Completable

    fun deleteCommunity(communityItem: CommunityItem): Completable

    fun deleteAllCommunity(): Completable

    fun releaseCaptured(capturedItems: CapturedItem): Completable

    fun releaseAllCaptured(): Completable

    fun getCaptured(): Single<List<CapturedItem>>

    fun getMyTeam(): Single<List<TeamItem>>

    fun getFriends(): Single<List<CommunityItem>>

    fun getFoes(): Single<List<CommunityItem>>
}