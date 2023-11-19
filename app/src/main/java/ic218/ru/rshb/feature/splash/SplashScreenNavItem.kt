package ic218.ru.rshb.feature.splash

sealed interface SplashScreenNavItem {
    object Auth: SplashScreenNavItem
    object Main: SplashScreenNavItem
}