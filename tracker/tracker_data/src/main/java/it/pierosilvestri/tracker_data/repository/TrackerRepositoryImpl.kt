package it.pierosilvestri.tracker_data.repository

import it.pierosilvestri.tracker_data.local.TrackerDao
import it.pierosilvestri.tracker_data.mapper.toTrackableFood
import it.pierosilvestri.tracker_data.mapper.toTrackedFood
import it.pierosilvestri.tracker_data.mapper.toTrackedFoodEntity
import it.pierosilvestri.tracker_data.remote.OpenFoodApi
import it.pierosilvestri.tracker_domain.model.TrackableFood
import it.pierosilvestri.tracker_domain.model.TrackedFood
import it.pierosilvestri.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
): TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(searchDto.products
                .filter {
                    val calculatedCalories =
                        it.nutriments.carbohydrates100g * 4f +
                            it.nutriments.proteins100g * 4f +
                                it.nutriments.fat100g * 9f
                    val lowerBound = calculatedCalories * 0.99f
                    val upperBound = calculatedCalories * 1.01f
                    it.nutriments.energyKcal100g in (lowerBound..upperBound)
                }
                .mapNotNull { it.toTrackableFood() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map {
                it.toTrackedFood()
            }
        }
    }
}