package co.edu.udea.compumovil.gr02_20242.lab1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr02_20242.lab1.ui.theme.Labs20242Gr02Theme

class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labs20242Gr02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Form2Screen()
                    }
                }
            }
        }
    }
}

@Composable
fun Form2Screen(lab1ViewModel: Lab1ViewModel = viewModel()){

    val lab1UiState by lab1ViewModel.uiState.collectAsState()

    Form2Layout(
        lab1ViewModel = lab1ViewModel,
        modifier = Modifier
    )
}

@Composable
fun Form2Layout(
    lab1ViewModel: Lab1ViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    var submissionStatus by rememberSaveable { mutableStateOf("") }

    Button(onClick = { submissionStatus = lab1ViewModel.handleSubmitContactData() }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Guardar Formulario")
    }

    if(submissionStatus.isNotEmpty()){
        Text(
            text = if(submissionStatus == "Success"){
                "Registro exitoso"
            } else {
                "Por favor complete los campos"
            },
            color = if(submissionStatus == "Error"){
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.primary
            },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}