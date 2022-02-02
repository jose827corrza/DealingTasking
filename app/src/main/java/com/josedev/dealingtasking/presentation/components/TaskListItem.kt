package com.josedev.dealingtasking.presentation.components


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.josedev.dealingtasking.R
import com.josedev.dealingtasking.firebasecrud.model.Task
import com.josedev.dealingtasking.ui.theme.OrangeFire
import com.josedev.dealingtasking.ui.theme.Purple500
import com.josedev.dealingtasking.ui.theme.Purple700

@ExperimentalMaterialApi
@Composable
fun TaskListItem(
    task: Task,
    onItemClick: (String) -> Unit,
    taskStatus: Boolean,
) {
    Card(
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable {
                    onItemClick(task.id)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.edittask),
                contentDescription = "editar",
                modifier = Modifier
                    .width(100.dp)
                    .height(160.dp)
                    .padding(8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = task.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = task.descrip,
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = if (task.status) Icons.Filled.Close else Icons.Outlined.Check,
                        contentDescription = "task Status",
                        tint = OrangeFire
                    )
                    Text(text = "La tarea esta completa?", color = Color.Black)


                    /**
                     * MIENTRAS SOLUCIONO OTRAS COSAS
                     */

//                    Text(
//                        text = task.id,
//                        style = TextStyle(
//                            color = Color.Black,
//                            fontWeight = FontWeight.Light,
//                            fontSize = 14.sp
//                        )
//                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "La urgencia de la tarea",
                        tint = if (task.prior == 1) Color.Green else if (task.prior == 2) OrangeFire else Color.Red
                    )
                    Text(text = "Prioridad de la tarea", color = Color.Black)
                }
                /**
                 * ESTE BOTON SE LE PUEDE DAR OTRA FUNCIONALIDAD
                 */
//                Button(
//                    onClick = {},
//                    shape = RoundedCornerShape(50),
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = Purple500
//                    )
//                ) {
//                    Text(
//                        text = "Descargar",
//                        color = Color.White
//                    )
//                }
            }
        }
    }
}