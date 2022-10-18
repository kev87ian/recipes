package com.kev.yourinternetcookbook.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kev.yourinternetcookbook.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var navController: NavController


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.cocktailsNavHostFragment) as NavHostFragment
		navController = navHostFragment.navController


		val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
		bottomNavigationView.setupWithNavController(navController)

		navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
			if (nd.id == R.id.mealCategoriesFragment || nd.id == R.id.searchResultsFragment || nd.id == R.id.savedRecipesFragment) {
				bottomNavigationView.visibility = View.VISIBLE
			} else {
				bottomNavigationView.visibility = View.GONE
			}

			if (nd.id == R.id.splashScreenFragment){
				supportActionBar?.hide()
			}
			else{
				supportActionBar?.show()
			}

		}
	}


}