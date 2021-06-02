package com.chidi.pokemongo.presentation.view

import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.ActivityMainBinding
import com.chidi.pokemongo.domain.CapturedItem
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.presentation.adapters.GoActivitiesPagerAdapter
import com.chidi.pokemongo.presentation.model.LocalStorageViewModel
import com.chidi.pokemongo.presentation.model.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private lateinit var activitiesPagerAdapter: GoActivitiesPagerAdapter

    private val viewModel: MainViewModel by viewModels()
    private val localViewModel: LocalStorageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpTransition()
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
        refreshToken()
        clearAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // release binding when activity is destroyed
    }

    /**
     *  Get Token
     */
    private fun refreshToken() {
        viewModel.getToken()
        viewModel.token.observe(this, {
            if (it != null) {
                if (it.token.isNotEmpty()) {
                    loadCloudFunctions()
                    setUpObservers()
                }
            }
        })
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
        //binding?.activitiesViewPager?.isUserInputEnabled = false
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

    /**
     *  Get rid of outdated data before adding new ones
     */
    private fun clearAll() {
        with(localViewModel) {
            deleteAllCommunity()
            releaseAllCaptured()
        }
    }

    private fun loadCloudFunctions() {
        with(viewModel) {
            getActivity()
            myTeam()
            getCapturePokemons()
        }
    }

    private fun setUpObservers() {

        viewModel.activity.observe(this, {
            if (it != null) {
                it.friends.forEach { friend ->
                    localViewModel.saveCommunity(CommunityItem(friend.name, friend.pokemon.id, friend.pokemon.name, friend.pokemon.captured_at, true))
                }
                it.foes.forEach { foe ->
                    localViewModel.saveCommunity(CommunityItem(foe.name, foe.pokemon.id, foe.pokemon.name, foe.pokemon.captured_at, false))
                }
            }
        })

        viewModel.captured.observe(this, {
            it?.forEach { captured ->
                localViewModel.saveCapturedPokemonToLocalStorage(
                    CapturedItem(
                        captured.name,
                        captured.captured_long_at,
                        captured.captured_lat_at,
                        captured.captured_at,
                        captured.id
                    )
                )
            }
        })
    }

    private fun setUpTransition() {
        val fade = Fade()
        val decor: View = window.decorView
        //fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        window.enterTransition = fade
        window.exitTransition = fade

    }


}