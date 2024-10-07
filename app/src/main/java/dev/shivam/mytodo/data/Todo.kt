package dev.shivam.mytodo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Todo")
data class Todo(
    var title : String,
    var description : String,
    var createdAt : Long,
    var completionDateTime : Long,

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
)


fun Todo.toUiModel() : dev.shivam.mytodo.presentation.Todo {
    return dev.shivam.mytodo.presentation.Todo(
        title = title,
        description = description,
        createdAt = Date(createdAt),
        completionDateTime = Date(completionDateTime),
        id = id
    )
}

fun dev.shivam.mytodo.presentation.Todo.toDataModel() : Todo {
    return Todo(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt.time,
        completionDateTime = completionDateTime.time
    )
}