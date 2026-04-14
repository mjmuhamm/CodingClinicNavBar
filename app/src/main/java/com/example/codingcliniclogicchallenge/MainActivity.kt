package com.example.codingcliniclogicchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.codingcliniclogicchallenge.ui.theme.CodingClinicLogicChallengeTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodingClinicLogicChallengeTheme {
                val viewModel: MainViewModel by viewModels()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppHost(modifier = Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}

@Composable
fun AppHost(modifier: Modifier, viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
        composable(route = "Home") {
            FirstScreen(navController = navController, viewModel)
        }
        composable(route = "Next") {
            SecondScreen(navController = navController, viewModel)
        }
    }
}

@Composable
fun FirstScreen(navController : NavController, viewModel : MainViewModel) {
    val array = remember { mutableStateListOf<Int>() }

    var value by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().background(Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Please enter a number that you would like to place in the array", modifier = Modifier.padding(20.dp))
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Number...")}
        )

        Button(onClick = {
            array.add(value.toInt())
            viewModel.update(value.toInt())
            value = ""
        }, modifier = Modifier.padding(15.dp)) {
            Text("Add value to array")
        }



        Text("Current Array", modifier = Modifier.padding(10.dp))
        LazyRow(modifier = Modifier.padding(5.dp)) {
            items(array) { item ->
                Text("$item")
            }
        }

        Button(onClick = {

            navController.navigate("Next")
        }, modifier = Modifier.padding(10.dp)) {
            Text("Click Here to Toggle to Next Screen")
        }

    }

}

@Composable
fun SecondScreen(navController: NavController, viewModel : MainViewModel) {
    val state = viewModel.array.collectAsState()

    val array = state.value
    val newArray : MutableList<Int> = mutableListOf()
    var zeroCount = 0

    for (i in 0..<array.size) {
        if (array[i] == 0) {
            zeroCount += 1

        } else {
            newArray.add(array[i])
        }
        if (i == array.size - 1) {
            for (i in 0..zeroCount) {
                newArray.add(0)
            }
        }

    }

    Column(Modifier.fillMaxSize().background(Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Filtered Array", modifier = Modifier.padding(15.dp))

        LazyRow() {
            items(newArray) { item ->
                Text("$item")
            }
        }

        Button( onClick = { navController.popBackStack(); viewModel.refreshList() }, modifier = Modifier.padding(15.dp) ) {
            Text("Go Back")
        }

    }

}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodingClinicLogicChallengeTheme {
        Greeting("Android")
    }
}