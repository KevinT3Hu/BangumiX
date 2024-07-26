package cc.spie.bangumix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.CharacterDetail
import cc.spie.bangumix.ui.components.collection.CharacterPersonItem
import cc.spie.bangumix.ui.theme.DetailPageImageHeight
import cc.spie.bangumix.ui.viewmodels.CharacterViewModel
import coil.compose.AsyncImage

@Composable
fun CharacterDetailContent(
    character: CharacterDetail,
    viewModel: CharacterViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        AsyncImage(
            model = character.images?.large,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .height(DetailPageImageHeight)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )

        StyledCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = character.name)
                character.gender?.let {
                    Text(text = stringResource(id = it))
                }
                character.bloodType?.let {
                    Text(text = it)
                }
            }
        }

        StyledCard {
            Text(text = character.summary)
        }

        StyledCard {
            Text(text = stringResource(R.string.subjects))

            val persons = viewModel.characterPersons.collectAsState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                persons.value.forEach { item ->
                    CharacterPersonItem(
                        item = item,
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        character.infoBox?.let {
            InfoBoxContent(infobox = it)
        }
    }
}