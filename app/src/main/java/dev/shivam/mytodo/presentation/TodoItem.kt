package dev.shivam.mytodo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.shivam.mytodo.R
import dev.shivam.mytodo.ui.theme.AppLight
import dev.shivam.mytodo.ui.theme.AppMedium


@Composable
fun TodoItem(todo: Todo, deleteAction: (Int) -> Unit) {


    Column(
        modifier = Modifier
            .heightIn(min = 100.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = AppMedium)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 15.dp)

        ) {
            // Title
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 18.sp,
                color = AppLight,
                text = if (todo.title.length > 18) todo.title.substring(
                    0,
                    18
                ) + "..." else todo.title
            )

            // completion time
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 12.sp,
                color = AppLight,
                text = todo.completionDateTime.toLocaleString()
            )
        }


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(vertical = 16.dp)
        ) {

            // Description
            Text(

                fontSize = 14.sp,
                color = AppLight,
                text = todo.description
            )


            val buttonSize = 22.dp
            // Delete button
            IconButton(
                onClick = {
                    deleteAction(todo.id)
                },
                modifier = Modifier
                    .size(buttonSize)
                    .align(Alignment.Bottom)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "",
                    modifier = Modifier
                        .size(buttonSize)
                )
            }
        }
    }
}