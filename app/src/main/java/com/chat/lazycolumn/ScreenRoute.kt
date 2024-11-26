package com.chat.lazycolumn

import kotlinx.serialization.Serializable

@Serializable
sealed class PlanetScreen {

    @Serializable
    data object PlanetScreenList : PlanetScreen()

    @Serializable
    data class PlanetScreenDetail(val item: ListItem) : PlanetScreen()
}
