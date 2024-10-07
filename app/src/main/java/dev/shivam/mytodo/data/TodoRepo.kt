package dev.shivam.mytodo.data

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepo @Inject constructor(
    var todoDb: TodoDatabase
) {


    suspend fun getTodos(): List<Todo> = withContext(Dispatchers.IO) {
        todoDb.todoDao().getTodos()
    }

    suspend fun addTodo(todo: Todo) = withContext(Dispatchers.IO) {
        todoDb.todoDao().addTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) = withContext(Dispatchers.IO) {
        todoDb.todoDao().addTodo(todo)
    }

    fun updateTodo(todo: Todo) {
        // Update todo in the database
    }
}