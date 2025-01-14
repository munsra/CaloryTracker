package it.pierosilvestri.calorytracker.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import it.pierosilvestri.onboarding_presentation.activity.ActivityScreen
import it.pierosilvestri.onboarding_presentation.age.AgeScreen
import it.pierosilvestri.onboarding_presentation.gender.GenderScreen
import it.pierosilvestri.onboarding_presentation.goal.GoalScreen
import it.pierosilvestri.onboarding_presentation.height.HeightScreen
import it.pierosilvestri.onboarding_presentation.nutrient_goal.NUtrientGoalScreen
import it.pierosilvestri.onboarding_presentation.weight.WeightScreen
import it.pierosilvestri.onboarding_presentation.welcome.WelcomeScreen
import it.pierosilvestri.tracker_presentation.search.SearchScreen
import it.pierosilvestri.tracker_presentation.tracker_overview.TrackerOverviewScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigator(shouldShowOnBoarding: Boolean){
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        NavHost(
            navController = navController,
            startDestination = if (shouldShowOnBoarding) Route.WELCOME else Route.TRACKER_OVERVIEW
        ) {
            composable(Route.WELCOME) {
                WelcomeScreen(onNextClick = {
                    navController.navigate(Route.GENDER)
                })
            }
            composable(Route.AGE) {
                AgeScreen(
                    scaffoldState = scaffoldState,
                    onNextClick = {
                        navController.navigate(Route.HEIGHT)
                    }
                )
            }
            composable(Route.GENDER) {
                GenderScreen(onNextClick = {
                    navController.navigate(Route.AGE)
                })
            }
            composable(Route.HEIGHT) {
                HeightScreen(
                    scaffoldState = scaffoldState,
                    onNextClick = {
                        navController.navigate(Route.WEIGHT)
                    }
                )
            }
            composable(Route.WEIGHT) {
                WeightScreen(
                    scaffoldState = scaffoldState,
                    onNextClick = {
                        navController.navigate(Route.ACTIVITY)
                    }
                )
            }
            composable(Route.NUTRIENT_GOAL) {
                NUtrientGoalScreen(
                    scaffoldState = scaffoldState,
                    onNextClick = {
                        navController.navigate(Route.TRACKER_OVERVIEW)
                    }
                )
            }
            composable(Route.ACTIVITY) {
                ActivityScreen(onNextClick = {
                    navController.navigate(Route.GOAL)
                })
            }
            composable(Route.GOAL) {
                GoalScreen(onNextClick = {
                    navController.navigate(Route.NUTRIENT_GOAL)
                })
            }
            composable(Route.TRACKER_OVERVIEW) {
                TrackerOverviewScreen(
                    onNavigateToSearch = { mealname, day, month, year ->
                        navController.navigate(
                            Route.SEARCH +
                                    "/$mealname" +
                                    "/$day" +
                                    "/$month" +
                                    "/$year"
                        )
                    }
                )
            }
            composable(
                route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                arguments = listOf(
                    navArgument("mealName") {
                        type = NavType.StringType
                    },
                    navArgument("dayOfMonth") {
                        type = NavType.IntType
                    },
                    navArgument("month") {
                        type = NavType.IntType
                    },
                    navArgument("year") {
                        type = NavType.IntType
                    },
                )
            ) {
                val mealName = it.arguments?.getString("mealName")!!
                val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                val month = it.arguments?.getInt("month")!!
                val year = it.arguments?.getInt("year")!!
                SearchScreen(
                    scaffoldState = scaffoldState,
                    mealName = mealName,
                    dayOfMonth = dayOfMonth,
                    month = month,
                    year = year,
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}