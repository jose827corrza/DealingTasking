package com.josedev.dealingtasking.presentation.detail

import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.josedev.dealingtasking.R
import com.josedev.dealingtasking.navigation.Destination
import com.josedev.dealingtasking.ui.theme.BlueFire
import com.josedev.dealingtasking.ui.theme.OrangeFire

@Composable
fun TaskDetailLScreen(
    navController: NavController,
    state: TaskDetailState,
    addNewTask: (String, String, Boolean, Int) -> Unit,
    updateTask: (String, String, Boolean, Int) -> Unit
) {
    var title by remember(state.task?.title) { mutableStateOf(state.task?.title ?: "") }
    var descrip by remember(state.task?.descrip) { mutableStateOf(state.task?.descrip ?: "") }
    var checked by remember(state.task?.status) { mutableStateOf(state.task?.status ?: false) }
    var expanded by remember { mutableStateOf(false) }
    var prioridad by remember { mutableStateOf("Prioridad tarea") }
    var prior by remember { mutableStateOf(1)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueFire)
            .padding(16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
                value = title,
                onValueChange = { title = it },
                label = { Text(text = "Titulo") })

            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(70.dp),
                value = descrip,
                onValueChange = { descrip = it },
                label = { Text(text = "Descripcion") })


            if (state.error.isNotBlank()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    text = state.error,
                    style = TextStyle(
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                )
            }



            IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {}
            Text(text = "Estado de la tarea", color = OrangeFire, fontSize = 24.sp)

            ExtendedFloatingActionButton(

                text = { Text(text = prioridad) },
                onClick = {
                    expanded = true
                },
                icon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "dropdown-priority"
                    )
                },
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(100.dp, 200.dp)
            ) {
                DropdownMenuItem(onClick = {
                    prioridad = "Prioridad Baja"
                    expanded = false
                    prior = 1
                }) {
                    Text(text = "Prioridad Baja", color = Color.Black)
                }
                DropdownMenuItem(onClick = {
                    prioridad = "Prioridad Media"
                    expanded = false
                    prior = 2
                }) {
                    Text(text = "Prioridad Media", color = Color.Black)
                }
                DropdownMenuItem(onClick = {
                    prioridad = "Prioridad Alta"
                    expanded = false
                    prior = 3
                }) {
                    Text(text = "Prioridad Alta", color = Color.Black)
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {


                //
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
                } else {


                    if (state.task?.id != null) {
                        ExtendedFloatingActionButton(
                            text = { Text(text = "Actualizar Tarea") },
                            onClick = {
                                updateTask(title, descrip, checked, prior)
                                navController.navigate(Destination.TaskList.route)
                            },
                            icon = { Icon(Icons.Default.Edit, contentDescription = "update task") },
                            modifier = Modifier
                                .padding(30.dp)
                                .fillMaxWidth()
                                .background(color = Color.Transparent)
                        )
                    } else {


                        ExtendedFloatingActionButton(
                            text = { Text(text = "Agrega una nueva tarea") },
                            onClick = {
                                addNewTask(title, descrip, checked, prior)
                                navController.navigate(Destination.TaskList.route)

                            },
                            icon = { Icon(Icons.Default.Add, contentDescription = "new task") },
                            modifier = Modifier
                                .padding(30.dp)
                                .fillMaxWidth()
                                .background(color = Color.Transparent)
                        )
                    }

                }
            }

//            Box() {
//
//            }

        }

    }

}


@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: () -> Unit
): @Composable Unit {
    Icon(
        imageVector = if (checked) Icons.Filled.Close else Icons.Outlined.Check,
        contentDescription = if (checked) "Anadir" else "Quitar",
        tint = OrangeFire,
        modifier = Modifier
            .size(50.dp)
            .clickable {
                onCheckedChange(!checked)
            }
    )
}
