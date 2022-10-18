package com.kev.yourinternetcookbook.ui.fragments.mealFragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.data.remote.models.Meal
import com.kev.yourinternetcookbook.databinding.FragmentMealDetailsBinding
import com.kev.yourinternetcookbook.ui.viewmodels.MealDetailsViewModel
import com.kev.yourinternetcookbook.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsFragment : Fragment(R.layout.fragment_meal_details) {
	private var _binding: FragmentMealDetailsBinding? = null
	private val binding get() = _binding!!
	private val viewModel: MealDetailsViewModel by viewModels()

	private val args: MealDetailsFragmentArgs by navArgs()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setUpObserver()

		binding.btnRetry.setOnClickListener {
			viewModel.fetchMealDetails(args.mealId)
		}
			(activity as AppCompatActivity?)!!.supportActionBar!!.hide()

		binding.toolbarDetail.setNavigationOnClickListener { requireActivity().onBackPressed() }

	}

	override fun onDestroyView() {
		super.onDestroyView()
		activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}


	private fun setUpObserver() {
		viewModel.observableMealDetailsResponse.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					binding.errorTextView.visibility = View.GONE
					binding.btnRetry.visibility = View.GONE
					binding.viewsLayout.visibility = View.VISIBLE
					binding.progressBar.visibility = View.GONE
					binding.viewsLayout.visibility = View.VISIBLE
					response.data?.let {
						val meal = it.meals[0]
						setRecipe(meal)
						saveToDatabase(meal)
					}

				}
				is Resource.Loading -> {
					binding.viewsLayout.visibility = View.GONE
					binding.progressBar.visibility = View.VISIBLE
					binding.errorTextView.visibility = View.GONE
					binding.btnRetry.visibility = View.GONE
				}

				is Resource.Error -> {
					binding.viewsLayout.visibility = View.GONE
					binding.progressBar.visibility = View.GONE
					binding.errorTextView.visibility = View.VISIBLE
					binding.errorTextView.text = response.message
					binding.btnRetry.visibility = View.VISIBLE
				}
			}
		}
	}

	private fun setRecipe(meal: Meal) {
		binding.tvTitle.text = meal.strMeal
		Glide.with(requireContext()).load(meal.strMealThumb)
			.diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.imgThumb)
		binding.tvInstructions.text = meal.strInstructions
		binding.tvYoutube.text = meal.strYoutube
		binding.tvSource.text = meal.strSource
		binding.tvSubTitle.text = meal.strArea

		if (meal.strSource?.isEmpty()!!) {
			binding.tvSource.visibility = View.GONE
			binding.tvShareRecipe.visibility = View.GONE
		}

		if (meal.strYoutube?.isEmpty()!!) {
			binding.tvYoutube.visibility = View.GONE
		}

		binding.tvShareRecipe.setOnClickListener {
			val shareIntent = Intent()
			shareIntent.action = Intent.ACTION_SEND
			shareIntent.type = "text/plain"
			shareIntent.putExtra(Intent.EXTRA_TEXT, meal.strSource)
			startActivity(Intent.createChooser(shareIntent, "Share with"))

		}

		binding.tvYoutube.setOnClickListener {
			val intentYoutube = Intent(Intent.ACTION_VIEW)
			intentYoutube.data = Uri.parse(meal.strYoutube)
			startActivity(intentYoutube)
		}

		if (!meal.strIngredient1.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient1))
		}

		if (!meal.strIngredient2.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient2))
		}


		if (!meal.strIngredient3.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient3))
		}

		if (!meal.strIngredient4.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient4))
		}

		if (!meal.strIngredient5.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient5))
		}

		if (!meal.strIngredient6.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient6))
		}

		if (!meal.strIngredient7.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient8))
		}

		if (!meal.strIngredient8.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient8))
		}

		if (!meal.strIngredient9.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient9))
		}

		if (!meal.strIngredient10.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient10))
		}

		if (!meal.strIngredient11.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient11))
		}

		if (!meal.strIngredient12.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient12))
		}

		if (!meal.strIngredient13.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient13))
		}

		if (!meal.strIngredient14.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient14))
		}

		if (!meal.strIngredient15.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient15))
		}

		if (!meal.strIngredient16.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient16))
		}

		if (!meal.strIngredient17.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient17))
		}

		if (!meal.strIngredient18.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient18))
		}

		if (!meal.strIngredient19.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient19))
		}

		if (!meal.strIngredient20.isNullOrBlank()) {
			binding.mealIngredients.append("\n \u2022 ".plus(meal.strIngredient20))
		}


		if (!meal.strMeasure1.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure1))
		}

		if (!meal.strMeasure2.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure2))
		}

		if (!meal.strMeasure3.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure3))
		}

		if (!meal.strMeasure4.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure4))
		}

		if (!meal.strMeasure5.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure5))
		}

		if (!meal.strMeasure6.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure6))
		}

		if (!meal.strMeasure7.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure7))
		}

		if (!meal.strMeasure8.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure8))
		}

		if (!meal.strMeasure9.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure9))
		}

		if (!meal.strMeasure10.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure10))
		}

		if (!meal.strMeasure11.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure11))
		}

		if (!meal.strMeasure12.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure12))
		}

		if (!meal.strMeasure13.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure13))
		}

		if (!meal.strMeasure14.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure14))
		}

		if (!meal.strMeasure15.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure16))
		}

		if (!meal.strMeasure17.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure17))
		}

		if (!meal.strMeasure18.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure18))
		}

		if (!meal.strMeasure19.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure19))
		}

		if (!meal.strMeasure20.isNullOrBlank()) {
			binding.measurementsTv.append("\n \u2022 ".plus(meal.strMeasure20))
		}

	}

	private fun saveToDatabase(meal: Meal) {
		binding.imgFavorite.setOnClickListener {
			viewModel.saveRecipe(meal)
			Toast.makeText(requireContext(), "Recipe added to favorites!", Toast.LENGTH_SHORT)
				.show()
		}
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
