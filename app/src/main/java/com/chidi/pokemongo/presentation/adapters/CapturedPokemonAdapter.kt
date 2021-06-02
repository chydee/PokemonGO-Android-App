package com.chidi.pokemongo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chidi.pokemongo.R
import com.chidi.pokemongo.databinding.PokemonCapturedItemBinding
import com.chidi.pokemongo.domain.CapturedItem

class CapturedPokemonAdapter : RecyclerView.Adapter<CapturedPokemonAdapter.CapturedPokemonViewHolder>() {

    private lateinit var listener: OnCapturedItemClickListener

    interface OnCapturedItemClickListener {
        fun onItemClick(captured: CapturedItem, sharedImageView: ImageView)
    }


    private val diffCallback = object : DiffUtil.ItemCallback<CapturedItem>() {
        override fun areItemsTheSame(oldItem: CapturedItem, newItem: CapturedItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CapturedItem, newItem: CapturedItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class CapturedPokemonViewHolder(private var binding: PokemonCapturedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.capturedImage.setImageResource(R.drawable.picasso_dummy)
            binding.executePendingBindings()
        }
    }

    fun submitList(list: List<CapturedItem>) {
        differ.submitList(list)
    }

    fun setOnClickListener(listener: OnCapturedItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapturedPokemonAdapter.CapturedPokemonViewHolder {
        return CapturedPokemonViewHolder(PokemonCapturedItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CapturedPokemonAdapter.CapturedPokemonViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind()

        ViewCompat.setTransitionName(holder.itemView.findViewById(R.id.capturedImage), item.name)

        holder.itemView.setOnClickListener {
            listener.onItemClick(item, holder.itemView.findViewById(R.id.capturedImage))
        }

    }

}
