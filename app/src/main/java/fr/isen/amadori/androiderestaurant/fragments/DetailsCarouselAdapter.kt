package fr.isen.amadori.androiderestaurant.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsCarouselAdapter(activity: AppCompatActivity, val list: List<String>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int =  list.size


    override fun createFragment(position: Int): Fragment {
        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = Bundle().apply {
            putString(DetailsFragment.ARG, list[position])
        }
        return detailsFragment
    }
}