package cc.spie.bangumix.ui.components.subject

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.data.models.RelatedCharacter
import cc.spie.bangumix.ui.activities.CharacterActivity
import coil.compose.AsyncImage

@Composable
fun RelatedCharacterBlock(character: RelatedCharacter) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.clickable {
            val intent = Intent(context, CharacterActivity::class.java).apply {
                putExtra(CharacterActivity.EXTRA_CHARACTER_ID, character.id)
            }
            context.startActivity(intent)
        }
    ) {
        val imageUrl = character.images.small.let {
            if (it.contains("/r/")) {
                "https://lain.bgm.tv/pic/crt/g/${character.images.small.split("/l/")[1]}"
            } else {
                "https://bgm.tv/img/info_only.png"
            }
        }
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 4.dp)
        )
        Text(text = character.name, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = character.actors?.joinToString(separator = ",") { x -> x.name } ?: "",
            style = MaterialTheme.typography.bodySmall
        )
    }
}