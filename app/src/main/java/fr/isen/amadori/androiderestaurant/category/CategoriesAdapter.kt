package fr.isen.amadori.androiderestaurant.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import fr.isen.amadori.androiderestaurant.R
import fr.isen.amadori.androiderestaurant.databinding.RepasMenuBinding
import fr.isen.amadori.androiderestaurant.model.Dish


class CategoriesAdapter(
    private val categories: List<Dish>,
    private val categoriesClickListener: (Dish) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryHolder {
        val itemBinding = RepasMenuBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(p0: CategoryHolder, p1: Int) {
        p0.title.text = categories[p1].title
        p0.layout.setOnClickListener {
            categoriesClickListener.invoke(categories[p1])
        }
        p0.prix_repas.text = categories[p1].getFormattedPrice()
        if(categories[p1].getFirstImage() != null) {
            Picasso.get().load(categories[p1].getFirstImage()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.logo_restaurant).error(
                R.drawable.jokes_about_italians)
                .into(p0.image_repas)
        }else{
            Picasso.get().load(R.drawable.jokes_about_italians).into(p0.image_repas)
        }
    }

    override fun getItemCount(): Int = categories.size


    class CategoryHolder(binding: RepasMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.idNomRepas
        val layout = binding.root
        val prix_repas = binding.idPrixRepas
        val image_repas = binding.idImageRepas

    }

   /* // Clean all elements of the recycler
    fun clear() {
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(list: List<Dish?>?) {
        items.addAll(list)
        notifyDataSetChanged()
    }*/
}