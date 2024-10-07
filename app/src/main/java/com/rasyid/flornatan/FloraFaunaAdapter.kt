package com.rasyid.flornatan

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rasyid.flornatan.databinding.ItemRowBinding

class FloraFaunaAdapter(private val listFloraFauna: ArrayList<FloraFauna>): RecyclerView.Adapter<FloraFaunaAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFloraFauna.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, desc, photo) = listFloraFauna[position]
        holder.binding.ivPhoto.setImageResource(photo)
        holder.binding.tvName.text = name
        holder.binding.tvDesc.text = desc

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_FLORA_FAUNA, listFloraFauna[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

}