package it.pierosilvestri.onboarding_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import it.pierosilvestri.onboarding_domain.use_case.ValidateNutrients

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {


    // We use ViewModelScoped because it's used only in the viewModel
    @Provides
    @ViewModelScoped
    fun provideValidateNutrientsUseCase(): ValidateNutrients {
        return ValidateNutrients()
    }

}