package com.chidi.pokemongo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chidi.pokemongo.data.local.CapturedDao
import com.chidi.pokemongo.data.local.CommunityDao
import com.chidi.pokemongo.data.local.TeamDao
import com.chidi.pokemongo.domain.CapturedItem
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.domain.TeamItem

@Database(entities = [TeamItem::class, CapturedItem::class, CommunityItem::class], version = 1, exportSchema = false)
abstract class GoDatabase : RoomDatabase() {
    abstract val capturedDao: CapturedDao
    abstract val teamDao: TeamDao
    abstract val communityDao: CommunityDao
}
