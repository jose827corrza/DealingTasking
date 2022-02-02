package com.josedev.dealingtasking.firebasecrud.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.ktx.Firebase
import com.josedev.dealingtasking.firebasecrud.di.FireAppAuth
import com.josedev.dealingtasking.firebasecrud.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository
@Inject
constructor(
    private val taskList: CollectionReference
) {
val user = Firebase.auth
    val auth =FireAppAuth(user)
    /**
     * Operaciones con Firestore
     */




    fun addNewTask(task: Task) {

        try {
            taskList.document(task.id).set(task)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getTaskList(): Flow<Result<List<Task>>> = flow {

        /**
         * Aca toca manejar los posibles que
         * se demoren, que no lleguen o que lleguen mal los datos
         */
        try {
            emit(Result.Loading<List<Task>>())
            val taskList = taskList.whereEqualTo("user", user.currentUser!!.email).get().await().map { document ->
                document.toObject(Task::class.java)
            }

            emit(Result.Success<List<Task>>(data = taskList))
        } catch (e: Exception) {
            emit(Result.Error<List<Task>>(message = e.localizedMessage ?: "Error desconocido"))
        }

    }

    fun getTaskById(taskId: String): Flow<Result<Task>> = flow {

        try {
            emit(Result.Loading<Task>())
            val task = taskList
                .whereGreaterThanOrEqualTo("id", taskId)
                .get()
                .await()
                .toObjects(Task::class.java)
                .first()

            emit(Result.Success<Task>(data = task))
        } catch (e: Exception) {
            emit(Result.Error<Task>(message = e.localizedMessage ?: "Error desconocido en Repository"))
        }
    }

    fun updateTask(taskId: String, task: Task) {
        try {
            val map = mapOf(
                "title" to task.title,
                "descrip" to task.descrip,
                "status" to task.status,
                "prior" to task.prior
            )
            taskList.document(taskId).update(map)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteTask(taskId: String) {
        try {
            taskList.document(taskId).delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}