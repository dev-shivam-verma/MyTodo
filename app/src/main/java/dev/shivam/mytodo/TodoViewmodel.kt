package dev.shivam.mytodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shivam.mytodo.data.TodoRepo
import dev.shivam.mytodo.data.toDataModel
import dev.shivam.mytodo.data.toUiModel
import dev.shivam.mytodo.presentation.Todo
import dev.shivam.mytodo.presentation.TodoListState
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class TodoViewmodel @Inject constructor(
    var todoRepo: TodoRepo
) : ViewModel() {

    init {
        fetchTodos()
    }

    var state by mutableStateOf(TodoListState())
        private set


    fun fetchTodos() {
        viewModelScope.launch {
            val todos = async { todoRepo.getTodos().map { it.toUiModel() } }.await()
            state = state.copy(todos = todos)
        }
    }

    fun addTodo(title: String, description: String, endDateTime: Date) {
        if (title.isEmpty()) {
            return
        }

        val todo = Todo(
            id = 0,
            title = title,
            description = description,
            completionDateTime = endDateTime
        )
        viewModelScope.launch {
            todoRepo.addTodo(todo.toDataModel())
            fetchTodos()
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            val todo = state.todos.find { it.id == id } ?: return@launch
            todoRepo.deleteTodo(todo.toDataModel())
            fetchTodos()
        }
    }
}