package com.josedev.dealingtasking

import android.content.SharedPreferences
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.josedev.dealingtasking.firebasecrud.datastore.UserSettings
import com.josedev.dealingtasking.firebasecrud.di.FireAppAuth
import com.josedev.dealingtasking.navigation.Destination
import com.josedev.dealingtasking.presentation.detail.TaskDetailLScreen
import com.josedev.dealingtasking.presentation.detail.TaskDetailViewModel
import com.josedev.dealingtasking.presentation.list.TaskListScreen
import com.josedev.dealingtasking.presentation.list.TaskListViewModel
import com.josedev.dealingtasking.presentation.components.SplashScreen
import com.josedev.dealingtasking.presentation.login.MainViewModel
import com.josedev.dealingtasking.presentation.loginScreen
import com.josedev.dealingtasking.ui.theme.DealingTaskingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.observeOn

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DealingTaskingTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val auth = Firebase.auth
                val user = FireAppAuth(auth)
                NavHost(
                    navController = navController,
                    startDestination = Destination.Splash.route

                ) {
                    addTaskList(navController = navController,
                    user, auth)
                    addTaskDetail(navController = navController)
                    creatingUser(navController = navController, auth = auth)

                }
            }
        }
    }


    @Composable
    fun rr(){
        val wert =viewModel.settingPref.collectAsState(initial = UserSettings(emailUser = "", psswdUser = ""))

    }
}


@ExperimentalMaterialApi
fun NavGraphBuilder.addTaskList(
    navController: NavController,
    user: FireAppAuth,
    auth: FirebaseAuth
) {
    composable(
        route = Destination.TaskList.route
    ) {
        val viewModel: TaskListViewModel = hiltViewModel()
        val state = viewModel.state.value
        val isRefreshing = viewModel.isRefreshing.collectAsState()
        TaskListScreen(
            auth = auth,
            navController = navController,
            user = user,
            state = state,
            navigateToTaskDetail = {
                navController.navigate(Destination.TaskDetail.route)

            },
            isRefreshing = isRefreshing.value,
            refreshingData = viewModel::getTaskList,
            onItemClick = { taskId -> navController.navigate(Destination.TaskDetail.route + "?taskId=${taskId}") },
            deleteTask = viewModel::deleteTask
        )
    }
    composable(
        route = Destination.Splash.route
    ) {
        SplashScreen(navController = navController)
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.creatingUser(
    navController: NavController,
    //user: FireAppAuth,
    auth: FirebaseAuth
    ) {
    composable(
        route = Destination.LoginUser.route
    ) {
val viewModel: MainViewModel = hiltViewModel()
        var existUser = viewModel.settingPref.collectAsState(initial = UserSettings(emailUser = "", psswdUser = ""))

        loginScreen(
            auth = auth,
            //userCreate = user,
            //userLogin = user,
            navController = navController,
            existingUser = existUser.value.emailUser,
            existingPsswd = existUser.value.psswdUser,
            toggleSavedUser = viewModel::toggleSettings
        )

    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.addTaskDetail(navController: NavController) {
    composable(
        route = Destination.TaskDetail.route + "?taskId={taskId}"
    ) {
        val viewModel: TaskDetailViewModel = hiltViewModel()
        val state = viewModel.state.value
        TaskDetailLScreen(
            navController = navController,
            state = state,
            addNewTask = viewModel::addNewTask,
            updateTask = viewModel::updateTask
        )
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")

}

@Preview(showBackground = true, )
@Composable
fun DefaultPreview() {
    DealingTaskingTheme {
        Greeting("Android")
    }
}