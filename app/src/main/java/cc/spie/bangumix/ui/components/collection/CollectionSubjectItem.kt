package cc.spie.bangumix.ui.components.collection

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.data.models.UserSubjectCollection
import cc.spie.bangumix.ui.activities.SubjectInfoActivity
import cc.spie.bangumix.ui.components.subject.SubjectTypeChip
import cc.spie.bangumix.ui.viewmodels.BangumiXViewModel
import coil.compose.AsyncImage

@Composable
fun CollectionSubjectItem(item: UserSubjectCollection, viewModel: BangumiXViewModel) {

    val context = LocalContext.current

    var subject by remember {
        mutableStateOf<Subject?>(null)
    }

    LaunchedEffect(key1 = item.subjectId) {
        subject = viewModel.getSubject(item.subjectId).getOrNull()
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            val intent = Intent(context, SubjectInfoActivity::class.java).apply {
                putExtra(SubjectInfoActivity.EXTRA_SUBJECT_ID, item.subjectId)
            }
            context.startActivity(intent)
        }) {

        var imageHeight by remember {
            mutableIntStateOf(0)
        }

        // convert imageHeight to dp
        val imageHeightDp = with(LocalDensity.current) {
            imageHeight.toDp()
        }

        AsyncImage(model = subject?.images?.common,
            contentDescription = null,
            placeholder = painterResource(
                id = R.drawable.placeholder
            ),
            modifier = Modifier
                .width(128.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .onSizeChanged {
                    imageHeight = it.height
                })

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .height(imageHeightDp)
                .padding(vertical = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = subject?.name ?: "Loading...",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subject?.nameCN ?: "Loading...",
                    style = MaterialTheme.typography.titleSmall
                )
                Row {
                    subject?.type?.let {
                        SubjectTypeChip(type = it)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    CollectionTypeChip(collectionType = item.type)
                }
            }
            Column {
                Text(text = "${item.epStatus}/${subject?.totalEpisodes}")
                LinearProgressIndicator(
                    progress = {
                        subject?.totalEpisodes?.let {
                            if (it > 0) item.epStatus.toFloat() / it.toFloat()
                            else 0f
                        } ?: 0f
                    },
                )
            }
        }
    }
}