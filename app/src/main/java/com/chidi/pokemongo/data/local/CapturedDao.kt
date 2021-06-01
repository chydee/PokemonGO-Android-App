package com.chidi.pokemongo.data.local

import androidx.room.*
import com.chidi.pokemongo.domain.CapturedItem
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Defines the method for using the CapturedItem class with room
 */
@Dao
interface CapturedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg capturedItems: CapturedItem): Completable

    @Delete
    fun delete(capturedItems: CapturedItem): Completable

    @Query("DELETE FROM captured")
    fun deleteAll(): Completable

    @Query("SELECT * FROM captured ")
    fun getCaptured(): Single<List<CapturedItem>>

}