package cc.spie.bangumix.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cc.spie.bangumix.ui.theme.CardInnerPadding
import cc.spie.bangumix.ui.theme.CardOuterPadding

@Composable
fun StyledCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(CardOuterPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CardInnerPadding)
        ) {
            content()
        }
    }
}