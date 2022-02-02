package com.josedev.dealingtasking.presentation.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.josedev.dealingtasking.ui.theme.YellowFire
import kotlinx.coroutines.delay
import com.josedev.dealingtasking.R.*
import com.josedev.dealingtasking.ui.theme.BlueFire

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(2f)
                        .getInterpolation(it)
                }
            )
        )
        delay(300)
        navController.navigate("login-screen")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(BlueFire)
    ) {
        Image(
            painter = painterResource(id = drawable.jetpack_compose),
            contentDescription = "SplashScreen",
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
                .scale(scale.value)
        )
        Text(
            text = "Desarrollador en compose by JoseDev 2021",
            color = YellowFire,
            fontSize = 12.sp
        )
    }

}