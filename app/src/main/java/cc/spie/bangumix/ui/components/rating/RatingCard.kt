package cc.spie.bangumix.ui.components.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.data.models.Rating

@Composable
fun RatingCard(rating: Rating, modifier: Modifier = Modifier) {
    Card(
        modifier, colors = CardDefaults.cardColors().copy(
            containerColor = CardDefaults.cardColors().containerColor.copy(
                alpha = 0.3f
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Layout(content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "${rating.score}",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = TextUnit(
                                48f,
                                TextUnitType.Sp
                            )
                        )
                    )
                }
                RatingPlot(rating = rating)
            }, modifier = Modifier.fillMaxHeight()) { measurables, constraints ->
                // Second measurable has three times width of the first one and same height
                val sliceWidth = constraints.maxWidth / 3
                val firstPlaceable = measurables[0].measure(
                    constraints.copy(
                        maxWidth = sliceWidth,
                        maxHeight = constraints.maxHeight
                    )
                )
                val secondPlaceable = measurables[1].measure(
                    constraints.copy(
                        maxWidth = sliceWidth * 2,
                        maxHeight = firstPlaceable.height
                    )
                )

                layout(constraints.maxWidth, constraints.maxHeight) {
                    firstPlaceable.place(0, 0)
                    secondPlaceable.place(firstPlaceable.width, 0)
                }
            }
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "${rating.total} votes", style = MaterialTheme.typography.bodyMedium)
        }
    }
}