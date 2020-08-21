package com.example.ddareungi.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ddareungi.data.StationRe
import com.example.ddareungi.databinding.BookmarkSingleItemBinding


class BookmarksAdapter(private val delegate: BookmarkViewHolder.Delegate)
    : ListAdapter<StationRe, BookmarksAdapter.BookmarkViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder.from(parent, delegate)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class BookmarkViewHolder(private val binding: BookmarkSingleItemBinding, private val delegate: Delegate)
        : RecyclerView.ViewHolder(binding.root) {

        interface Delegate {
            fun onItemClick(station: StationRe)
        }

        fun bind(item: StationRe) {
            binding.bikeStation = item
            binding.root.setOnClickListener{ delegate.onItemClick(item) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup?, delegate: Delegate): BookmarkViewHolder {
                val layoutInflater = LayoutInflater.from(parent!!.context)
                val binding = BookmarkSingleItemBinding.inflate(layoutInflater, parent, false)

                return BookmarkViewHolder(binding, delegate)
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<StationRe>() {
        override fun areItemsTheSame(oldItem: StationRe, newItem: StationRe): Boolean {
            return oldItem.stationId == newItem.stationId
        }

        override fun areContentsTheSame(oldItem: StationRe, newItem: StationRe): Boolean {
            return oldItem == newItem
        }
    }
}