package cc.spie.bangumix.ui.components.person

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cc.spie.bangumix.data.models.PersonDetail
import cc.spie.bangumix.ui.components.StyledCard

@Composable
fun PersonBasicInfo(person: PersonDetail, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    StyledCard(modifier = modifier) {
        Text(text = person.name)
        Text(text = person.career.joinToString(", ") { context.getString(it.label) })
        Text(text = person.getBirthday())
    }
}