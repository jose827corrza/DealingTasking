package com.josedev.dealingtasking.presentation.list

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.josedev.dealingtasking.firebasecrud.di.FireAppAuth
import com.josedev.dealingtasking.firebasecrud.repositories.Result
import com.josedev.dealingtasking.firebasecrud.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel
@Inject
constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _state: MutableState<TaskListState> = mutableStateOf(TaskListState())
    val state: State<TaskListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    val user = Firebase.auth
    val auth = FireAppAuth(user)


    init {
        getTaskList()

    }

    fun getTaskList() {

        taskRepository.getTaskList().onEach { result ->

            when (result) {
                is Result.Error -> {
                    _state.value = TaskListState(error = result.message ?: "Error inesperado")
                }
                is Result.Loading -> {
                    _state.value = TaskListState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = TaskListState(tasks = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteTask(taskId: String) {
        taskRepository.deleteTask(taskId = taskId)
    }

}