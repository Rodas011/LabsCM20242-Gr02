package co.edu.udea.compumovil.gr02_20242.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr02_20242.lab1.ui.theme.Labs20242Gr02Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Labs20242Gr02Theme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    viewModel: Lab1ViewModel = viewModel()
) {
    val lab1UiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true)}
    var shouldShowPersonalData by rememberSaveable { mutableStateOf(true)}
    var shouldShowContactData by rememberSaveable { mutableStateOf(true)}

    var personalSubmissionStatus by rememberSaveable { mutableStateOf("") }
    var contactSubmissionStatus by rememberSaveable { mutableStateOf("") }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            if (shouldShowPersonalData){
                PersonalDataScreen(onContinueClicked = {
                    keyboardController?.hide()
                    personalSubmissionStatus = viewModel.handleSubmitPersonalData()
                    if (personalSubmissionStatus == "Success") { shouldShowPersonalData = false }},
                    viewModel = viewModel, submissionStatus = personalSubmissionStatus)
            } else {
                if(shouldShowContactData) {
                    ContactDataScreen(onContinueClicked = {
                        keyboardController?.hide()
                        contactSubmissionStatus = viewModel.handleSubmitContactData()
                        if (contactSubmissionStatus == "Success") {
                            shouldShowContactData = false
                        }},
                        viewModel = viewModel, submissionStatus = contactSubmissionStatus)
                } else {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.end_form),
                            style = MaterialTheme.typography.headlineLarge,)
                    }

                }
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.welcome),
            style = MaterialTheme.typography.headlineLarge,
            )
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(stringResource(R.string.go_to_form),
                style = MaterialTheme.typography.bodyMedium,)
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    Labs20242Gr02Theme {
        MyApp(Modifier.fillMaxSize())
    }
}

