package com.chidi.pokemongo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chidi.pokemongo.databinding.PokemonTeamItemBinding
import com.chidi.pokemongo.domain.TeamItem

class MyTeamAdapter : RecyclerView.Adapter<MyTeamAdapter.MyTeamViewHolder>() {

    private lateinit var listener: OnMyTeamItemClickListener

    interface OnMyTeamItemClickListener {
        fun onItemClick(team: TeamItem)
    }


    private val diffCallback = object : DiffUtil.ItemCallback<TeamItem>() {
        override fun areItemsTheSame(oldItem: TeamItem, newItem: TeamItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TeamItem, newItem: TeamItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class MyTeamViewHolder(private var binding: PokemonTeamItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(team: TeamItem) {
            binding.team = team
            binding.executePendingBindings()
        }
    }

    fun submitList(list: List<TeamItem>) {
        differ.submitList(list)
    }

    fun setOnClickListener(listener: OnMyTeamItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTeamAdapter.MyTeamViewHolder {
        val bd = PokemonTeamItemBinding.inflate(LayoutInflater.from(parent.context))
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        bd.root.layoutParams = lp
        return MyTeamViewHolder(bd)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyTeamAdapter.MyTeamViewHolder, position: Int) {

        val item = differ.currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }

    }

}
