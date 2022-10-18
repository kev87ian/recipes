package com.kev.yourinternetcookbook.data.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kev.yourinternetcookbook.getOrAwaitValue
import com.kev.yourinternetcookbook.data.remote.models.MockMealItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//instrumented tests arun on an android device
//small test annotation tells junit that the tests under the class are unit tests

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoritesDaoTest {

	@get:Rule

	var instantTaskExecutorRule = InstantTaskExecutorRule()

	private lateinit var database: AppDatabase
	private lateinit var dao: FavoritesDao

	@Before
	fun setup() {
		database = Room.inMemoryDatabaseBuilder(
			ApplicationProvider.getApplicationContext(),
			AppDatabase::class.java
		).allowMainThreadQueries().build()

		dao = database.favoritesDao()
	}


	//runs after the tests are over
	@After
	fun teardown() {
		database.close()
	}

	//run blocking is a way to execute coroutines on the main thread
	@Test
	fun insertRecipeItem() = runTest {
		val mockMealItem = MockMealItem("1", "Nairobi", "Kienyeji", "imageurl")
		dao.mockSaveRecipe(mockMealItem)

		val allFavorites = dao.getMockRecipeItems().getOrAwaitValue()
		assertThat(allFavorites).contains(mockMealItem)
	}


	@Test
	fun deleteRecipeItem() = runTest {
		val mockMealItem = MockMealItem("1", "Nairobi", "Kienyeji", "imageurl")
		dao.mockSaveRecipe(mockMealItem)
		dao.deleteMockRecipes(mockMealItem)

		val allRecipes = dao.getMockRecipeItems().getOrAwaitValue()

		assertThat(allRecipes).doesNotContain(mockMealItem)
	}



}
