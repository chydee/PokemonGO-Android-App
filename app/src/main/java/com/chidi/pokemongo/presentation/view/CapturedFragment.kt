package com.chidi.pokemongo.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.FragmentCapturedBinding
import com.chidi.pokemongo.domain.CapturedItem
import com.chidi.pokemongo.presentation.adapters.CapturedPokemonAdapter
import com.chidi.pokemongo.presentation.model.LocalStorageViewModel
import com.chidi.pokemongo.presentation.utils.Constants
import com.chidi.pokemongo.presentation.utils.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class CapturedFragment : Fragment(), CapturedPokemonAdapter.OnCapturedItemClickListener {

    private var binding: FragmentCapturedBinding? = null

    private val viewModel: LocalStorageViewModel by viewModels()
    private lateinit var adapter: CapturedPokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCapturedBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        getCapturedItemFromDatabase()
        observeViewModelItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getCapturedItemFromDatabase() {
        viewModel.getAllCaptured()
    }

    private fun observeViewModelItems() {
        viewModel.captured.observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty()) {
                setUpCapturedRecyclerView(it)
            } else {
                Toast.makeText(context, "No Pokemon Captured", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpCapturedRecyclerView(capturedItems: List<CapturedItem>) {
        adapter = CapturedPokemonAdapter()
        adapter.submitList(capturedItems)
        binding?.capturedRecyclerView?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.capturedRecyclerView?.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        binding?.capturedRecyclerView?.adapter = adapter
        adapter.setOnClickListener(this)
    }

    override fun onItemClick(captured: CapturedItem, sharedImageView: ImageView) {
        val intent = Intent(requireContext(), PokemonDetail::class.java).apply {
            putExtra(Constants.EXTRA_POKEMON_TYPE, Constants.TYPE_CAPTURED)
            putExtra(Constants.EXTRA_CAPTURED, captured)
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