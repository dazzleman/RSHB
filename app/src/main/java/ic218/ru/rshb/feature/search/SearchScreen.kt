package ic218.ru.rshb.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ic218.ru.rshb.R
import ic218.ru.rshb.feature.addTask.TaskAddItemType
import ic218.ru.rshb.ui.theme.headerBigBoldStyle
import ic218.ru.rshb.ui.theme.title4NormalStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    title: String,
    typeIndex: Int,
    viewModel: SearchViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .padding(top = 26.dp)
            .fillMaxSize()
    ) {
        Title(title) { navController.popBackStack() }
        Search(viewModel)
        Operations(viewModel) {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("result", it)

            navController.popBackStack()
        }
    }

    LaunchedEffect(typeIndex) {
        viewModel.setType(typeIndex)
    }
}

@Composable
fun Title(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 30.dp)
                .size(30.dp)
                .clickable { onClick() },
            painter = painterResource(id = R.drawable.ic_back_ios),
            contentDescription = null,
            tint = Color(0xFF8AE1F9)
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = text.uppercase(),
            style = headerBigBoldStyle
        )
    }
}

@Composable
fun Search(viewModel: SearchViewModel) {
    var text by remember { mutableStateOf(String()) }

    TextField(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth(),
        value = text,
        placeholder = {
            Text(
                text = "Поиск",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0x59FFFFFF),
                )
            )
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color(0x1AFFFFFF),
            unfocusedIndicatorColor = Color(0x1AFFFFFF),
            cursorColor = Color(0xFF8AE159),
            focusedTextColor = Color(0xFF8AE159),
            unfocusedTextColor = Color(0xFF8AE159)
        ),
        shape = RectangleShape,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF8AE159),
        ),
        onValueChange = {
            text = it
            viewModel.search(it)
        },
    )
}

@Composable
fun Operations(
    viewModel: SearchViewModel,
    onClick: (TaskAddItemType) -> Unit
) {
    val items = viewModel.items

    LazyColumn(
        modifier = Modifier
            .padding(start = 18.dp, top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { type ->
            OperationItem(type, onClick)
        }
    }
}

@Composable
fun OperationItem(
    type: TaskAddItemType,
    onClick: (TaskAddItemType) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clickable { onClick(type) },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(

            text = type.name,
            style = title4NormalStyle
        )
    }
}