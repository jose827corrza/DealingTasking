package com.josedev.dealingtasking.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.josedev.dealingtasking.firebasecrud.di.FireAppAuth
import com.josedev.dealingtasking.firebasecrud.model.Task
import com.josedev.dealingtasking.firebasecrud.repositories.Result
import com.josedev.dealingtasking.firebasecrud.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel
@Inject
constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<TaskDetailState> = mutableStateOf(TaskDetailState())
    val state: State<TaskDetailState>
        get() = _state
    val user = Firebase.auth
    val auth = FireAppAuth(user)

    init {
        savedStateHandle.get<String>("taskId")?.let { taskId ->
            getTask(taskId)

        }

    }


    fun addNewTask(title: String, descrip: String, status: Boolean, prior: Int) {

        val task = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            descrip = descrip,
            priority = 0.0f,
            user = user.currentUser!!.email,
            status = status,
            prior = prior
        )

        taskRepository.addNewTask(task)
    }

    private fun getTask(taskId: String) {
        taskRepository.getTaskById(taskId = taskId).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = TaskDetailState(error = result.message ?: "Error inesperado")
                }
                is Result.Loading -> {
                    _state.value = TaskDetailState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = TaskDetailState(task = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateTask(newTitle: String, newDescrip: String, status: Boolean, prior: Int) {
        if (state.value.task == null) {
            _state.value = TaskDetailState(error = "Error inesperado en DetailViewModel")
            return
        }
        val taskedited = state.value.task!!.copy(
            title = newTitle,
            descrip = newDescrip,
            status = status,
            prior = prior
        )
        taskRepository.updateTask(taskedited.id, taskedited)
    }


}