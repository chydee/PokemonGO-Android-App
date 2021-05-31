package com.chidi.pokemongo.presentation.view

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.ActivityMainBinding
import com.chidi.pokemongo.presentation.adapters.GoActivitiesPagerAdapter
import com.chidi.pokemongo.presentation.model.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private lateinit var activitiesPagerAdapter: GoActivitiesPagerAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        activitiesPagerAdapter = GoActivitiesPagerAdapter(this@MainActivity) // create a new GoActivitiesPagerAdapter
        activitiesPagerAdapter.setFragments(
            listOf(
                ExploreFragment(),
                CommunityFragment(),
                MyTeamFragment(),
                CapturedFragment()
            )
        ) // set the fragments lists
        binding?.activitiesViewPager?.adapter = activitiesPagerAdapter
        setUpGOActivities()
        getToken()//Check TODO
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // release binding when activity is destroyed
    }

    /**
     *  Get Token
     */
    private fun getToken() {
        viewModel.getActivity()
    }

    /**
     *  Connect The activitiesTabLayout with the ViewPager2 class
     *  to display the activities
     */
    private fun setUpGOActivities() {

        binding?.activitiesTabLayout?.let { tabLayout ->
            binding?.activitiesViewPager?.let { viewPager ->
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    //To get the first name of doppelganger celebrities
                    tab.text = when (position) {
                        0 -> getString(R.string.go_activity_explore)
                        1 -> getString(R.string.go_activity_community)
                        2 -> getString(R.string.go_activity_my_team)
                        3 -> getString(R.string.go_activity_captured)
                        else -> null
                    }
                    viewPager.setCurrentItem(tab.position, true)
                }.attach()
            }
        }
        binding?.activitiesViewPager?.isUserInputEnabled = false
        // Add Margin or Spacing around TabItems
        val tabs = binding?.activitiesTabLayout?.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(16, 8, 16, 8)
            tab.layoutParams = layoutParams
            binding?.activitiesTabLayout?.requestLayout()
        }
    }
}