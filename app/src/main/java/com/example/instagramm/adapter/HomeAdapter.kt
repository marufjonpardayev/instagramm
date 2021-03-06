package com.example.instagramm.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramm.databinding.ItemPostHomeBinding
import com.example.instagramm.model.Post


class HomeAdapter(var items: ArrayList<Post>) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            ItemPostHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = items[position]
        if (holder is PostViewHolder) {
            holder.itemPostHomeBinding.apply {
                tvCaption.text = post.caption
                tvTime.text = post.currentDate
                tvFullname.text = post.fullname
                Glide.with(ivProfile)
                    .load(post.userImg)
                    .into(ivProfile)

                Glide.with(ivPost)
                    .load(post.postImage)
                    .into(ivPost)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class PostViewHolder(var itemPostHomeBinding: ItemPostHomeBinding) :
        RecyclerView.ViewHolder(itemPostHomeBinding.root) {
    }
}