package com.edwin.granados.listadopaises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private val items = mutableListOf<Country>()

    fun submitItems(newItems: List<Country>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flag: ImageView = itemView.findViewById(R.id.countryFlag)
        private val name: TextView = itemView.findViewById(R.id.countryName)
        private val region: TextView = itemView.findViewById(R.id.countryRegion)
        private val code: TextView = itemView.findViewById(R.id.countryCode)

        fun bind(country: Country) {
            name.text = country.name
            region.text = country.region
            code.text = country.alphaCode
            Glide.with(flag)
                .load(country.flagUrl)
                .centerCrop()
                .placeholder(R.drawable.flag_placeholder)
                .error(R.drawable.flag_placeholder)
                .into(flag)
        }
    }
}
