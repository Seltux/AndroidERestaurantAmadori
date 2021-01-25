package fr.isen.amadori.androiderestaurant

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isen.amadori.androiderestaurant.databinding.ActivityMenuBinding

class CategoriesAdapter(private val categories: List<String>): RecyclerView.Adapter<CategoriesAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryHolder {
        val itemBinding = ActivityMenuBinding.inflate(LayoutInflater.from(p0.context),p0,false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(p0: CategoryHolder, p1: Int) {
        p0.title.text = categories[p1]
    }

    override fun getItemCount(): Int = categories.size

    class CategoryHolder(binding: ActivityMenuBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.idTitreMenu
    }
}