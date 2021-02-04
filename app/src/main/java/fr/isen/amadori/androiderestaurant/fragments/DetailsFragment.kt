package fr.isen.amadori.androiderestaurant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.amadori.androiderestaurant.R
import fr.isen.amadori.androiderestaurant.databinding.FragmentDetailsBinding

private lateinit var binding: FragmentDetailsBinding

class DetailsFragment : Fragment() {

    companion object{
        const val ARG = "argument"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf {
            it.containsKey(ARG).apply {
                val image = arguments?.getString(ARG)

                if(!image.isNullOrEmpty()){
                    Picasso.get().load(image).into(binding.idCarouselImage)
                }else{
                    Picasso.get().load(R.drawable.jokes_about_italians).into(binding.idCarouselImage)
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

}
