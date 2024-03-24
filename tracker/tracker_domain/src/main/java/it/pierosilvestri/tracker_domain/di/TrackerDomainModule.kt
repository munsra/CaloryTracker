package it.pierosilvestri.tracker_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import it.pierosilvestri.core.domain.preferences.Preferences
import it.pierosilvestri.tracker_domain.repository.TrackerRepository
import it.pierosilvestri.tracker_domain.use_case.CalculateMealNutrients
import it.pierosilvestri.tracker_domain.use_case.DeleteTrackedFood
import it.pierosilvestri.tracker_domain.use_case.GetFoodsForDate
import it.pierosilvestri.tracker_domain.use_case.SearchFood
import it.pierosilvestri.tracker_domain.use_case.TrackFood
import it.pierosilvestri.tracker_domain.use_case.TrackerUseCase

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCase {
        return TrackerUseCase(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
    }

}