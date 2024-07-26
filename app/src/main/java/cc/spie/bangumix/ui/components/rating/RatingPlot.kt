package cc.spie.bangumix.ui.components.rating

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.data.models.Rating

@Composable
fun RatingPlot(rating: Rating, plotPadding: Float = 48f) {

    Canvas(
        modifier = Modifier
            .padding(bottom = 8.dp, top = 16.dp)
            .fillMaxSize()
    ) {
        val xLabels = rating.count.keys
        val yValues = rating.count.values.toList()

        val yValueMax = yValues.maxOrNull() ?: 0
        val yStep = size.height / yValueMax
        val yValuesNormalized = yValues.map { it.toFloat() * yStep }

        val xStep = (size.width - plotPadding * 2) / xLabels.size
        val xLabelOffset = 10f

        drawLine(
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 2f,
            color = Color.Gray
        )

        drawLine(
            start = Offset(plotPadding, size.height),
            end = Offset(size.width - plotPadding, size.height),
            strokeWidth = 2f,
            color = Color.Gray
        )


        for ((index, _) in xLabels.withIndex()) {
            drawLine(
                start = Offset(plotPadding + xStep * index + xStep / 2, size.height),
                end = Offset(plotPadding + xStep * index + xStep / 2, size.height + xLabelOffset),
                strokeWidth = 2f,
                color = Color.Gray
            )

            drawLine(
                start = Offset(plotPadding + xStep * index + xStep / 2, size.height),
                end = Offset(
                    plotPadding + xStep * index + xStep / 2,
                    size.height - yValuesNormalized[index]
                ),
                strokeWidth = 40f,
                color = Color.Blue
            )
        }
    }
}