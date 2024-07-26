package cc.spie.bangumix.ui.components.subject

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cc.spie.bangumix.data.models.SubjectType

@Composable
fun SubjectTypeChip(type: SubjectType, onClick: () -> Unit = {}) {
    val color = type.getColor()
    AssistChip(
        onClick = onClick,
        label = { Text(text = stringResource(id = type.label)) },
        colors = AssistChipDefaults.assistChipColors().copy(containerColor = color)
    )
}