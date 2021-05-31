// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.chidi.pokemongo.place

import android.content.Context
import androidx.core.content.ContextCompat
import com.chidi.pokemongo.Character
import com.chidi.pokemongo.R
import com.chidi.pokemongo.presentation.utils.BitmapHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

/**
 * A custom cluster renderer for Character objects.
 */
class PlaceRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Character>
) : DefaultClusterRenderer<Character>(context, map, clusterManager) {

    /**
     * The icon to use for each cluster item
     */
    private val pokeBall: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(
            context,
            R.color.secondaryColor
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.ic_pokeball_b,
            color
        )
    }

    /**
     * Method called before the cluster item (i.e. the marker) is rendered. This is where marker
     * options should be set
     */
    override fun onBeforeClusterItemRendered(item: Character, markerOptions: MarkerOptions) {
        markerOptions.title(item.name)
            .position(LatLng(item.rand_lat, item.rand_long))
            .icon(pokeBall)
    }

    /**
     * Method called right after the cluster item (i.e. the marker) is rendered. This is where
     * properties for the Marker object should be set.
     */
    override fun onClusterItemRendered(clusterItem: Character, marker: Marker) {
        marker.tag = clusterItem
    }
}