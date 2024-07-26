package cc.spie.bangumix.ui.components.subject

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.SearchItem
import cc.spie.bangumix.ui.activities.SubjectInfoActivity
import coil.compose.AsyncImage

@Composable
fun SearchSubjectItem(item: SearchItem) {

    val context = LocalContext.current

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            val intent = Intent(context, SubjectInfoActivity::class.java).apply {
                putExtra(SubjectInfoActivity.EXTRA_SUBJECT_ID, item.id)
            }
            context.startActivity(intent)
        }) {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .width(156.dp)
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        )
        Column(verticalArrangement = Arrangement.Top) {
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(text = item.nameCn, style = MaterialTheme.typography.titleSmall)
            SubjectTypeChip(type = item.type)
            Text(text = "${item.score}/10")
        }
    }
}