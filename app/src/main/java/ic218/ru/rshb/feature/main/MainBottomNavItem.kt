package ic218.ru.rshb.feature.main

import ic218.ru.rshb.R

sealed class MainBottomNavItem(var title:String, var icon:Int, var screen_route:String) {
    object Tasks : MainBottomNavItem("Задачи", R.drawable.ic_tasks,"tasks")
    object CurrentTasks : MainBottomNavItem("Работа", R.drawable.ic_current_tasks,"current_tasks")
    object Statistics : MainBottomNavItem("Статистика", R.drawable.ic_stats,"stats")
    object Profile : MainBottomNavItem("Профиль", R.drawable.ic_profile,"profile")
}
