package com.josedev.dealingtasking.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument>//NameNavArgument
) {
    object TaskList : Destination("task-list", emptyList())
    object TaskDetail :
        Destination(route = "task-detail", arguments = listOf(navArgument("taskId") {
            nullable = true
        }))
    object LoginUser: Destination("login-screen", emptyList())

    object Splash : Destination("splash-view", emptyList())
}
