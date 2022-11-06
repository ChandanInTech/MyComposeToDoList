package com.chandan.mycomposetodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chandan.mycomposetodolist.ui.theme.MyComposeToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()
        setContent {
            MyComposeToDoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ToDoScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun ToDoScreen(viewModel: MainViewModel) {
    Column {
        AddNoteRow(viewModel::addToDOItem)
        TodoList(viewModel.todoList, viewModel::removeToDOItem, viewModel::onCheckClick)
    }
}

@Composable
private fun TodoList(
    todoList: List<ToDoItem>,
    removeItem: (ToDoItem) -> Unit,
    onCheckChange: (ToDoItem) -> Unit
) {
    Card(
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (todoList.isEmpty()) {
            Text(
                text = "Wow so empty!",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
            return@Card
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            items(todoList.reversed(), key = { task -> task.id }) { task ->
                ToDoItem(
                    toDoItem = task,
                    removeItem = { removeItem(task) },
                    onCheckChange = { onCheckChange(task) })
            }
        }
    }
}

@Composable
private fun AddNoteRow(addNote: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by remember {
            mutableStateOf(TextFieldValue(""))
        }
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            placeholder = {
                Text(text = "Whats your todo")
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        Button(onClick = {
            addNote(text.text)
            text = TextFieldValue("")
        }, modifier = Modifier.height(56.dp)) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Add")
        }
    }
}

@Composable
fun ToDoItem(
    modifier: Modifier = Modifier,
    toDoItem: ToDoItem,
    onCheckChange: () -> Unit,
    removeItem: () -> Unit
) {

    val topRadius = if (toDoItem.isDone) 32.dp else 4.dp

    Card(
        shape = RoundedCornerShape(
            topStart = topRadius,
            bottomEnd = topRadius,
            topEnd = 4.dp,
            bottomStart = 4.dp
        ),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = toDoItem.isDone, onCheckedChange = {
                onCheckChange()
            })

            Text(
                text = toDoItem.task,
                modifier = Modifier.weight(1f),
                style = if (toDoItem.isDone) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(
                    textDecoration = TextDecoration.None
                ),
                fontSize = 22.sp
            )

            IconButton(onClick = { removeItem() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeToDoListTheme {
        ToDoItem("")
    }
}
