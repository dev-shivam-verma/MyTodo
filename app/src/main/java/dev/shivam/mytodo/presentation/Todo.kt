package dev.shivam.mytodo.presentation


import java.util.Date

data class Todo(
    var id: Int,
    var title: String,
    var description: String,
    var createdAt: Date = Date(),
    var completionDateTime : Date = Date(System.currentTimeMillis() + 1000 * 60 * 60)
)

fun getDummyTodos(): List<Todo> {
    return listOf(
        Todo(1, "Buy groceries", "Buy milk, eggs, and bread"),
        Todo(2, "Call mom", "Check in with mom and see how she is doing"),
        Todo(3, "Go for a run", "Run 5 kilometers in the park"),
        Todo(4, "Read a book", "Finish reading the current chapter"),
        Todo(5, "Complete project", "Work on the Android project for 2 hours")
    )
}
