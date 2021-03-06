package com.chidi.pokemongo.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.FragmentMyTeamBinding
import com.chidi.pokemongo.domain.TeamItem
import com.chidi.pokemongo.presentation.adapters.MyTeamAdapter
import com.chidi.pokemongo.presentation.model.LocalStorageViewModel
import com.chidi.pokemongo.presentation.model.MainViewModel
import com.chidi.pokemongo.presentation.utils.Constants
import com.chidi.pokemongo.presentation.utils.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class MyTeamFragment : Fragment() {

    private var binding: FragmentMyTeamBinding? = null

    private val viewModel: LocalStorageViewModel by viewModels()
    private val mainVM: MainViewModel by viewModels()
    private lateinit var adapter: MyTeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyTeamBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        getMyTeamFromLocalStorage()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getMyTeamFromLocalStorage() {
        viewModel.getMyTeam()
    }

    private fun observeViewModel() {
        viewModel.myTeam.observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty()) {
                setUpRecyclerView(it)
            } else {
                getMyTeamFromCloud()
            }
        })
        mainVM.team.observe(viewLifecycleOwner, {
            if (it != null) {
                setUpRecyclerView(it)
            }
        })
    }

    /**
     * Retrieve Captured from cloud if the local storage is empty
     */
    private fun getMyTeamFromCloud() {
        mainVM.myTeam()
    }

    private fun setUpRecyclerView(myTeam: List<TeamItem>) {
        adapter = MyTeamAdapter()
        adapter.submitList(myTeam)
        binding?.myTeamRecyclerView?.adapter = adapter
        binding?.myTeamRecyclerView?.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))

        adapter.setOnClickListener(object : MyTeamAdapter.OnMyTeamItemClickListener {
            override fun onItemClick(team: TeamItem) {
                val intent = Intent(requireContext(), PokemonDetail::class.java).apply {
                    putExtra(Constants.EXTRA_POKEMON_TYPE, Constants.TYPE_CAPTURED)
                    putExtra(Constants.EXTRA_TEAM, team)
                }
                startActivity(intent)
            }
        })
    }

}