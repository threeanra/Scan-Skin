package com.tearsdr0p.scanskin.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.data.network.response.ArticlesItem
import com.tearsdr0p.scanskin.databinding.ItemArticleBinding

class ArticleAdapter(private val context: Context) : ListAdapter<ArticlesItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articles = getItem(position)
        holder.bind(articles)
    }

    class MyViewHolder(private val binding: ItemArticleBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articles: ArticlesItem){
            binding.articleTitle.text = articles.title
            binding.articleAuthor.text = articles.author?.let { context.getString(R.string.author, it) } ?: "Unknown"
            if (articles.urlToImage != null) {
                binding.articleAuthor.text = context.getString(R.string.author, articles.author.toString())
                Glide.with(binding.articleImage.context)
                    .load(articles.urlToImage)
                    .apply(RequestOptions().error(R.drawable.article_image_preview))
                    .into(binding.articleImage)
            } else {
                binding.articleImage.setImageResource(R.drawable.article_image_preview)
            }

            binding.root.setOnClickListener {
                val intentUrl = Intent(Intent.ACTION_VIEW, Uri.parse(articles.url))
                binding.root.context.startActivity(intentUrl)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}