package ic218.ru.rshb.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ic218.ru.rshb.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val headerBigBoldStyle = TextStyle(
    fontSize = 25.sp,
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight(700),
    color = Color(0xFFFFFFFF)
)

val header1BoldStyle = TextStyle(
    fontSize = 20.sp,
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight(700),
    color = Color(0xFFFFFFFF)
)

val title2SlimStyle = TextStyle(
    fontSize = 18.sp,
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight(300),
    color = Color(0xB2FFFFFF)
)

val title3BoldStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight(600),
    color = Color(0xFFFFFFFF)
)

val title3MediumStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight(500),
    color = Color(0xFFFFFFFF)
)

val title3NormalStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight(400),
    color = Color(0xFFFFFFFF)
)

val title4NormalStyle = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight(400),
    color = Color(0xFFFFFFFF)
)