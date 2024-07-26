package cc.spie.bangumix.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cc.spie.bangumix.R
import cc.spie.bangumix.ui.components.person.PersonDetailContent
import cc.spie.bangumix.ui.theme.BangumiXTheme
import cc.spie.bangumix.ui.viewmodels.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: PersonViewModel by viewModels()

        val personId = intent.getIntExtra(EXTRA_PERSON_ID, -1)
        if (personId == -1) {
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            BangumiXTheme {

                LaunchedEffect(key1 = personId) {
                    val success = viewModel.initPerson(personId)
                    if (!success) {
                        Toast.makeText(
                            this@PersonActivity,
                            "Failed to load person",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                text = viewModel.person.value?.name
                                    ?: stringResource(id = R.string.loading)
                            )
                        }, navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        })
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (viewModel.loading.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    } else if (viewModel.person.value != null) {
                        val person = viewModel.person.value!!

                        PersonDetailContent(
                            person = person,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_PERSON_ID = "person_id"
    }
}