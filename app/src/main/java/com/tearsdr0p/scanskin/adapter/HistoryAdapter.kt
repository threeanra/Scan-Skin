package com.tearsdr0p.scanskin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tearsdr0p.scanskin.R
import com.tearsdr0p.scanskin.data.local.room.HistoryEntity
import com.tearsdr0p.scanskin.databinding.HistoryCardBinding
import com.tearsdr0p.scanskin.ui.history.HistoryViewModel

class HistoryAdapter(private val viewModel: HistoryViewModel) :
    ListAdapter<HistoryEntity, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class HistoryViewHolder(private val binding: HistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryEntity) {
            binding.apply {
                titleHistory.text = item.title
                dateHistory.text = item.time
                Glide.with(itemView.context).load(item.image).into(imageHistory)
                btnDelete.setOnClickListener {
                    showDeleteDialog(item)
                }
            }
        }

        private fun showDeleteDialog(item: HistoryEntity) {
            val dialogView =
                LayoutInflater.from(itemView.context).inflate(R.layout.alert_dialog_history, null)

            val builder = AlertDialog.Builder(itemView.context)
                .setView(dialogView)
                .setCancelable(false)

            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent) //to make alert dialog rounded corner

            dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                viewModel.deleteAnalysisResult(item)
                alertDialog.dismiss()
            }

            dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}