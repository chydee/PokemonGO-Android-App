package com.chidi.pokemongo.data.local

import androidx.room.*
import com.chidi.pokemongo.domain.CommunityItem
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Defines the method for using the Center class with room
 */
@Dao
interface CommunityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg communityItem: CommunityItem): Completable

    @Delete
    fun delete(communityItem: CommunityItem): Completable

    @Query("DELETE FROM community")
    fun deleteAll(): Completable

    @Query("SELECT * FROM community WHERE isFriend = 1")
    fun getFriends(): Single<List<CommunityItem>>

    @Query("SELECT * FROM community WHERE isFriend = 0")
    fun getFoes(): Single<List<CommunityItem>>
}