package com.chidi.pokemongo.presentation.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class GoActivitiesPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private var fragments: List<Fragment> = listOf()

    internal fun setFragments(fragments: List<Fragment>) {
        this.fragments = fragments
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}