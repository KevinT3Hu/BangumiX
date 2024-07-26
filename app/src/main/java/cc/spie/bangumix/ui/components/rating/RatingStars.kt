package cc.spie.bangumix.ui.components.rating

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cc.spie.bangumix.R

@Composable
fun RatingStars(
    rating: Float,
    modifier: Modifier = Modifier,
    maxScore: Int = 10,
    starsCount: Int = 5
) {
    val fullStars = (rating / maxScore * starsCount).toInt()
    val halfStar = if (rating % maxScore >= maxScore / 2) 1 else 0
    val emptyStars = starsCount - fullStars - halfStar

    Row(modifier) {
        List(fullStars) {
            Icon(painter = painterResource(id = R.drawable.star_full), contentDescription = null)
        }
        if (halfStar == 1) {
            Icon(painter = painterResource(id = R.drawable.star_half), contentDescription = null)
        }
        List(emptyStars) {
            Icon(painter = painterResource(id = R.drawable.star_outline), contentDescription = null)
        }
    }
}