package com.josedev.dealingtasking.presentation.list

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.josedev.dealingtasking.R
import com.josedev.dealingtasking.firebasecrud.di.FireAppAuth
import com.josedev.dealingtasking.navigation.Destination
import com.josedev.dealingtasking.presentation.components.TaskList
import com.josedev.dealingtasking.ui.theme.BlueFire
import com.josedev.dealingtasking.ui.theme.OrangeFire
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TaskListScreen(
    auth: FirebaseAuth,
    navController: NavController,
    user: FireAppAuth,
    state: TaskListState,
    navigateToTaskDetail: () -> Unit,
    isRefreshing: Boolean,
    refreshingData: () -> Unit,
    onItemClick: (String) -> Unit,
    deleteTask: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    val scope = rememberCoroutineScope()

    val contexto = LocalContext.current


//    public suspend fun saveUserCredentials(){
//
//        val contexto = this
//        val dataStore: DataStore<Preferences> = createDataStore(name = "account")
//    }


    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = OrangeFire,
                title = { Text(text = "Lista de Tareas") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Busqueda",
                            modifier = Modifier.clickable {
                                Toast.makeText(
                                    contexto,
                                    "Cerraste sesion: ${auth.currentUser?.email}",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(Destination.LoginUser.route)
                                //auth.signOut()
                            })
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTaskDetail,
                backgroundColor = OrangeFire,
                contentColor = BlueFire
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add icon"
                )
            }
        },
        drawerContent = {
            Text(
                text = "Drawer DealingTask",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Divider()
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Column() {
                    Image(
                        painter = painterResource(id = if (auth.currentUser?.photoUrl != null) R.drawable.profile else R.drawable.defaultimg),
                        contentDescription = "Perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)

                    )
                    Text(
                        text = auth.currentUser!!.email.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()

                    .padding(10.dp)
            ) {
                Text(text = "Ajustes - Proximamente", color = Color.Black)
                Text(text = "Cambiar Imagen de perfil - Proximamente", color = Color.Black)
                Text(text = "Sobre", color = Color.Black, modifier = Modifier.clickable {
                    Toast.makeText(
                        contexto,
                        "Desarrollado por joseDev 2022",
                        Toast.LENGTH_LONG
                    ).show()
                })
                Row(modifier = Modifier.clickable {

                    //navController.navigate(Destination.LoginUser.route)
                    //auth.signOut()
                    Toast.makeText(
                        contexto,
                        "Se supone debes salir",
                        Toast.LENGTH_LONG
                    )
                }) {
                    Text(text = "Salir", color = Color.Black)
                    Icon(imageVector = Icons.Default.Close, contentDescription = "LogOut")
                }
            }
        },
        scaffoldState = scaffoldState
    ) {
        TaskList(
            state = state,
            isRefreshing = isRefreshing,
            refreshData = refreshingData,
            onItemClick = onItemClick,
            deleteTask = deleteTask
        )
    }


}


@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    auth: FireAppAuth,
    navController: NavController
) {
    Column {
//        Image(painter = painterResource(id = R.drawable.bg),
//            contentDescription = "backGround",
//            modifier = Modifier
//                .height(150.dp)
//                .fillMaxWidth(),
//            contentScale = ContentScale.FillWidth
//        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
        Text(text = "item1")
        Text(text = "item2")
        Text(text = "item3")
        Text(text = "item4")
        IconButton(onClick = {
            auth.signAccountOff()
            navController.navigate("login-screen")
        }) {
            Icon(Icons.Default.Close, contentDescription = "Adios")
        }
    }
}