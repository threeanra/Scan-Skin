package com.tearsdr0p.scanskin.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.data.network.response.ArticlesItem
import com.tearsdr0p.scanskin.databinding.ArticleCardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class ArticlesAdapter : ListAdapter<ArticlesItem, ArticlesAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ArticleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articles = getItem(position)
        holder.bind(articles)
    }

    class MyViewHolder(private val binding: ArticleCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(articles: ArticlesItem){
            binding.titleArticle.text = articles.title
            binding.descArticle.text = convertDate(articles.publishedAt.toString())
            if (articles.urlToImage != null) {
                Glide.with(binding.imageArticle.context)
                    .load(articles.urlToImage)
                    .apply(RequestOptions().error(R.drawable.article_image_preview))
                    .into(binding.imageArticle)
            } else {
                binding.imageArticle.setImageResource(R.drawable.article_image_preview)
            }

            binding.root.setOnClickListener {
                val intentUrl = Intent(Intent.ACTION_VIEW, Uri.parse(articles.url))
                binding.root.context.startActivity(intentUrl)
            }
        }

        private fun convertDate(time: String): String {
            // Define the input date format
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")

            // Define the output date format
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            // Parse the input date string to a Date object
            val date: Date? = inputFormat.parse(time)

            // Format the Date object to the desired output format
            return date?.let { outputFormat.format(it) } ?: ""
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