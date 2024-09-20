package co.edu.udea.compumovil.gr02_20242.lab1

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun PersonalDataScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: Lab1ViewModel,
    submissionStatus: String
){
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PersonalDataLayout(
                onContinueClicked = onContinueClicked,
                submissionStatus = submissionStatus,
                name = viewModel.name,
                lastName = viewModel.lastName,
                sex = viewModel.sex,
                birthdate = viewModel.birthdate,
                scholarity = viewModel.scholarity,
                onNameChanged = { viewModel.updateName(it) },
                onLastNameChanged = { viewModel.updateLastName(it) },
                onSexChanged = { viewModel.updateSex(it) },
                onBirthdateChanged = { viewModel.updateBirthdate(it) },
                onScholarityChanged = { viewModel.updateScholarity(it) },
                snackbarHostState = snackbarHostState,
                viewModel = viewModel,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PersonalDataLayout(
    onContinueClicked: () -> Unit,
    submissionStatus: String,
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
    snackbarHostState: SnackbarHostState,
    viewModel: Lab1ViewModel,
    modifier: Modifier = Modifier
){
    val configuration = LocalConfiguration.current

    var manSelected by rememberSaveable { mutableStateOf(false) }
    var womanSelected by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val sexOptions = listOf(stringResource(R.string.male), stringResource(R.string.female))
    val scholarities = listOf(
        stringResource(R.string.primary),
        stringResource(R.string.high_school),
        stringResource(R.string.college)
    )

    var isNameTouched by remember { mutableStateOf(false) }
    var isLastNameTouched by remember { mutableStateOf(false) }
    var isSexTouched by remember { mutableStateOf(false) }
    var isBirthdateTouched by remember { mutableStateOf(false) }
    var isScholarityTouched by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.personal_info),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChanged,
                    label = { Text(stringResource(R.string.name)) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next),

                    isError = isNameTouched && name.isBlank(),
                    supportingText = { if (isNameTouched && name.isBlank()) Text(stringResource(R.string.required)) else null },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isNameTouched = focusState.isFocused || focusState.hasFocus
                        }
                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = onLastNameChanged,
                    label = { Text(stringResource(R.string.last_name)) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next),

                    isError = isLastNameTouched && lastName.isBlank(),
                    supportingText = { if (isLastNameTouched && lastName.isBlank()) Text(stringResource(R.string.required)) else null },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isLastNameTouched = focusState.isFocused || focusState.hasFocus
                        }
                )
            }
        }else {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChanged,
                label = { Text(stringResource(R.string.name)) },
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next),

                isError = isNameTouched && name.isBlank(),
                supportingText = { if (isNameTouched && name.isBlank()) Text(stringResource(R.string.required)) else null },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isNameTouched = focusState.isFocused || focusState.hasFocus
                    }
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = onLastNameChanged,
                label = { Text(stringResource(R.string.last_name)) },
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next),

                isError = isLastNameTouched && lastName.isBlank(),
                supportingText = { if (isLastNameTouched && lastName.isBlank()) Text(stringResource(R.string.required)) else null },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isLastNameTouched = focusState.isFocused || focusState.hasFocus
                    }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.then(
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Modifier.align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                        .height(56.dp)
                } else {
                    Modifier.padding(horizontal = 16.dp)
                        .height(56.dp)
                }
            )
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Sex",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 1.dp, end = 5.dp)
            )

            Text(
                text = stringResource(R.string.sex),
                style = MaterialTheme.typography.bodyLarge.merge(),

                )

            sexOptions.forEach { option ->

                RadioButton(
                    selected = (option == sex),
                    onClick = { onSexChanged(option) },
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium.merge(),
                    modifier = Modifier.padding(start = 1.dp)
                )
            }

        }

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                onBirthdateChanged("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
            }, year, month, day
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.then(
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Modifier.align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                        .height(56.dp)
                } else {
                    Modifier.padding(horizontal = 16.dp)
                        .height(56.dp)
                }
            )
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 1.dp, end = 5.dp)
            )

            Text(
                text = stringResource(R.string.birthdate),
                style = MaterialTheme.typography.bodyLarge.merge()
            )

            Button(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.change),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        ScholarityDropdown(scholarity, scholarities, onScholarityChanged)

        Spacer(modifier = Modifier.height(6.dp))

        Button(
            onClick = onContinueClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string._continue),
                style = MaterialTheme.typography.bodyMedium.merge(),
            )
        }

        if (manSelected) {
            viewModel.updateSex("Hombre")
        }
        if (womanSelected) {
            viewModel.updateSex("Mujer")
        }

        if (submissionStatus.isNotEmpty()) {
            val snackbarMessage = if (submissionStatus == "Success") {
                stringResource(R.string.success_message)
            } else {
                stringResource(R.string.error_message)
            }
            if (submissionStatus == "Error") {
                isNameTouched = true
                isLastNameTouched = true
                isSexTouched = true
                isBirthdateTouched = true
                isScholarityTouched = true
            }
            LaunchedEffect(submissionStatus) {
                snackbarHostState.showSnackbar(
                    message = snackbarMessage,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScholarityDropdown(
    scholarity: String,
    scholarities: List<String>,
    onScholaritySelected: (String) -> Unit)
{
    var expanded by remember { mutableStateOf(false) }
    var selected = scholarity
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.scholarity)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            scholarities.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onScholaritySelected(item)
                        selected = item
                        expanded = false
                    }
                )
            }
        }
    }
}