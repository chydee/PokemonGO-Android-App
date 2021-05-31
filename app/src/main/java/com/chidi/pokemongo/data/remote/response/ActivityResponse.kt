package com.chidi.pokemongo.data.remote.response

import com.chidi.pokemongo.domain.Foe
import com.chidi.pokemongo.domain.Friend

data class ActivityResponse(
    val friends: List<Friend>,
    val foes: List<Foe>
)
