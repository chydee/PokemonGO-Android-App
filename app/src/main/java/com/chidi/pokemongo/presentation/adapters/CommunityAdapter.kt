package com.chidi.pokemongo.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.PokemonCommunityItemBinding
import com.chidi.pokemongo.domain.CommunityItem
import com.chidi.pokemongo.presentation.utils.getTimeAgo
import com.chidi.pokemongo.presentation.utils.timeFormat


class CommunityAdapter : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, community: CommunityItem, animItem: ImageView)
    }


    private val diffCallback = object : DiffUtil.ItemCallback<CommunityItem>() {
        override fun areItemsTheSame(oldItem: CommunityItem, newItem: CommunityItem): Boolean {
            return oldItem.capturedAt == newItem.capturedAt
        }

        override fun areContentsTheSame(oldItem: CommunityItem, newItem: CommunityItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class CommunityViewHolder(private var binding: PokemonCommunityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(community: CommunityItem) {
            binding.community = community
            Log.d("PrettyTime : ", getTimeAgo(timeFormat(community.capturedAt)))
            binding.executePendingBindings()
        }
    }

    fun submitList(list: List<CommunityItem>) {
        differ.submitList(list)
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        return CommunityViewHolder(PokemonCommunityItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

        ViewCompat.setTransitionName(holder.itemView.findViewById(R.id.pokemonCharacterImage), item.pokemonName)

        holder.itemView.setOnClickListener {
            listener.onItemClick(holder.adapterPosition, item, holder.itemView.findViewById(R.id.pokemonCharacterImage))
        }

    }

}
