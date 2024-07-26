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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cc.spie.bangumix.ui.components.subject.SubjectInfoContent
import cc.spie.bangumix.ui.theme.BangumiXTheme
import cc.spie.bangumix.ui.viewmodels.SubjectInfoViewModel
import cc.spie.bangumix.utils.LoginStateHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SubjectInfoActivity : ComponentActivity() {

    @Inject
    lateinit var loginStateHolder: LoginStateHolder

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val subjectId = intent.getIntExtra(EXTRA_SUBJECT_ID, -1)
        if (subjectId == -1) {
            Toast.makeText(this, "Invalid subject id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val viewModel: SubjectInfoViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            BangumiXTheme {

                LaunchedEffect(key1 = subjectId) {

                    val userName = loginStateHolder.loggedUser.value?.username

                    val success =
                        viewModel.initSubject(subjectId, this@SubjectInfoActivity, userName)
                    if (!success) {
                        Toast.makeText(
                            this@SubjectInfoActivity,
                            "Failed to get subject",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }

                    if (loginStateHolder.loggedIn.value) {
                        viewModel.requestEpisodeCollections(this@SubjectInfoActivity)
                    } else {
                        viewModel.requestEpisodes(this@SubjectInfoActivity)
                    }
                }

                var backgroundColor by remember { mutableStateOf<Color?>(null) }

                Scaffold(
                    containerColor = backgroundColor ?: MaterialTheme.colorScheme.background,
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors().copy(
                                containerColor = Color.Transparent
                            ),
                            title = { Text(text = viewModel.subject.value?.name ?: "") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    if (viewModel.loading.value) {
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    } else if (viewModel.subject.value != null) {
                        val subject = viewModel.subject.value!!

                        SubjectInfoContent(
                            subject = subject,
                            loginStateHolder = loginStateHolder,
                            viewModel = viewModel,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            backgroundColor = it
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_SUBJECT_ID = "subject_id"
    }
}