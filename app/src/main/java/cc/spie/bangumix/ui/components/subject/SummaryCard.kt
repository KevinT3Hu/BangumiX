package cc.spie.bangumix.ui.components.subject

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.R

@Composable
fun SummaryCard(summary: String, modifier: Modifier = Modifier) {
    var summaryExpanded by remember { mutableStateOf(false) }

    var textWidth by remember {
        mutableIntStateOf(0)
    }

    Card(modifier = modifier
        .clickable {
            summaryExpanded = !summaryExpanded
        }
    ) {
        Text(text = summary, modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 16.dp)
            .animateContentSize()
            .onSizeChanged {
                textWidth = it.width
            }
            .let {
                if (!summaryExpanded) {
                    it.height(64.dp)
                } else {
                    it
                }
            })


        TextButton(
            onClick = { summaryExpanded = !summaryExpanded },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(if (summaryExpanded) R.string.less else R.string.more))
        }
    }
}