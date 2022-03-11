package com.example.contohrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contohrecycler.databinding.ActivityDetailHeroesBinding

class DetailHeroesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHeroesBinding

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Detail")

        val hero = intent.getParcelableExtra<Hero>(EXTRA_DATA) as Hero
        binding.tvItemName.text = hero.name
        binding.tvItemDescription.text = hero.description
        binding.imgItemPhoto.setImageResource(hero.photo)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}