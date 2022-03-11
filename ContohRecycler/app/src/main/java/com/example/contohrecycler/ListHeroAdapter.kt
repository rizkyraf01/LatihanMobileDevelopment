package com.example.contohrecycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contohrecycler.databinding.ItemRowHeroBinding

class ListHeroAdapter: RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {
    private var listHero = arrayListOf<Hero>()

    fun submitList(heroList: ArrayList<Hero>){
        listHero = heroList
    }

    class ListViewHolder(private val binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero){
            with(binding){
                tvItemName.text = hero.name
                tvItemDescription.text = hero.description
                imgItemPhoto.setImageResource(hero.photo)
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailHeroesActivity::class.java)
                    intent.putExtra(DetailHeroesActivity.EXTRA_DATA, hero)
                    itemView.context.startActivities(arrayOf(intent))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val listHeroBinding = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ListViewHolder(listHeroBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val hero = listHero[position]
        holder.bind(hero)
    }

    override fun getItemCount(): Int = listHero.size
}