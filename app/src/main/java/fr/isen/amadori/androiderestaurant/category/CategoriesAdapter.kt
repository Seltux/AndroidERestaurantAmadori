package fr.isen.amadori.androiderestaurant.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.amadori.androiderestaurant.databinding.RepasMenuBinding
import fr.isen.amadori.androiderestaurant.model.Dish

class CategoriesAdapter(private val categories: List<Dish>, private val categoriesClickListener:(String) -> Unit): RecyclerView.Adapter<CategoriesAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryHolder {
        val itemBinding = RepasMenuBinding.inflate(LayoutInflater.from(p0.context),p0,false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(p0: CategoryHolder, p1: Int) {
        p0.title.text = categories[p1].title
        p0.layout.setOnClickListener{
            categoriesClickListener.invoke(categories[p1].title)
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoryHolder(binding: RepasMenuBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.idNomRepas
        val layout = binding.root

    }
}