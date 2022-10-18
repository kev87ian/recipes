package com.kev.yourinternetcookbook.ui.fragments.mealFragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.ui.adapters.MealSearchAdapter
import com.kev.yourinternetcookbook.databinding.FragmentSearchResultsBinding
import com.kev.yourinternetcookbook.utils.Resource
import com.kev.yourinternetcookbook.ui.viewmodels.SearchMealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMealsFragment : Fragment(R.layout.fragment_search_results) {
	private var _binding: FragmentSearchResultsBinding? = null
	private val binding get() = _binding!!
	private val viewModel: SearchMealsViewModel by viewModels()
	private lateinit var myAdapter: MealSearchAdapter



	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		//sets the toolbar title
		(activity as AppCompatActivity).supportActionBar?.title = "Search Recipes"

		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentSearchResultsBinding.bind(view)
		setupRecyclerview()

		var job: Job? = null
		binding.editSearch.addTextChangedListener {editable->
		job?.cancel()
			job = MainScope().launch {
				delay(500L)
				editable?.let {
					if (editable.toString().isNotEmpty()){
						viewModel.searchMeals(editable.toString().trim())
					}
				}
			}
		}

		setupObserver()

	}


	private fun setupRecyclerview() {
		myAdapter = MealSearchAdapter()
		binding.recyclerView.apply {
			adapter = myAdapter
			layoutManager = GridLayoutManager(requireContext(), 2)
		}
	}

	private fun setupObserver() {
		viewModel.searchMealObservableResponse.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					binding.progressBar.visibility = View.GONE
					binding.recyclerView.visibility = View.VISIBLE
					binding.errorTextView.visibility = View.GONE
					response.data?.meals.apply {
						myAdapter.differ.submitList(this)
					}

				}

				is Resource.Error -> {
					binding.errorTextView.visibility = View.VISIBLE
					binding.errorTextView.text = response.message
					binding.progressBar.visibility = View.GONE
				}

				is Resource.Loading ->{
					binding.progressBar.visibility = View.VISIBLE
					binding.errorTextView.visibility = View.GONE
					binding.recyclerView.visibility = View.GONE
				}
			}

		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}