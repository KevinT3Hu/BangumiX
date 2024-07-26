package cc.spie.bangumix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.InfoBoxItem

@Composable
private fun InfoBoxText(infobox: List<InfoBoxItem>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        infobox.forEach { info ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = info.key,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Column(modifier = Modifier.weight(3f)) {
                    when (info.value) {
                        is InfoBoxItem.InfoBoxValue.StringValue -> {
                            Text(
                                text = info.value.value,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        is InfoBoxItem.InfoBoxValue.ArrayValue -> {
                            info.value.value.forEach { item ->
                                when (item) {
                                    is InfoBoxItem.KVOrV.V -> {
                                        Text(
                                            text = item.v,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    is InfoBoxItem.KVOrV.KV -> {
                                        Text(
                                            text = "${item.k}: ${item.v}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBoxContent(infobox: List<InfoBoxItem>, modifier: Modifier = Modifier) {

    var expandMore by remember { mutableStateOf(false) }

    StyledCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .sizeIn(maxHeight = 256.dp)
        ) {
            InfoBoxText(infobox = infobox)
        }

        HorizontalDivider()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = {
                expandMore = true
            }) {
                Text(text = stringResource(id = R.string.more))
            }
        }
    }

    if (expandMore) {
        ModalBottomSheet(onDismissRequest = { expandMore = false }) {
            InfoBoxText(
                infobox = infobox,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
            )
        }
    }
}