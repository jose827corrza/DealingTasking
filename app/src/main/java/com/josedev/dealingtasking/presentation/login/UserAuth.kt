package com.josedev.dealingtasking.presentation

import android.text.Layout
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.josedev.dealingtasking.firebasecrud.di.FireAppAuth
import com.josedev.dealingtasking.navigation.Destination
import com.josedev.dealingtasking.ui.theme.BlueFire
import com.josedev.dealingtasking.ui.theme.OrangeFire
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@ExperimentalMaterialApi
@Composable
fun loginScreen(
    //userCreate: FireAppAuth,
    //userLogin: FireAppAuth,
    auth: FirebaseAuth,
    navController: NavController,
    existingUser: String,
    existingPsswd: String,
    toggleSavedUser: (String, String) -> Unit
) {
    val contexto = LocalContext.current
    var correo by remember { mutableStateOf("") }
    var psswd by remember { mutableStateOf("") }
    val version = "1.0.1"




    Column(
        modifier = Modifier
            .background(BlueFire)
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Text(
            text = "DealingTasking",
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp),
            fontSize = 50.sp,
            fontFamily = FontFamily.SansSerif,
            color = OrangeFire
        )
        Text(
            text = version,
            modifier = Modifier.padding(5.dp),
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = OrangeFire
        )

        Box(
            modifier = Modifier
                .padding()
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Ingresa con tu correo electronico") },
                    modifier = Modifier.padding(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
//Falta ubicar la caja en la mitad de la pantalla

                OutlinedTextField(
                    value = psswd,
                    onValueChange = { psswd = it },
                    label = { Text(text = "Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.padding(10.dp)
                )
                ExtendedFloatingActionButton(text = { Text(text = "Inicia Sesion !") }, onClick = {

                    //userLogin.signInUser(correo, psswd)
                    //userLogin.authentication()
                    //Toast.makeText(contexto,"usuario: ${userLogin.authentication()}!!", Toast.LENGTH_LONG)
                    if (correo != "") {
                        auth.signInWithEmailAndPassword(correo, psswd).addOnCompleteListener {

                            if (it.isSuccessful) {
                                navController.navigate(Destination.TaskList.route)
                                toggleSavedUser(correo, psswd)
                            } else {
                                Toast.makeText(
                                    contexto,
                                    "Revisa tus credenciales",
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        }
                    } else {
                        Toast.makeText(
                            contexto,
                            "Ingresa un correo",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                }, icon = {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "Face"
                    )
                }, modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                )

                ExtendedFloatingActionButton(
                    text = { Text(text = "Registrate !!") },
                    onClick = {
                        //userCreate.createNewUser(correo, psswd)
                        if (correo != "") {
                            auth.createUserWithEmailAndPassword(correo, psswd)
                                .addOnCompleteListener {

                                    if (it.isSuccessful) {
                                        navController.navigate(Destination.TaskList.route)
                                    } else {
                                        Toast.makeText(
                                            contexto,
                                            "Revisa los datos ingresados",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                }
                        } else {
                            Toast.makeText(
                                contexto,
                                "Ingresa un correo",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    },
                    icon = {
                        Icon(
                            Icons.Outlined.Face,
                            contentDescription = "Face"
                        )
                    },
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                )
            }

        }
    }


}