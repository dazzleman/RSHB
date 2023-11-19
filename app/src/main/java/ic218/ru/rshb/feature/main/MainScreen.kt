package ic218.ru.rshb.feature.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ic218.ru.rshb.feature.addTask.TaskAddItemType
import ic218.ru.rshb.feature.addTask.TaskAddScreen
import ic218.ru.rshb.feature.currentTasks.CurrentTasksScreen
import ic218.ru.rshb.feature.profile.ProfileScreen
import ic218.ru.rshb.feature.search.SearchScreen
import ic218.ru.rshb.feature.statistics.StatisticsScreen
import ic218.ru.rshb.feature.taskDetail.TaskDetailScreen
import ic218.ru.rshb.feature.taskTake.TaskTakeWorkScreen
import ic218.ru.rshb.feature.tasks.TasksScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val navController = rememberNavController()

    var showSheet by remember { mutableStateOf<Int?>(null) }
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    showSheet?.also { id ->
        ModalBottomSheet(
            onDismissRequest = { showSheet = null },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            TaskTakeWorkScreen(
                taskId = id,
                onClick = { showSheet = null })
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavHost(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .fillMaxSize(),
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            navController = navController,
            startDestination = MainBottomNavItem.Tasks.screen_route
        ) {
            composable(MainBottomNavItem.Tasks.screen_route) {
                TasksScreen(navController) { id ->
                    showSheet = id
                }
            }
            composable(MainBottomNavItem.CurrentTasks.screen_route) {
                CurrentTasksScreen(navController)
            }
            composable("task_add") { backStackEntry ->
                val searchResult = backStackEntry.savedStateHandle.get<TaskAddItemType>("result")
                TaskAddScreen(navController, searchResult)
            }
            composable(
                "search/{title}/{type_index}",
                arguments = listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("type_index") { type = NavType.IntType })

            ) { backStackEntry ->
                SearchScreen(
                    navController,
                    backStackEntry.arguments?.getString("title").orEmpty(),
                    backStackEntry.arguments?.getInt("type_index") ?: 0,
                )
            }
            composable(
                "task_detail/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                TaskDetailScreen(navController, backStackEntry.arguments?.getInt("id") ?: 0)
            }
            composable(MainBottomNavItem.Statistics.screen_route) {
                StatisticsScreen()
            }
            composable(MainBottomNavItem.Profile.screen_route) {
                ProfileScreen(onClickLogOff = {
                    rootNavController.navigate("auth") {
                        popUpTo(0) {
                            inclusive = true
                            saveState = false
                        }
                    }
                })
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        MainBottomNavItem.Tasks,
        MainBottomNavItem.CurrentTasks,
        MainBottomNavItem.Statistics,
        MainBottomNavItem.Profile,
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            AddItem(
                isSelected = currentRoute == item.screen_route || isSelectedNavBar(index, currentRoute),
                screen = item,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun isSelectedNavBar(index: Int, route: String?): Boolean {
    return when (index) {
        0 -> {
            route == "task_add" || route?.contains("search") == true
        }

        1 -> {
            route?.contains("task_detail") == true
        }

        else -> false
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun RowScope.AddItem(
    isSelected: Boolean,
    screen: MainBottomNavItem,
    onClick: () -> Unit
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },
        selected = isSelected,
        alwaysShowLabel = true,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF8AE159),
            unselectedIconColor = Color(0xFF59779C),
            selectedTextColor = Color(0xFF8AE159),
            unselectedTextColor = Color(0xFF59779C),
            indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(LocalAbsoluteTonalElevation.current)
        )
    )
}