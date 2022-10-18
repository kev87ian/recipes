package com.kev.yourinternetcookbook.ui.fragments.mealFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.yourinternetcookbook.ui.adapters.MealsUnderCategoryAdapter
import com.kev.yourinternetcookbook.databinding.FragmentMealsUnderCategoryBinding
import com.kev.yourinternetcookbook.utils.Resource
import com.kev.yourinternetcookbook.ui.viewmodels.MealsUnderCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealsUnderCategoryFragment : Fragment() {

	private lateinit var mealsUnderCategoryAdapter: MealsUnderCategoryAdapter
	private var _binding: FragmentMealsUnderCategoryBinding? = null
	private val binding get() = _binding!!

	private val args: MealsUnderCategoryFragmentArgs by navArgs()

	private val viewModel by viewModels<MealsUnderCategoryViewModel>()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val categoryName = args.categoryName

		//sets the toolbar title
		(activity as AppCompatActivity).supportActionBar?.title = args.categoryName

		binding.btnRetry.setOnClickListener {
			viewModel.fetchMealsUnderCategories(categoryName)
		}

		setupRecyclerView()
		setupObserver()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentMealsUnderCategoryBinding.inflate(inflater, container, false)
		return binding.root
	}


	private fun setupObserver() {
		viewModel.observableMealsUnderCategoriesResponse.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					binding.progressBar.visibility = View.GONE
					response.data?.meals.let {
						mealsUnderCategoryAdapter.differ.submitList(it)
					}
				}

				is Resource.Loading -> {
					binding.progressBar.visibility = View.VISIBLE
					binding.btnRetry.visibility = View.GONE
					binding.textView.visibility = View.GONE

				}
				is Resource.Error -> {
					binding.progressBar.visibility = View.GONE
					binding.textView.visibility = View.VISIBLE
					binding.textView.text = response.message
					binding.btnRetry.visibility = View.VISIBLE
				}
			}

		}
	}

	private fun setupRecyclerView() {
		mealsUnderCategoryAdapter = MealsUnderCategoryAdapter()
		binding.mealsUnderCategoryRecyclerview.apply {
			adapter = mealsUnderCategoryAdapter
			layoutManager = GridLayoutManager(requireContext(), 2)
		}

	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
