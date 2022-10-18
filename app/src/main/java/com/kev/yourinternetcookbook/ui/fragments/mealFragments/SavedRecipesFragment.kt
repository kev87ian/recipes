package com.kev.yourinternetcookbook.ui.fragments.mealFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.ui.adapters.FavoriteRecipesAdapter
import com.kev.yourinternetcookbook.databinding.FragmentSavedRecipesBinding
import com.kev.yourinternetcookbook.ui.viewmodels.SavedMealsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SavedRecipesFragment : Fragment(R.layout.fragment_saved_recipes) {

	private var _binding: FragmentSavedRecipesBinding? = null
	private val binding get() = _binding!!
	private val viewModel: SavedMealsViewModel by viewModels()
	private lateinit var myAdapter: FavoriteRecipesAdapter


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSavedRecipesBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//sets the toolbar title
		(activity as AppCompatActivity).supportActionBar?.title = "Your Favorites"

		setUpAdapter()
		setupObserver()
		swipeToDelete()
		checkIfDbIsEmpty()
	}


	private fun swipeToDelete() {
		val itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(
			ItemTouchHelper.UP or ItemTouchHelper.DOWN,
			ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
		) {
			override fun onMove(
				recyclerView: RecyclerView,
				viewHolder: RecyclerView.ViewHolder,
				target: RecyclerView.ViewHolder
			): Boolean {
				return true
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				val position = viewHolder.absoluteAdapterPosition
				val meal = myAdapter.differ.currentList[position]
				viewModel.deleteMealsDatabase(meal)

				checkIfDbIsEmpty()

			}
		}

		ItemTouchHelper(itemTouchCallBack).apply {
			attachToRecyclerView(binding.recyclerView)
		}
	}

	private fun checkIfDbIsEmpty() {
		val itemCount = viewModel.checkIfDbIsEmpty()
			if (itemCount.isEmpty()){
			binding.emptyTv.visibility = View.VISIBLE
			}


	}

	private fun setupObserver() {
		viewModel.fetchMealsFromDatabase().observe(viewLifecycleOwner) {
			myAdapter.differ.submitList(it)

		}


	}

	private fun setUpAdapter() {
		myAdapter = FavoriteRecipesAdapter()
		binding.recyclerView.apply {
			adapter = myAdapter
			layoutManager = LinearLayoutManager(requireContext())
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}