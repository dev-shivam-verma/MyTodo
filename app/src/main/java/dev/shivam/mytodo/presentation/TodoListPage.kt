package dev.shivam.mytodo.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.darkokoa.datetimewheelpicker.WheelDateTimePicker
import dev.darkokoa.datetimewheelpicker.core.TimeFormat
import dev.darkokoa.datetimewheelpicker.core.WheelPickerDefaults
import dev.shivam.mytodo.R
import dev.shivam.mytodo.TodoViewmodel
import dev.shivam.mytodo.ui.theme.AppDark
import dev.shivam.mytodo.ui.theme.AppLight
import dev.shivam.mytodo.ui.theme.AppMedium
import kotlinx.datetime.toJavaLocalDateTime
import java.time.ZoneId
import java.util.Date

@Composable
fun TodoListPage(
    viewmodel: TodoViewmodel = viewModel()
) {
    val state = viewmodel.state

    var newTodoTitle by remember {
        mutableStateOf("")
    }

    var newTodoDesc by remember {
        mutableStateOf("")
    }

    var newTodoEndTime by remember {
        mutableStateOf(Date(System.currentTimeMillis() + 1000 * 60 * 60))
    }

    var isAddingTodo by remember {
        mutableStateOf(false)
    }


    // Todo List Screen
    AnimatedVisibility(visible = !isAddingTodo) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // Add new todo button
            ButtonAddNewTodo { isAddingTodo = true }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                items(state.todos.size) { index ->
                    AnimatedVisibility(visible = state.todos[index] in state.todos) {
                        TodoItem(todo = state.todos[index], deleteAction = viewmodel::deleteTodo)
                    }
                }
            }
        }
    }


    // Todo Add Screen
    AnimatedVisibility(
        visible = isAddingTodo) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Title input field
            TodoTextField(newTodoTitle, "Title", { newTodoTitle = it })

            Spacer(modifier = Modifier.height(16.dp))

            // Description input field
            TodoTextField(
                newTodoDesc,
                "Description",
                { newTodoDesc = it },
                Modifier.heightIn(min = 200.dp)
            )

            Text(
                fontSize = 18.sp,
                color = AppDark,
                text = "Select End Date"
            )


            // Wheel Date Time picker
            WheelDateTimePicker(
                textColor = androidx.compose.ui.graphics.Color.Cyan,
                timeFormat = TimeFormat.AM_PM,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = true,
                    shape = RoundedCornerShape(12.dp),
                    color = AppDark.copy(alpha = 0.95f),
                    border = BorderStroke(1.dp, AppDark)
                )
            ) { localDateTime ->
                newTodoEndTime = Date.from(
                    localDateTime.toJavaLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()
                )
            }


            // button add Todo
            Button(onClick = {
                viewmodel.addTodo(
                    newTodoTitle, newTodoDesc, newTodoEndTime
                )

                // changing the screen state
                isAddingTodo = false
            }
            ) {
                Text("Add Todo")
            }

        }
    }
}

@Composable
private fun ButtonAddNewTodo(onClick: () -> Unit) {

    IconButton(
        modifier = Modifier
            .padding(horizontal = 48.dp)
            .padding(vertical = 24.dp)
            .width(77.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(AppDark),
        onClick = {
            onClick()
        }) {
        Row {
            Text(
                color = AppLight,
                fontSize = 18.sp,
                text = "Add"
            )

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp)
                    .aspectRatio(1f),
                painter = painterResource(id = R.drawable.add),
                contentDescription = "",
                colorFilter = ColorFilter.tint(AppLight)
            )
        }
    }
}


@Composable
private fun TodoTextField(
    newTodoTitle: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        placeholder = {
            Text(
                color = AppLight.copy(alpha = 0.5f),
                text = placeholder
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        textStyle = TextStyle(
            color = AppLight,
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp
        ),
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedContainerColor = AppMedium,
            unfocusedContainerColor = AppMedium,
            cursorColor = AppLight
        ),
        value = newTodoTitle,
        onValueChange = {
            onValueChange(it)
        })
}


@Preview(showSystemUi = true)
@Composable
fun TodoListPagePreview() {
    TodoListPage(
    )
}