package com.chidi.pokemongo.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chidi.pokemongo.databinding.ActivityPokemonDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetail : AppCompatActivity() {

    private var binding: ActivityPokemonDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}