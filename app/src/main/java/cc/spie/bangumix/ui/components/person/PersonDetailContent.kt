package cc.spie.bangumix.ui.components.person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.PersonDetail
import cc.spie.bangumix.ui.components.InfoBoxContent
import cc.spie.bangumix.ui.theme.DetailPageImageHeight
import coil.compose.AsyncImage

@Composable
fun PersonDetailContent(person: PersonDetail, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        AsyncImage(
            model = person.images?.large,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(DetailPageImageHeight)
                .padding(vertical = 16.dp)
        )

        PersonBasicInfo(person = person)

        person.infoBox?.let {
            InfoBoxContent(infobox = it)
        }
    }
}