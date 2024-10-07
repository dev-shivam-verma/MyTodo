package dev.shivam.mytodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TodoDao {

    // get all todos
    @Query("SELECT * FROM Todo")
    fun getTodos() : List<Todo>

    // add a todo
    @Insert
    fun addTodo(todo: Todo)

    // delete a todo
    @Delete
    fun deleteTodo(todo: Todo)
}