package com.kev.yourinternetcookbook.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.kev.yourinternetcookbook.data.local.db.MealCategoriesDao
import com.kev.yourinternetcookbook.data.local.db.AppDatabase
import com.kev.yourinternetcookbook.data.local.db.FavoritesDao
import com.kev.yourinternetcookbook.data.remote.network.ApiService
import com.kev.yourinternetcookbook.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Singleton
	@Provides
	fun providesLoggingInterceptor()= HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	@Singleton
	@Provides
	fun providesOkhttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
		OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()

	@Singleton
	@Provides
	fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit{
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(okHttpClient)
			.build()
	}

	@Singleton
	@Provides
	fun providesApiService(retrofit: Retrofit) : ApiService{
		return retrofit.create(ApiService::class.java)
	}

	@Singleton
	@Provides
	fun getAppDatabase(@ApplicationContext context: Context) : AppDatabase{
		return AppDatabase.getAppDbInstance(context)
	}

	@Singleton
	@Provides
	fun getAppDao(appDatabase: AppDatabase) : MealCategoriesDao {
		return appDatabase.mealCategoriesDao()
	}

	@Singleton
	@Provides
	fun getRecipesDao(appDatabase: AppDatabase) : FavoritesDao{
		return appDatabase.favoritesDao()
	}

	@Singleton
	@Provides
	fun providesFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()
}
