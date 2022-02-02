package com.josedev.dealingtasking.presentation.detail

import com.josedev.dealingtasking.firebasecrud.model.Task

data class TaskDetailState(

    val isLoading: Boolean = false,
    val task: Task? = null,
    val error: String = ""
)
