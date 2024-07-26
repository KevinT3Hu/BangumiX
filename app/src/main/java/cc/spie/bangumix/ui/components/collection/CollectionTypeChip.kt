package cc.spie.bangumix.ui.components.collection

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cc.spie.bangumix.data.models.CollectionType

@Composable
fun CollectionTypeChip(collectionType: CollectionType) {
    AssistChip(label = {
        Text(text = stringResource(id = collectionType.label))
    }, onClick = {})
}