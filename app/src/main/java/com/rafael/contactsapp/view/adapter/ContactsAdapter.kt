package com.rafael.contactsapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.databinding.RowRecyclerviewContactBinding

class ContactsAdapter(var list: MutableList<Contacts>) : RecyclerView.Adapter<ContactsAdapter.ProductViewHolder>() {

    private var itemClickListener: ((Contacts) -> Unit)? = null

    inner class ProductViewHolder(private val binding: RowRecyclerviewContactBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Contacts) {
            binding.apply {
                contactDetailTv.text = "${item.contact_name}  ${item.contact_number}"
                itemView.setOnClickListener {
                    itemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = RowRecyclerviewContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun setItemClickListener(listener: (Contacts) -> Unit) {
        itemClickListener = listener
    }
}
