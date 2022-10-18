package com.kev.yourinternetcookbook.ui.fragments.mealFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.yourinternetcookbook.ui.adapters.MealCategoriesAdapter
import com.kev.yourinternetcookbook.databinding.FragmentMealCategoriesBinding
import com.kev.yourinternetcookbook.utils.Resource
import com.kev.yourinternetcookbook.ui.viewmodels.MealCategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealCategoriesFragment : Fragment() {
	private lateinit var myAdapter: MealCategoriesAdapter
	private var _binding: FragmentMealCategoriesBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MealCategoriesViewModel by viewModels()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnRetryMealCategories.setOnClickListener {
			retryNetworkCall()
		}

		setupRecyclerView()
		setupObserver()
	}

	private fun setupObserver() {

		viewModel.observableMealCategoriesResponse.observe(viewLifecycleOwner) { response ->

			when (response) {
				is Resource.Success -> {
					binding.btnRetryMealCategories.visibility = View.GONE
					binding.errorCategoriesTextview.visibility = View.GONE
					binding.mealCategoriesProgressbar.visibility = View.GONE
					/*response.data?.categories.let {
						myAdapter.differ.submitList(it)

					}*/

					myAdapter.differ.submitList(
						response.data
					)

				}

				is Resource.Loading -> {
					binding.mealCategoriesProgressbar.visibility = View.VISIBLE
					binding.btnRetryMealCategories.visibility = View.GONE
					binding.errorCategoriesTextview.visibility = View.GONE
				}

				is Resource.Error -> {
					binding.mealCategoriesProgressbar.visibility = View.GONE
					binding.errorCategoriesTextview.visibility = View.VISIBLE
					binding.errorCategoriesTextview.text = response.message
					binding.btnRetryMealCategories.visibility = View.VISIBLE
				}
			}
		}


	}

	//TODO Get rid of the splash activity
	// Create a splash fragment in the main activity,
	// alafu change the starting fragment to be the splash fragment.
	// Check if the user is logged in kwa splash fragment, which determines the next screeen


	private fun retryNetworkCall() {
		viewModel.getAllCategories()
	}

	private fun setupRecyclerView() {
		myAdapter = MealCategoriesAdapter()
		binding.mealCategoriesRecyclerview.apply {
			adapter = myAdapter
			layoutManager = GridLayoutManager(requireContext(), 2)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentMealCategoriesBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}


}