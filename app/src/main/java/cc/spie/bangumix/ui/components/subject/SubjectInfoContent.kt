package cc.spie.bangumix.ui.components.subject

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.ui.components.InfoBoxContent
import cc.spie.bangumix.ui.components.StyledCard
import cc.spie.bangumix.ui.components.rating.RatingCard
import cc.spie.bangumix.ui.theme.CardOuterPadding
import cc.spie.bangumix.ui.viewmodels.SubjectInfoViewModel
import cc.spie.bangumix.utils.LoginStateHolder
import cc.spie.bangumix.utils.dateFormat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import kotlinx.datetime.format

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubjectInfoContent(
    subject: Subject,
    loginStateHolder: LoginStateHolder,
    viewModel: SubjectInfoViewModel,
    modifier: Modifier = Modifier,
    onGetColor: (Color) -> Unit = {}
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val imageRequest = remember {
        ImageRequest.Builder(context)
            .data(subject.images.common)
            .allowHardware(false)
            .listener { _, result ->
                Palette.from(result.drawable.toBitmap()).generate { palette ->
                    val lightVibrant = palette?.getLightVibrantColor(0)
                    lightVibrant?.let {
                        onGetColor(Color(it))
                    }
                }
            }.build()
    }

    Column(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 16.dp, bottom = 4.dp)
        ) {
            var imageHeight by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current
            AsyncImage(
                model = imageRequest,
                placeholder = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)
                    .padding(end = 16.dp)
                    .shadow(elevation = 48.dp)
                    .onSizeChanged {
                        imageHeight = with(density) {
                            it.height.toDp()
                        }
                    }
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.height(imageHeight)
            ) {
                Column {
                    val dateString = subject.date?.format(dateFormat)
                    Text(
                        text = subject.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = subject.nameCN,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = dateString ?: "",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                if (loginStateHolder.loggedIn.value) {

                    var expandMenu by remember { mutableStateOf(false) }

                    Box {
                        if (viewModel.collection.value == null) {
                            Button(onClick = {
                                scope.launch {
                                    viewModel.modifyCollectionType(
                                        context,
                                        CollectionType.WISH
                                    )
                                }
                            }) {
                                Text(text = stringResource(R.string.add_to_wish))
                            }
                        } else {
                            FilledTonalButton(onClick = { expandMenu = true }) {
                                Text(
                                    text = stringResource(id = viewModel.collection.value!!.type.label)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = expandMenu,
                            onDismissRequest = { expandMenu = false }) {
                            CollectionType.entries.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(text = type.name) },
                                    onClick = {
                                        scope.launch {
                                            viewModel.modifyCollectionType(
                                                context,
                                                type
                                            )
                                        }
                                        expandMenu = false
                                    })
                            }
                        }
                    }

                } else {
                    Text(text = stringResource(R.string.login_to_see_collections))
                }
            }
        }

        RatingCard(
            rating = subject.rating,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        )

        Column(modifier = Modifier.padding(horizontal = 32.dp)) {

            Text(
                text = stringResource(R.string.episodes),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Episodes
            val episodesCollections =
                viewModel.episodeCollections.collectAsState()
            val episodes = viewModel.episodes.collectAsState()

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (loginStateHolder.loggedIn.value) {
                    episodesCollections.value.forEach { episode ->
                        EpisodeBlock(
                            episodeCollection = episode,
                            modifier = Modifier.size(32.dp)
                        ) { type ->
                            scope.launch {
                                val ret = viewModel.modifyEpisodeCollectionType(
                                    episode.episode.id,
                                    type,
                                    context
                                )
                                if (ret) {
                                    viewModel.requestEpisodeCollections(context)
                                }
                            }
                        }
                    }
                } else {
                    episodes.value.forEach { episode ->
                        EpisodeBlock(episode = episode, modifier = Modifier.size(32.dp))
                    }
                }
            }
        }

        SummaryCard(
            summary = subject.summary, modifier = Modifier
                .padding(CardOuterPadding)
                .fillMaxWidth()
        )

        Column {
            StyledCard {

                Text(text = stringResource(R.string.characters))

                LaunchedEffect(key1 = subject.id) {
                    viewModel.requestRelatedCharacters(context)
                }

                val characters = viewModel.relatedCharacters.collectAsState()

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .horizontalScroll(
                            rememberScrollState()
                        )
                ) {
                    characters.value.forEach { character ->
                        RelatedCharacterBlock(character = character)
                    }
                }
            }
        }

        subject.infoBox?.let {
            InfoBoxContent(infobox = it)
        }
    }
}