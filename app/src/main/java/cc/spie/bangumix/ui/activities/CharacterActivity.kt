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
import cc.spie.bangumix.ui.components.CharacterDetailContent
import cc.spie.bangumix.ui.theme.BangumiXTheme
import cc.spie.bangumix.ui.viewmodels.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: CharacterViewModel by viewModels()

        val characterId = intent.getIntExtra(EXTRA_CHARACTER_ID, -1)
        if (characterId == -1) {
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            BangumiXTheme {

                LaunchedEffect(key1 = characterId) {
                    val success = viewModel.initCharacter(characterId, this@CharacterActivity)
                    if (!success) {
                        Toast.makeText(
                            this@CharacterActivity,
                            getString(R.string.failed_to_load_character), Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }

                    val success2 =
                        viewModel.initCharacterPersons(characterId, this@CharacterActivity)
                    if (!success2) {
                        Toast.makeText(
                            this@CharacterActivity,
                            getString(R.string.failed_to_load_character_persons), Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            },
                            title = {
                                Text(
                                    text = viewModel.character.value?.name ?: stringResource(
                                        R.string.loading
                                    )
                                )
                            },
                        )
                    }) { innerPadding ->
                    if (viewModel.loading.value) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    } else if (viewModel.character.value != null) {
                        val character = viewModel.character.value!!

                        CharacterDetailContent(
                            character = character,
                            viewModel = viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_CHARACTER_ID = "character_id"
    }
}