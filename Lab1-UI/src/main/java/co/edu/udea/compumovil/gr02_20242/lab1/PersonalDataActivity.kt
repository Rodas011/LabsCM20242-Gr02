package co.edu.udea.compumovil.gr02_20242.lab1

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr02_20242.lab1.ui.theme.Labs20242Gr02Theme
import java.util.Calendar

class PersonalDataActivity : ComponentActivity() {
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
                        FormScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun FormScreen(lab1ViewModel: Lab1ViewModel = viewModel()){

    val lab1UiState by lab1ViewModel.uiState.collectAsState()

    FormLayout(
        name = lab1ViewModel.name,
        onNameChanged = { lab1ViewModel.updateName(it) },
        modifier = Modifier
    )

}

@Composable
fun FormLayout(
    name: String,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var apellido by remember { mutableStateOf(TextFieldValue("")) }
    var submissionStatus by remember { mutableStateOf("") }
    var isSelected by remember { mutableStateOf(false) }
    var fechaSeleccionada by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    Column(
        modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Formulario",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 110.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sexo:",
                style = MaterialTheme.typography.bodyLarge.merge(),
                modifier = Modifier.padding(start = 1.dp)
            )

            RadioButton(
                selected = isSelected,
                onClick = { isSelected = true },
                modifier = Modifier.padding(start = 16.dp)
            )

            Text(
                text = "Hombre",
                style = MaterialTheme.typography.bodyLarge.merge(),
                modifier = Modifier.padding(start = 1.dp)
            )

            RadioButton(
                selected = isSelected,
                onClick = { isSelected = true },
                modifier = Modifier.padding(start = 30.dp)
            )

            Text(
                text = "Mujer",
                style = MaterialTheme.typography.bodyLarge.merge(),
                modifier = Modifier.padding(start = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                // Actualizar la fecha seleccionada
                fechaSeleccionada = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            }, year, month, day
        )

        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = "Fecha de nacimiento:",
                style = MaterialTheme.typography.bodyLarge.merge()
            )

            Button(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "Seleccionar fecha")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = { submissionStatus = handleSubmit(name, apellido.text) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Guardar Formulario")

        }

        if(submissionStatus.isNotEmpty()){
            Text(
                text = submissionStatus,
                color = if(submissionStatus == "Registro exitoso"){
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


fun handleSubmit(name: String, apellido: String): String{
    if(name.isEmpty() || apellido.isEmpty()){
        return "Please fill all the fields"
    } else {
        /**
        println("Nombre: $nombre")
        println("Apellido: $apellido")
         */
        Log.d("mensaje", "Nombre $name")
        Log.d("mensaje", "Apellido $apellido")
        return "Registro exitoso"
    }
}

@Preview
@Composable
fun PreviewForm(){
    FormScreen()
}