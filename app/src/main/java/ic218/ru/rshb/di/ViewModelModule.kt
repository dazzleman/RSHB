package ic218.ru.rshb.di

import ic218.ru.rshb.feature.addTask.TaskAddViewModel
import ic218.ru.rshb.feature.auth.AuthViewModel
import ic218.ru.rshb.feature.currentTasks.CurrentTasksViewModel
import ic218.ru.rshb.feature.profile.ProfileViewModel
import ic218.ru.rshb.feature.search.SearchViewModel
import ic218.ru.rshb.feature.splash.SplashViewModel
import ic218.ru.rshb.feature.statistics.StatisticsViewModel
import ic218.ru.rshb.feature.taskDetail.TaskDetailViewModel
import ic218.ru.rshb.feature.taskTake.TaskTakeWorkViewModel
import ic218.ru.rshb.feature.tasks.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::StatisticsViewModel)
    viewModelOf(::TaskDetailViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::CurrentTasksViewModel)
    viewModelOf(::TaskTakeWorkViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::TaskAddViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::AuthViewModel)
}