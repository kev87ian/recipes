package com.kev.yourinternetcookbook.data.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kev.yourinternetcookbook.getOrAwaitValue
import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MealCategoriesDaoTest {

	@get:Rule
	var instantTaskExecutorRule = InstantTaskExecutorRule()

	private lateinit var database: AppDatabase
	private lateinit var dao: MealCategoriesDao

	@Before
	fun setup() {
		database = Room.inMemoryDatabaseBuilder(
			ApplicationProvider.getApplicationContext(),
			AppDatabase::class.java
		).allowMainThreadQueries().build()

		dao = database.mealCategoriesDao()
	}

	@After
	fun teardown() {
		database.close()
	}

	@Test
	fun insertCategories() = runTest {
		val category = MealCategoriesItem(1, "1", "beef", "thumbNail", "beefHatarri mno")
		dao.insertRecords(category)
		val allCategories = dao.getAllRecords().getOrAwaitValue()
		assertThat(allCategories).contains(category)
	}


}