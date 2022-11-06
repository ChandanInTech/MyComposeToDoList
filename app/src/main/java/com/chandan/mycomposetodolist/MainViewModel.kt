package com.chandan.mycomposetodolist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var todoList = mutableStateListOf<ToDoItem>()
        private set

    fun addToDOItem(itemText: String) {
        if (itemText.isNotEmpty()) {
            todoList.add(ToDoItem(itemText))
        }
    }

    fun removeToDOItem(toDoItem: ToDoItem) {
        todoList.remove(toDoItem)
    }

    fun onCheckClick(task: ToDoItem) {
        val index = todoList.indexOf(task)

        todoList[index] = task.copy(
            isDone = !task.isDone
        )
    }
}
