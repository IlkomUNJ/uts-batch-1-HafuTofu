package com.example.utsmocom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.utsmocom.ui.theme.UTSMOCOMTheme

data class User(
    val id: Int,
    val username: String,
    val password: String,
)

data class StudentData(
    var id: String,
    var name: String,
    var phone: String,
    var address: String,
)

enum class ScreenNav {
    LoginScreen,
    DashboardScreen,
    AddStudentScreen
}


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val user = User(1,"Admin","12345")
            UTSMOCOMTheme {
                var currentScreen by remember { mutableStateOf(ScreenNav.LoginScreen) }
                var studentList by rememberSaveable { mutableStateOf(listOf<StudentData>()) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                            if(currentScreen == ScreenNav.LoginScreen) {
                                Text("Login Screen")
                            }
                            if(currentScreen == ScreenNav.DashboardScreen){
                                Text("Dashboard Screen")
                            }
                            if(currentScreen == ScreenNav.AddStudentScreen) {
                                Text("Add Student Screen")
                            }
                        }, navigationIcon = {
                                if(currentScreen == ScreenNav.AddStudentScreen){
                                    IconButton(onClick = {
                                        currentScreen = ScreenNav.DashboardScreen
                                    }) {
                                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                    }
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        if(currentScreen == ScreenNav.DashboardScreen){
                            FloatingActionButton(
                                onClick = { currentScreen = ScreenNav.AddStudentScreen },
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ){
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "Add"
                                )
                            }
                        }
                    }

                ) { innerPadding ->
                    when(currentScreen) {
                        ScreenNav.LoginScreen -> LoginScreen(
                            modifier = Modifier.padding(innerPadding),
                            user,
                            onNavToDash = { screen -> currentScreen = screen },
                        )

                        ScreenNav.DashboardScreen -> DashboardScreen(
                            modifier = Modifier.padding(innerPadding),
                            onNavToAdd = { screen -> currentScreen = screen },
                            studentlist = studentList
                            )

                        ScreenNav.AddStudentScreen -> AddStudentScreen(
                            modifier = Modifier.padding(innerPadding),
                            studentlist = studentList
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier, user: User, onNavToDash: (ScreenNav) -> Unit){
    val username = user.username
    val password = user.password
    var usernameValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = usernameValue,
            onValueChange = { newValue ->
                usernameValue = newValue
            },
            label = { Text("Enter Username") },
            modifier = Modifier.padding(8.dp)
        )

        TextField(
            value = passwordValue,
            onValueChange = { newValue ->
                passwordValue = newValue
            },
            label = { Text("Enter Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(8.dp)
        )

        ElevatedButton(onClick = { if(usernameValue == username && passwordValue == password) {
            onNavToDash(ScreenNav.DashboardScreen)
        } }) {
            Text("LOGIN")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(modifier: Modifier = Modifier, onNavToAdd: (ScreenNav) -> Unit, studentlist: List<StudentData>){
    var studentList by rememberSaveable { mutableStateOf(studentlist) }
    Column (modifier = modifier){
        studentList.reversed().forEach { stud ->
            Box(modifier = Modifier.fillMaxWidth().height(50.dp)){
                Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()){
                   Text("${stud.name}")
                }
            }
        }
    }
}

@Composable
fun AddStudentScreen(modifier: Modifier = Modifier, studentlist: List<StudentData>){
    var studname by remember { mutableStateOf("") }
    var studid by remember { mutableStateOf("") }
    var studaddress by remember { mutableStateOf("") }
    var studphone by remember { mutableStateOf("") }
    var studentmut by remember { mutableStateOf(studentlist) }
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        TextField(
            value = studid,
            onValueChange = { newValue ->
                studid = newValue
            },
            label = { Text("Enter Id") },
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = studname,
            onValueChange = { newValue ->
                studname = newValue
            },
            label = { Text("Enter Name") },
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = studphone,
            onValueChange = { newValue ->
                studphone = newValue
            },
            label = { Text("Enter Phone") },
            modifier = Modifier.padding(8.dp)
        )
        TextField(
            value = studaddress,
            onValueChange = { newValue ->
                studaddress = newValue
            },
            label = { Text("Enter Address") },
            modifier = Modifier.padding(8.dp)
        )
        ElevatedButton(
            onClick = { studentmut = studentlist + listOf(StudentData(studid,studname,studphone,studaddress)) },
        ){
            Text("SAVE")

        }
    }
}
