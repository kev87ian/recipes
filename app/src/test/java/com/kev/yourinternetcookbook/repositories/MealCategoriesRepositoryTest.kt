package com.kev.yourinternetcookbook.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kev.yourinternetcookbook.data.local.db.MealCategoriesDao
import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesItem
import com.kev.yourinternetcookbook.data.remote.network.ApiService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MealCategoriesRepositoryTest {
	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@MockK
	private lateinit var apiService: ApiService

	@MockK
	private lateinit var mealCategoriesDao: MealCategoriesDao

	@MockK
	private lateinit var mealCategoriesItem: MealCategoriesItem

	private lateinit var categoriesRepository: MealCategoriesRepository


	@Before
	fun setUP() {
		MockKAnnotations.init(this, relaxed = true)
		categoriesRepository = MealCategoriesRepository(apiService, mealCategoriesDao)
	}

	@Test
	fun when_get_categories_from_dao_called_verify_method() {
		runBlocking {
			categoriesRepository.fetchMealCategories().collect {}
		}
		verify { runBlocking { mealCategoriesDao.countRecords() } }
	}

	@Test
	fun when_get_categories_called_verify_method_call_from_api() {
		runBlocking {
			categoriesRepository.fetchMealCategories().collect {
			}
			verify { runBlocking { apiService.fetchMealCategories() } }
		}
	}

	@Test
	fun when_categories_inserted_into_db_verify_method() {

		runBlocking {
			categoriesRepository.insertIntoDb(mealCategoriesItem)

			verify { runBlocking { mealCategoriesDao.insertRecords(mealCategoriesItem) } }
		}

	}
}