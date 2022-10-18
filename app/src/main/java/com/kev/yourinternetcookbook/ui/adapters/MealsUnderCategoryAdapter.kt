package com.kev.yourinternetcookbook.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.data.remote.models.Meal
import com.kev.yourinternetcookbook.databinding.MealUnderCategoryItemBinding
import com.kev.yourinternetcookbook.ui.fragments.mealFragments.MealsUnderCategoryFragmentDirections

class MealsUnderCategoryAdapter :
	RecyclerView.Adapter<MealsUnderCategoryAdapter.MealsViewHolder>() {
	class MealsViewHolder(val binding: MealUnderCategoryItemBinding) :
		RecyclerView.ViewHolder(binding.root)


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
		val binding = MealUnderCategoryItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)

		return MealsViewHolder(binding)
	}

	override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
		with(holder) {
			with(differ.currentList[position]) {
				binding.mealNameTextView.text = this.strMeal
				Glide.with(holder.itemView.context).load(this.strMealThumb)
					.placeholder(R.drawable.loading).into(binding.mealImageView)
			}
		}

		holder.itemView.setOnClickListener { mView ->
			val currentRecipe = differ.currentList[position]
			val direction =
				MealsUnderCategoryFragmentDirections.actionMealsUnderCategoryFragmentToMealDetailsFragment(
					currentRecipe.idMeal
				)
			mView.findNavController().navigate(direction)
		}

	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
		override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
			return oldItem.idMeal == newItem.idMeal
		}

		override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
			return oldItem.strMeal == newItem.strMeal
		}
	}

	val differ = AsyncListDiffer(this, diffUtil)
}