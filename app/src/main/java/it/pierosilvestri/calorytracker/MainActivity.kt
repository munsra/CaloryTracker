package it.pierosilvestri.calorytracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import it.pierosilvestri.calorytracker.navigation.Navigator
import it.pierosilvestri.core.domain.preferences.Preferences
import it.pierosilvestri.calorytracker.navigation.Route
import it.pierosilvestri.core_ui.CaloryTrackerTheme
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnBoarding = preferences.loadShouldShowOnboarding()
        setContent {
            CaloryTrackerTheme {
                Navigator(shouldShowOnBoarding)
            }
        }
    }
}