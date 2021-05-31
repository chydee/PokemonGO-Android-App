package com.chidi.pokemongo.data.local

import androidx.room.Dao

/**
 * Defines the method for using the Center class with room
 */
@Dao
interface PokemonDao {
    /**
     * This method inserts a center into  centers
     *//*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg center: Center)

    */
    /**
     * This method updates a lesson in the period_table
     *//*
    @Update
    fun update(center: Center)

    */
    /**
     * This method deletes a center from the center table
     *//*
    @Delete
    fun delete(center: Center)

    */
    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     *//*
    @Query("DELETE FROM $TABLE_NAME_CENTERS")
    fun deleteAll()

    */
    /**
     * Get all periods from period_table
     *
     *//*
    @Query("SELECT * FROM $TABLE_NAME_CENTERS  ORDER BY id DESC")
    fun getCenters(): Flow<List<Center>>

    @ExperimentalCoroutinesApi
    fun getCentersDistinctUntilChanged() = getCenters().distinctUntilChanged()*/
}