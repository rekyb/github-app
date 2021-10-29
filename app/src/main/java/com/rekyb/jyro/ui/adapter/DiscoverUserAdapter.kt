package com.rekyb.jyro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rekyb.jyro.databinding.RvUsersItemBinding
import com.rekyb.jyro.domain.model.UserItems

class DiscoverUserAdapter : RecyclerView.Adapter<DiscoverUserAdapter.ViewHolder>() {

    companion object {
        private val diffCallBack =
            object : DiffUtil.ItemCallback<UserItems>(){
                override fun areItemsTheSame(
                    oldItem: UserItems,
                    newItem: UserItems
                ): Boolean {
                    return oldItem.userId == newItem.userId
                }

                override fun areContentsTheSame(
                    oldItem: UserItems,
                    newItem: UserItems
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    class ViewHolder(val binding: RvUsersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: UserItems){
            binding.userData = items
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvUsersItemBinding.inflate(layoutInflater, parent,false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = differ.currentList[position]

        holder.apply {
            bind(items)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun renderList (newList: List<UserItems>) {
        differ.submitList(newList)
        notifyItemChanged(itemCount)
    }
}