package com.chidi.pokemongo.presentation.view

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
import com.chidi.pokemongo.presentation.utils.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class MyTeamFragment : Fragment() {

    private var binding: FragmentMyTeamBinding? = null

    private val viewModel: LocalStorageViewModel by viewModels()
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
            }
        })
    }

    private fun setUpRecyclerView(myTeam: List<TeamItem>) {
        adapter = MyTeamAdapter()
        adapter.submitList(myTeam)
        binding?.myTeamRecyclerView?.adapter = adapter
        binding?.myTeamRecyclerView?.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))

        adapter.setOnClickListener(object : MyTeamAdapter.OnMyTeamItemClickListener {
            override fun onItemClick(team: TeamItem) {

            }
        })
    }

}