package ic218.ru.rshb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ic218.ru.rshb.feature.addTask.TaskAddItemType
import ic218.ru.rshb.ui.theme.title4NormalStyle

@Composable
fun DropSelectItem(
    type: TaskAddItemType,
    onClick: (TaskAddItemType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = type.desc,
            style = title4NormalStyle
        )

        Row(
            modifier = Modifier
                .padding(top = 2.dp)
                .height(50.dp)
                .fillMaxWidth()
                .background(color = Color(0x1AFFFFFF), shape = RoundedCornerShape(size = 5.dp))
                .padding(start = 15.dp, end = 15.dp)
                .clickable {
                    onClick(type)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when {
                    type.name.isEmpty() -> type.hint
                    else -> type.name
                },
                style = title4NormalStyle,
                color = when {
                    type.name.isEmpty() -> Color(0x80FFFFFF)
                    else -> Color(0xFF8AE159)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = Color(0xFF969696),
                contentDescription = "image description",
            )
        }
    }
}