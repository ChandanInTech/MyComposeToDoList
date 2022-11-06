package com.chandan.mycomposetodolist

import java.util.UUID

data class ToDoItem(
    val task: String,
    val id: UUID = UUID.randomUUID(),
    var isDone: Boolean = false
)
