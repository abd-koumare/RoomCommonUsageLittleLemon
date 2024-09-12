package com.koogin.roomcommonusagelittlelemon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.koogin.roomcommonusagelittlelemon.ui.theme.RoomCommonUsageLittleLemonTheme
import java.lang.Integer.parseInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val users: MutableState<List<User>> = mutableStateOf(
            database.userDao().getAllUsers()
        )

        enableEdgeToEdge()
        setContent {
            RoomCommonUsageLittleLemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        users.value = database.userDao().getAllUsers()

                        val fullName = remember {
                            mutableStateOf("")
                        }
                        val age = remember {
                            mutableStateOf("")
                        }


                        for (user in users.value) {
                            Greeting(
                                name = "${user.name} (id: ${user.id})",
                            )
                        }

                        Box(modifier = Modifier.padding(vertical = 20.dp))

                        TextField(
                            placeholder = {
                                Text("Enter your full name")
                            },
                            value = fullName.value,
                            onValueChange = {
                                fullName.value = it
                            }
                        )
                        Box(modifier = Modifier.padding(vertical = 5.dp))

                        TextField(
                            placeholder = {
                                Text("Enter your age")
                            },
                            value = age.value,
                            onValueChange = {
                                try {
                                    age.value = parseInt(it).toString()
                                } catch (e: NumberFormatException) {
                                    println(e.message)
                                }
                            }
                        )

                        Button(
                            modifier = Modifier.padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                            onClick = {
                                val user = User(
                                    name = fullName.value,
                                    age = age.value.toInt()
                                )
                                // dumb validation for demo
                                if(
                                    user.name.isNotEmpty() &&
                                    user.name.length > 2 &&
                                    database.userDao().getUserByName(user.name).isEmpty() &&
                                    user.age > 0
                                    ) {
                                    database.userDao().insert(user)
                                    users.value = database.userDao().getAllUsers()
                                    Toast.makeText(applicationContext, "User added", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(applicationContext, "Invalid user", Toast.LENGTH_SHORT).show()
                                }

                            }
                        ) {
                            Text(text = "Add User")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomCommonUsageLittleLemonTheme {
        Greeting("Android")
    }
}