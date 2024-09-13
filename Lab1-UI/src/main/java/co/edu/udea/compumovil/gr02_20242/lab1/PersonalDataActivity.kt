package co.edu.udea.compumovil.gr02_20242.lab1

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
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

                        Button(onClick = {
                            val intent = Intent(this@PersonalDataActivity, ContactDataActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text("Siguiente")
                        }
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
        lastName = lab1ViewModel.lastName,
        sex = lab1ViewModel.sex,
        birthdate = lab1ViewModel.birthdate,
        scholarity = lab1ViewModel.scholarity,
        onNameChanged = { lab1ViewModel.updateName(it) },
        onLastNameChanged = { lab1ViewModel.updateLastName(it) },
        onSexChanged = { lab1ViewModel.updateSex(it) },
        onBirthdateChanged = { lab1ViewModel.updateBirthdate(it) },
        onScholarityChanged = { lab1ViewModel.updateScholarity(it) },
        lab1ViewModel = lab1ViewModel,
        modifier = Modifier
    )

}

@Composable
fun FormLayout(
    name: String,
    lastName: String,
    sex: String,
    birthdate: String,
    scholarity: String,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onSexChanged: (String) -> Unit,
    onBirthdateChanged: (String) -> Unit,
    onScholarityChanged: (String) -> Unit,
    lab1ViewModel: Lab1ViewModel = viewModel(),
    modifier: Modifier = Modifier
){

    var submissionStatus by rememberSaveable { mutableStateOf("") }
    var manSelected by rememberSaveable { mutableStateOf(false) }
    var womanSelected by rememberSaveable { mutableStateOf(false) }

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
            value = lastName,
            onValueChange = onLastNameChanged,
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
                selected = manSelected,
                onClick = { manSelected = !manSelected },
                modifier = Modifier.padding(start = 16.dp)
            )

            Text(
                text = "Hombre",
                style = MaterialTheme.typography.bodyLarge.merge(),
                modifier = Modifier.padding(start = 1.dp)
            )

            RadioButton(
                selected = womanSelected,
                onClick = { womanSelected = !womanSelected },
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
                onBirthdateChanged("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
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
                Text(text = "Cambiar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = { submissionStatus = lab1ViewModel.handleSubmitPersonalData() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Guardar Formulario")

        }

        if(manSelected){
            lab1ViewModel.updateSex("Hombre")
        }
        if(womanSelected){
            lab1ViewModel.updateSex("Mujer")
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
}

@Preview
@Composable
fun PreviewForm(){
    FormScreen()
}