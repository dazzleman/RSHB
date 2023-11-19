package ic218.ru.rshb.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ic218.ru.rshb.R
import ic218.ru.rshb.ui.theme.header1BoldStyle
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title3NormalStyle
import ic218.ru.rshb.ui.theme.title4NormalStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    onClickLogOff: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val profile = viewModel.profile

    Column(
        modifier = Modifier
            .padding(top = 26.dp, bottom = 12.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Профиль",
            style = headerBigBoldStyle
        )

        if (profile != null) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 25.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = profile.username,
                        style = header1BoldStyle
                    )
                    Text(
                        text = profile.jobtitle,
                        style = title4NormalStyle,
                        color = Color(0x80FFFFFF)
                    )
                }

                Image(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(70.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.image_default_avatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier
                        .padding(top = 120.dp),
                    text = profile.phone,
                    style = title3NormalStyle,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clickable {
                    viewModel.logOff()
                    onClickLogOff.invoke()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Выйти из профиля",
                style = title3NormalStyle
            )
        }
    }
}