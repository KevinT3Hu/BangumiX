package cc.spie.bangumix.ui.components.subject

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.ui.activities.SubjectInfoActivity
import cc.spie.bangumix.ui.components.rating.RatingStars
import coil.compose.AsyncImage

@Composable
fun RankingSubjectItem(subject: Subject, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Row(modifier = modifier.clickable {
        val intent = Intent(context, SubjectInfoActivity::class.java).apply {
            putExtra(SubjectInfoActivity.EXTRA_SUBJECT_ID, subject.id)
        }
        context.startActivity(intent)
    }, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.weight(8f)) {
            AsyncImage(
                model = subject.images.common,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.placeholder),
                modifier = Modifier
                    .width(128.dp)
                    .padding(8.dp)
            )

            Column {
                Text(text = subject.name, fontSize = TextUnit(22f, TextUnitType.Sp))
                Text(text = subject.nameCN, fontSize = TextUnit(12f, TextUnitType.Sp))

                Row {
                    RatingStars(rating = subject.rating.score.toFloat())
                    Text(text = subject.rating.score.toString())
                }
            }
        }

        Column(modifier = Modifier.weight(2f)) {
            AssistChip(onClick = { }, label = {
                val rankingText = remember {
                    buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = TextUnit(12f, TextUnitType.Sp)))
                        append("Rank ")
                        pop()
                        pushStyle(SpanStyle(fontSize = TextUnit(20f, TextUnitType.Sp)))
                        append(subject.rating.rank.toString())
                        pop()
                    }
                }
                Text(text = rankingText)
            }, colors = AssistChipDefaults.assistChipColors().copy(
                containerColor = subject.type.getColor()
            )
            )
        }
    }
}