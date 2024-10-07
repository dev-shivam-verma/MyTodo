package dev.shivam.mytodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.shivam.mytodo.presentation.TodoListPage
import dev.shivam.mytodo.ui.theme.MyTodoTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTodoTheme {
                TodoListPage()
            }
        }
    }
}

