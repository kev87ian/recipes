package com.kev.yourinternetcookbook.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kev.yourinternetcookbook.data.remote.network.ApiService
import com.kev.yourinternetcookbook.repositories.MealsUnderCategoryRepository
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
class MealsUnderCategoryRepositoryTest {
	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@MockK
	private lateinit var apiService: ApiService

	@MockK
	private lateinit var mealsUnderCategoryRepository: MealsUnderCategoryRepository

	@Before
	fun setUp(){
		MockKAnnotations.init(this, relaxed = true)
		mealsUnderCategoryRepository = MealsUnderCategoryRepository(apiService)
	}

	@Test
	fun when_meals_under_categories_called_verify_method_from_api(){
		val categoryName = "beef"
		runBlocking {
			mealsUnderCategoryRepository.fetchMealsUnderCategory(categoryName).collect{}
			verify { runBlocking { apiService.fetchMealsUnderCategories(categoryName) } }

		}

	}

}
