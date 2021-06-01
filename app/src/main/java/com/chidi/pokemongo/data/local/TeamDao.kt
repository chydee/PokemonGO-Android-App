package com.chidi.pokemongo.data.local

import androidx.room.*
import com.chidi.pokemongo.domain.TeamItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg teams: TeamItem): Completable

    @Delete
    fun delete(teamItem: TeamItem): Completable

    @Query("DELETE FROM my_team")
    fun deleteAll(): Completable

    @Query("SELECT * FROM my_team")
    fun getMyTeam(): Single<List<TeamItem>>

}