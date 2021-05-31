package com.chidi.pokemongo.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chidi.pokemongo.Character
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.FragmentExploreBinding
import com.chidi.pokemongo.place.PlaceRenderer
import com.chidi.pokemongo.presentation.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {

    private var binding: FragmentExploreBinding? = null
    private lateinit var mMap: GoogleMap


    private lateinit var places: List<Character>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        places = Constants.randomCharacters
        val mapFragment = requireActivity().supportFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        lifecycleScope.launchWhenCreated {
            // Get map
            val googleMap = mapFragment?.awaitMap()

            if (googleMap != null) {
                addClusteredMarkers(googleMap)
            }

            // Wait for map to finish loading
            googleMap?.awaitMapLoad()

            // Ensure all places are visible in the map
            val bounds = LatLngBounds.builder()
            places.forEach { bounds.include(LatLng(it.rand_lat, it.rand_long)) }
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
        }

        /*mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }*/
    }

    /**
     * Adds markers to the map with clustering support.
     */
    private fun addClusteredMarkers(googleMap: GoogleMap) {
        // Create the ClusterManager class and set the custom renderer
        val clusterManager = ClusterManager<Character>(requireContext(), googleMap)
        clusterManager.renderer =
            PlaceRenderer(
                requireContext(),
                googleMap,
                clusterManager
            )

        // Set custom info window adapter
        // clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        // Add the places to the ClusterManager
        clusterManager.addItems(places)
        clusterManager.cluster()


        // When the camera starts moving, change the alpha value of the marker to translucent
        googleMap.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        googleMap.setOnCameraIdleListener {
            // When the camera stops moving, change the alpha value back to opaque
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            // Call clusterManager.onCameraIdle() when the camera stops moving so that re-clustering
            // can be performed when the camera stops moving
            clusterManager.onCameraIdle()
        }
    }

    /**
     * Adds marker representations of the places list on the provided GoogleMap object
     */
    private fun addMarkers(googleMap: GoogleMap) {
        Constants.randomCharacters.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(LatLng(place.rand_lat, place.rand_long))
            )
        }
    }

    fun getLocation(x0: Double, y0: Double, radius: Int) {
        val random = Random()

        // Convert radius from meters to degrees
        val radiusInDegrees = (radius / 111000f).toDouble()
        val u: Double = random.nextDouble()
        val v: Double = random.nextDouble()
        val w = radiusInDegrees * sqrt(u)
        val t = 2 * Math.PI * v
        val x = w * cos(t)
        val y = w * sin(t)

        // Adjust the x-coordinate for the shrinking of the east-west distances
        val newX = x / cos(Math.toRadians(y0))
        val foundLongitude = newX + x0
        val foundLatitude = y + y0
        println("Longitude: $foundLongitude  Latitude: $foundLatitude")
    }

    /* private fun loadCharactersAndSetOnMap(googleMap: GoogleMap) {
         val pokes = listOf(
             Character(1, "Bulbasaur", 20.10099206786478, 32.5910273441159),
             Character(10, "Caterpie", -7.558541622012861, 44.741980750732765),
             Character(4, "Charmander", 43.486976716307126, 47.03270976831398),
             Character(7, "Squirtle", 20.10099206786478, 21.51259421447304),
             Character(3, "Keith", 2.6735856304167953, 31.5910273441159),
         )
         val pokeballIcon = BitmapDescriptorFactory
             .fromBitmap(ResourceUtil.getBitmap(requireActivity(), R.drawable.ic_pokeball_b))
         pokes.forEach { character ->
             val markerOption = MarkerOptions()
                 .title(character.name)
                 .position(LatLng(character.rand_lat, character.rand_long))
                 .icon(pokeballIcon)
                 .anchor(0.5f, 0.5f)
                 .snippet(character.id.toString())
             googleMap.addMarker(markerOption)
         }
     }*/


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}