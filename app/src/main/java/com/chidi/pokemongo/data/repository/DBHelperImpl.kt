package com.chidi.pokemongo.data.repository

import com.chidi.pokemongo.data.local.CapturedDao
import com.chidi.pokemongo.data.local.CommunityDao
import com.chidi.pokemongo.data.local.TeamDao
import com.chidi.pokemongo.domain.CapturedItem
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.domain.TeamItem
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DBHelperImpl @Inject constructor(private val teamDao: TeamDao, private val communityDao: CommunityDao, private val capturedDao: CapturedDao) :
    DBHelper {
    override fun insertTeam(vararg teams: TeamItem): Completable {
        return teamDao.insert(*teams)
    }

    override fun insertCapturedItem(vararg capturedItems: CapturedItem): Completable {
        return capturedDao.insert(*capturedItems)
    }

    override fun insertCommunityItem(vararg communityItem: CommunityItem): Completable {
        return communityDao.insertAll(*communityItem)
    }

    override fun deleteTeam(teamItem: TeamItem): Completable {
        return teamDao.delete(teamItem)
    }

    override fun deleteAllTeams(): Completable {
        return teamDao.deleteAll()
    }

    override fun deleteCommunity(communityItem: CommunityItem): Completable {
        return communityDao.delete(communityItem)
    }

    override fun deleteAllCommunity(): Completable {
        return communityDao.deleteAll()
    }

    override fun releaseCaptured(capturedItems: CapturedItem): Completable {
        return capturedDao.delete(capturedItems)
    }


    override fun releaseAllCaptured(): Completable {
        return capturedDao.deleteAll()
    }

    override fun getCaptured(): Single<List<CapturedItem>> {
        return capturedDao.getCaptured()
    }

    override fun getMyTeam(): Single<List<TeamItem>> {
        return teamDao.getMyTeam()
    }

    override fun getFriends(): Single<List<CommunityItem>> {
        return communityDao.getFriends()
    }

    override fun getFoes(): Single<List<CommunityItem>> {
        return communityDao.getFoes()
    }
}