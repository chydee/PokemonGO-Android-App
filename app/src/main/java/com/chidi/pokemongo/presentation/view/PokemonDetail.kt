package com.chidi.pokemongo.presentation.view

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.chidi.pokemongo.R
import com.chidi.pokemongo.data.remote.param.Capture
import com.chidi.pokemongo.databinding.ActivityPokemonDetailBinding
import com.chidi.pokemongo.domain.CapturedItem
import com.chidi.pokemongo.domain.Character
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.domain.TeamItem
import com.chidi.pokemongo.presentation.model.MainViewModel
import com.chidi.pokemongo.presentation.utils.AppBarStateChangeListener
import com.chidi.pokemongo.presentation.utils.Constants
import com.chidi.pokemongo.presentation.utils.State
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber


@AndroidEntryPoint
class PokemonDetail : AppCompatActivity() {

    private var binding: ActivityPokemonDetailBinding? = null

    private val viewModel: MainViewModel by viewModels()
    private var pokemonType: String? = null

    private var captured: CapturedItem? = null
    private var pokemon: Character? = null
    private var community: CommunityItem? = null
    private var team: TeamItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpTransition()
        setSupportActionBar(binding?.detailsToolBar)
        supportPostponeEnterTransition()
        getIntentExtrasAndInitializeVariables()
        val extras = intent.extras
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = extras!!.getString(Constants.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME)
            binding?.image?.transitionName = imageTransitionName
            startPostponedEnterTransition()
        }


        binding?.detailsAppBarLayout?.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.COLLAPSED -> {
                        binding?.logo?.isVisible = true
                        binding?.capturedIndicatorImageView?.isGone = true

                    }
                    State.EXPANDED -> {
                        binding?.logo?.isGone = true
                        if (pokemonType.equals(Constants.TYPE_CAPTURED))
                            binding?.capturedIndicatorImageView?.isVisible = true
                    }
                    State.IDLE -> {
                        if (pokemonType.equals(Constants.TYPE_CAPTURED))
                            binding?.capturedIndicatorImageView?.isVisible = true
                    }
                    else -> {
                        binding?.logo?.isGone = true
                        if (pokemonType.equals(Constants.TYPE_CAPTURED))
                            binding?.capturedIndicatorImageView?.isVisible = true
                    }
                }
            }
        })

        setUpOnClickListener()
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setUpOnClickListener() {
        binding?.btnCapturePokemon?.setOnClickListener {
            showAlertDialogButtonClicked()
        }
    }

    private fun getIntentExtrasAndInitializeVariables() {
        pokemonType = intent.getStringExtra(Constants.EXTRA_POKEMON_TYPE)
        pokemon = intent.getParcelableExtra(Constants.EXTRA_POKEMON)
        community = intent.getParcelableExtra(Constants.EXTRA_COMMUNITY)
        captured = intent.getParcelableExtra(Constants.EXTRA_CAPTURED)
        team = intent.getParcelableExtra(Constants.EXTRA_TEAM)

        binding?.detailsCollapsingToolBar?.title = provideDetailsTitle()
        binding?.detailsCollapsingToolBar?.setExpandedTitleColor(resources.getColor(R.color.secondaryTextColor))

        setUpLayout()
    }

    private fun provideDetailsTitle(): String? {
        return when {
            pokemon != null -> {
                pokemon?.name
            }
            community != null -> {
                community?.pokemonName
            }
            team != null -> {
                team?.name
            }
            captured != null -> {
                captured?.name
            }
            else -> {
                getString(R.string.default_pokemon_name)
            }
        }
    }

    private fun setUpLayout() {
        if (pokemonType.equals(Constants.TYPE_CAPTURED)) {
            whenPokemonIsCaptured()
        } else if (intent.getStringExtra(Constants.EXTRA_POKEMON_TYPE).equals(Constants.TYPE_CAPTURED_BY_OTHER)) {
            whenPokemonIsCapturedByOther()
        } else {// Load Default
            whenPokemonIsWild()
        }
    }


    private fun whenPokemonIsWild() {
        binding?.capturedIndicatorImageView?.visibility = View.INVISIBLE
        binding?.capturedByLayout?.capturedByLayout?.visibility = View.GONE
        binding?.btnCapturePokemon?.visibility = View.VISIBLE
        binding?.foundInLayout?.foundInTextView?.text = getString(R.string.pokemon_found_in)
    }

    private fun whenPokemonIsCaptured() {
        binding?.root?.findViewById<CircleImageView>(R.id.capturedIndicatorImageView)?.visibility = View.GONE
        binding?.capturedByLayout?.root?.visibility = View.GONE
        binding?.btnCapturePokemon?.isGone = true
        binding?.btnCapturePokemon?.visibility = View.GONE
        binding?.foundInLayout?.root?.findViewById<TextView>(R.id.foundInTextView)?.text = getString(R.string.pokemon_captured_in)
    }

    private fun whenPokemonIsCapturedByOther() {
        binding?.capturedByLayout?.root?.visibility = View.VISIBLE
        binding?.btnCapturePokemon?.visibility = View.GONE
        binding?.capturedIndicatorImageView?.visibility = View.INVISIBLE
        binding?.btnCapturePokemon?.isGone = true
        binding?.foundInLayout?.root?.visibility = View.GONE
    }

    /**
     *  uses Maps Static API service  to creates  map based on URL parameters sent through
     *  a standard HTTP request and returns the map as an image
     *
     *  The Google Maps Platform server rejected your request.
     *  You must enable Billing on the Google Cloud Project
     *  at https://console.cloud.google.com/project/_/billing/enable Learn more at https://developers.google.com/maps/gmp-get-started
     */
    private fun showPokemonCoordinatesOnMap() {
        val lat = "48.858235"
        val lng = "2.294571"
        val url =
            "http://maps.google.com/maps/api/staticmap?center=$lat,$lng&zoom=15&size=200x200&sensor=false&key=${getString(R.string.google_maps_key)}"
        Picasso.get().load(url).into(binding?.foundInLayout?.pokemonCoordinatesImageView)
    }

    private fun scaleView(v: View, startScale: Float, endScale: Float) {
        val anim: Animation = ScaleAnimation(
            1f, 1f,  // Start and end values for the X axis scaling
            startScale, endScale,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0f,  // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 1f
        ) // Pivot point of Y scaling
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = 1000
        v.startAnimation(anim)
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

    private fun showAlertDialogButtonClicked() {
        // create an alert builder
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.layout_capture_pokemon, null)
        builder.setView(customLayout)
        val dialog: AlertDialog = builder.create()
        customLayout.findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener { dialog.dismiss() }
        customLayout.findViewById<MaterialButton>(R.id.btnSave).setOnClickListener {
            captureAWildPokemon()
            dialog.dismiss()
        }
        // create and show the alert dialog
        dialog.show()
    }


    private fun captureAWildPokemon() {
        val requestBody = pokemon?.let {
            Capture.Pokemon(it.id, it.name, it.rand_lat, it.rand_long)
        }
        if (requestBody != null) {
            viewModel.capturePokemon(Capture(requestBody))
            Toast.makeText(this, "Capturing...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun releasePokemon(id: Int) {
        viewModel.releasePokemon(id)
    }

    private fun observeViewModel() {
        viewModel.capturePokemon.observe(this, {
            if (it != null) {
                //Add captured to team and show animation
            }
        })

        viewModel.releasePokemon.observe(this, {
            if (it != null) {
                Timber.d("Pokemon Released")
            }
        })
    }
}