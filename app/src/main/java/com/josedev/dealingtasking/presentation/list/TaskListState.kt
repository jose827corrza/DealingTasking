package com.josedev.dealingtasking.presentation.list

import android.content.SharedPreferences
import com.josedev.dealingtasking.firebasecrud.model.Task

data class TaskListState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val error: String = ""
)
