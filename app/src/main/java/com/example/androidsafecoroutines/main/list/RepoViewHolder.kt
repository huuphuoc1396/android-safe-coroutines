package com.example.androidsafecoroutines.main.list

import androidx.recyclerview.widget.RecyclerView
import com.example.androidsafecoroutines.databinding.ItemRepoBinding
import com.example.androidsafecoroutines.ext.setImageUrl
import com.example.androidsafecoroutines.model.Repo

class RepoViewHolder(
    private val viewBinding: ItemRepoBinding,
    onItemClick: (repo: Repo) -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: Repo? = null

    init {
        viewBinding.root.setOnClickListener {
            item?.let(onItemClick)
        }
    }

    fun bind(item: Repo) {
        this.item = item
        viewBinding.imgAvatar.setImageUrl(item.imageUrl)
        viewBinding.txtRepoName.text = item.name
        viewBinding.txtOwnerName.text = item.owner
    }
}