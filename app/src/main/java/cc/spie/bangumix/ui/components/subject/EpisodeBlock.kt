package cc.spie.bangumix.ui.components.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.data.models.Episode
import cc.spie.bangumix.data.models.EpisodeCollectionType
import cc.spie.bangumix.data.models.UserEpisodeCollection
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char

@Composable
fun EpisodeBlock(
    episodeCollection: UserEpisodeCollection,
    modifier: Modifier = Modifier,
    onModifyCollectionType: (EpisodeCollectionType) -> Unit
) {
    EpisodeBlock(
        episodeCollection = episodeCollection,
        episode = null,
        modifier = modifier,
        onModifyCollectionType = onModifyCollectionType
    )
}

@Composable
fun EpisodeBlock(episode: Episode, modifier: Modifier = Modifier) {
    EpisodeBlock(episode = episode, episodeCollection = null, modifier = modifier)
}

@Composable
private fun EpisodeBlock(
    episodeCollection: UserEpisodeCollection?,
    episode: Episode?,
    modifier: Modifier = Modifier,
    onModifyCollectionType: (EpisodeCollectionType) -> Unit = {}
) {
    if (episodeCollection == null && episode == null) {
        return
    }
    val ep = episodeCollection?.episode ?: episode!!

    var expandMenu by remember {
        mutableStateOf(false)
    }

    val dateFormat = LocalDate.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
    }

    Box {
        Box(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = episodeCollection?.type?.getColor()
                        ?: MaterialTheme.colorScheme.primaryContainer
                )
                .aspectRatio(1f)
                .clickable {
                    expandMenu = !expandMenu
                }
        ) {
            Text(text = "${ep.ep}", modifier = Modifier.align(Alignment.Center))
        }

        DropdownMenu(expanded = expandMenu, onDismissRequest = { expandMenu = false }) {
            Text(text = ep.name, modifier = Modifier.padding(start = 4.dp))
            Text(
                text = ep.airDate?.format(dateFormat) ?: "",
                modifier = Modifier.padding(start = 4.dp)
            )
            EpisodeCollectionType.entries.forEach {
                DropdownMenuItem(text = { Text(text = stringResource(id = it.label)) }, onClick = {
                    onModifyCollectionType(it)
                    expandMenu = false
                })
            }
        }
    }
}