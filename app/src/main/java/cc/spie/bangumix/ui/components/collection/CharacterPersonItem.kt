package cc.spie.bangumix.ui.components.collection

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cc.spie.bangumix.data.models.CharacterPerson
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.ui.activities.PersonActivity
import cc.spie.bangumix.ui.activities.SubjectInfoActivity
import cc.spie.bangumix.ui.viewmodels.CharacterViewModel

@Composable
fun CharacterPersonItem(
    item: CharacterPerson,
    viewModel: CharacterViewModel,
    modifier: Modifier = Modifier
) {

    var subject by remember {
        mutableStateOf<Subject?>(null)
    }

    LaunchedEffect(key1 = item.subjectId) {
        subject = viewModel.getSubject(item.subjectId)
    }
    subject?.let { subject ->

        var expandMenu by remember {
            mutableStateOf(false)
        }

        val context = LocalContext.current

        Box(modifier = modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expandMenu = true
                    }
            ) {
                Column {
                    Row {
                        Icon(imageVector = subject.type.getIcon(), contentDescription = null)
                        Text(text = subject.name)
                    }
                    item.staff?.let {
                        AssistChip(onClick = { }, label = {
                            Text(text = it)
                        })
                    }
                }
                Text(text = item.name)
            }

            DropdownMenu(expanded = expandMenu, onDismissRequest = { expandMenu = false }) {
                DropdownMenuItem(text = {
                    Text(text = subject.name)
                }, onClick = {
                    expandMenu = false
                    val intent = Intent(context, SubjectInfoActivity::class.java).apply {
                        putExtra(SubjectInfoActivity.EXTRA_SUBJECT_ID, subject.id)
                    }
                    context.startActivity(intent)
                })

                DropdownMenuItem(text = {
                    Text(text = item.name)
                }, onClick = {
                    expandMenu = false
                    val intent = Intent(context, PersonActivity::class.java).apply {
                        putExtra(PersonActivity.EXTRA_PERSON_ID, item.id)
                    }
                    context.startActivity(intent)
                })
            }
        }
    }
}