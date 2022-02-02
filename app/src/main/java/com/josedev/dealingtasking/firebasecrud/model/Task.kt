package com.josedev.dealingtasking.firebasecrud.model

data class Task(
    val id: String,
    val title: String,
    val descrip: String,
    val priority: Float,
    val user: String?,
    val status: Boolean,
    val prior: Int
){

    constructor() : this("","","", 0f, "", false, 1)
}
