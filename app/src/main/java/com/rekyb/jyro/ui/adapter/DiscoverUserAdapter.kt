package com.rekyb.jyro.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rekyb.jyro.databinding.RvUsersItemBinding
import com.rekyb.jyro.domain.model.UserItemsModel

class DiscoverUserAdapter(private val listener: Listener) :
    RecyclerView.Adapter<DiscoverUserAdapter.ViewHolder>() {

    companion object {
        private val diffCallBack =
            object : DiffUtil.ItemCallback<UserItemsModel>() {
                override fun areItemsTheSame(
                    oldItem: UserItemsModel,
                    newItem: UserItemsModel,
                ): Boolean {
                    return oldItem.userId == newItem.userId
                }

                override fun areContentsTheSame(
                    oldItem: UserItemsModel,
                    newItem: UserItemsModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface Listener {
        fun onItemClick(view: View, data: UserItemsModel)
    }

    class ViewHolder(private val binding: RvUsersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: UserItemsModel) {
            binding.userData = items
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvUsersItemBinding.inflate(layoutInflater, parent, false)

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
            itemView.setOnClickListener {
                listener.onItemClick(it, items)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun renderList(newList: List<UserItemsModel>) {
        differ.submitList(newList)
        notifyItemChanged(itemCount)
    }
}
