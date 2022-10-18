package com.kev.yourinternetcookbook.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kev.yourinternetcookbook.data.remote.models.Meal
import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesItem
import com.kev.yourinternetcookbook.data.remote.models.MockMealItem

@Database(entities = [MealCategoriesItem::class, Meal::class, MockMealItem::class],version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun mealCategoriesDao(): MealCategoriesDao

	abstract fun favoritesDao() : FavoritesDao

	companion object {
		private var DB_INSTANCE: AppDatabase? = null

		fun getAppDbInstance(context: Context): AppDatabase {
			if (DB_INSTANCE == null) {
				DB_INSTANCE = Room.databaseBuilder(
					context,
					AppDatabase::class.java,
					"APP_DB"
				)
					.allowMainThreadQueries()
					.build()
			}

			return DB_INSTANCE!!
		}
	}
}