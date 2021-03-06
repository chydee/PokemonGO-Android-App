package com.chidi.pokemongo.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.FragmentCommunityBinding
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.presentation.adapters.CommunityAdapter
import com.chidi.pokemongo.presentation.model.LocalStorageViewModel
import com.chidi.pokemongo.presentation.utils.Constants
import com.chidi.pokemongo.presentation.utils.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class CommunityFragment : Fragment() {

    private var binding: FragmentCommunityBinding? = null

    private val viewModel: LocalStorageViewModel by viewModels()
    private lateinit var adapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommunityBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        getActivityFromDatabase()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getActivityFromDatabase() {
        viewModel.getFriends() // Get Friends Activity
        viewModel.getFoes() // Get Foes Activity
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.friends.observe(viewLifecycleOwner, {
            if (it != null) {
                setUpFriendsRecyclerView(it)
            }
        })

        viewModel.foes.observe(viewLifecycleOwner, {
            if (it != null) {
                setUpFoesRecyclerView(it)
            }
        })
    }

    private fun setUpFriendsRecyclerView(friends: List<CommunityItem>) {
        adapter = CommunityAdapter()
        adapter.submitList(friends)
        binding?.friendsRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.friendsRecyclerView?.adapter = adapter
        binding?.friendsRecyclerView?.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))



        adapter.setOnClickListener(object : CommunityAdapter.OnItemClickListener {

            override fun onItemClick(position: Int, community: CommunityItem, animItem: ImageView) {
                navigateToDetailsActivity(community, animItem)
            }
        })
    }

    private fun setUpFoesRecyclerView(foes: List<CommunityItem>) {
        adapter = CommunityAdapter()
        adapter.submitList(foes)
        binding?.foesRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.foesRecyclerView?.adapter = adapter
        binding?.foesRecyclerView?.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))

        adapter.setOnClickListener(object : CommunityAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, community: CommunityItem, animItem: ImageView) {
                navigateToDetailsActivity(community, animItem)
            }
        })
    }

    private fun navigateToDetailsActivity(communityItem: CommunityItem, sharedImageView: ImageView) {
        val intent = Intent(requireContext(), PokemonDetail::class.java).apply {
            putExtra(Constants.EXTRA_POKEMON_TYPE, Constants.TYPE_CAPTURED_BY_OTHER)
            putExtra(Constants.EXTRA_COMMUNITY, communityItem)
            putExtra(Constants.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView))
        }
        val options: ActivityOptionsCompat? = ViewCompat.getTransitionName(sharedImageView)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                sharedImageView,
                it
            )
        }

        startActivity(intent, options?.toBundle())

    }

}