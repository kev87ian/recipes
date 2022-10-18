package com.kev.yourinternetcookbook.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kev.yourinternetcookbook.R
import com.kev.yourinternetcookbook.databinding.MealCategoriesLayoutItemBinding
import com.kev.yourinternetcookbook.data.remote.models.MealCategoriesItem
import com.kev.yourinternetcookbook.ui.fragments.mealFragments.MealCategoriesFragmentDirections

class MealCategoriesAdapter : RecyclerView.Adapter<MealCategoriesAdapter.MealsViewHolder>() {

	class MealsViewHolder(val binding: MealCategoriesLayoutItemBinding) :
		RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
		val binding = MealCategoriesLayoutItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return MealsViewHolder(binding)
	}

	override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
		with(holder) {
			with(differ.currentList[position]) {
				binding.categoryNameTextView.text = this.strCategory
				Glide.with(holder.itemView.context).load(this.strCategoryThumb).placeholder(R.drawable.loading)
					.into(binding.categoryImage)
			}
		}

		holder.itemView.setOnClickListener { mView ->
			val currentCategory = differ.currentList[position]
			val direction = MealCategoriesFragmentDirections
				.actionMealCategoriesFragmentToMealsUnderCategoryFragment(currentCategory.strCategory)
			mView.findNavController().navigate(direction)

		}
	}
	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	private val diffUtil = object : DiffUtil.ItemCallback<MealCategoriesItem>() {
		override fun areItemsTheSame(
			oldItem: MealCategoriesItem,
			newItem: MealCategoriesItem
		): Boolean {
			return oldItem.idCategory == newItem.idCategory
		}

		override fun areContentsTheSame(
			oldItem: MealCategoriesItem,
			newItem: MealCategoriesItem
		): Boolean {
			return oldItem.strCategory == newItem.strCategory
		}
	}
	val differ = AsyncListDiffer(this, diffUtil)
}