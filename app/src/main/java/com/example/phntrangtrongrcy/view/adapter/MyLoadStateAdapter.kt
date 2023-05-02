package com.example.phntrangtrongrcy.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.phntrangtrongrcy.R

class MyLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MyLoadStateAdapter.MyLoadStateAdapterViewHolder>() {
    class MyLoadStateAdapterViewHolder(itemView: View, private val retry: () -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val button = itemView.findViewById<Button>(R.id.btn_retry)
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)

        init {
            button.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
                 progressBar.isVisible = loadState is LoadState.Loading
                button.isVisible = loadState is LoadState.Error

        }
    }

    override fun onBindViewHolder(holder: MyLoadStateAdapterViewHolder, loadState: LoadState) {
        holder.bind(loadState)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MyLoadStateAdapterViewHolder {
        return MyLoadStateAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.state, parent,false), retry
        )
    }
}