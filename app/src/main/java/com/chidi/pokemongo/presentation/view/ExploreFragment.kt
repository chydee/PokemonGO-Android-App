package com.chidi.pokemongo.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chidi.pokemongo.R
import com.chidi.pokemongo.presentation.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        val markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.icons_pokeball)
        // Add a marker in random location and move the camera
        val defaultPos = LatLng(1.6735856304167953, 21.51259421447304)
        mMap.addMarker(MarkerOptions().position(defaultPos).title("Pokemon").icon(markerIcon))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultPos))
        Constants.randomCharacters.forEach { pokemon ->
            mMap.addMarker(
                MarkerOptions().position(LatLng(pokemon.rand_lat, pokemon.rand_long)).title(pokemon.name).icon(markerIcon)
            )
        }

        mMap.setOnMarkerClickListener { marker ->
            Toast.makeText(requireContext(), marker.position.toString(), Toast.LENGTH_SHORT).show()
            true
        }
    }

}