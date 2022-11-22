package com.example.gsoninterfaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.databinding.DataBindingUtil
import com.example.gsoninterfaces.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnLoadFruit.setOnClickListener {
            val random = Random()
            val randNum = random.nextInt(2)
    // saving fruit to preferences
            val fruit = if (randNum == 0)
                Lemon(acidity = random.nextInt(2))
            else
                Watermelon(isSeedless = random.nextBoolean(), color = if (random.nextInt() == 0) "red" else "yellow")
            saveFruitToPreferences(fruit)
        }

        binding.btnSaveRandomFruit.setOnClickListener {
            val fruit = getFruitFromPreferences()
            binding.tvFruitDetails.text = fruit.toString()
        }
    }

    private val gsonForFruit:Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Fruit::class.java, FruitTypeAdapter())
            .create()
    }

    private fun saveFruitToPreferences(fruit: Fruit){
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        val jsonString = gsonForFruit.toJson(fruit, Fruit::class.java)
        prefEditor.putString("fruit",jsonString).apply()
    }

    private fun getFruitFromPreferences(): Fruit{
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val jsonString = preferences.getString("fruit",null)
        return gsonForFruit.fromJson(jsonString, Fruit::class.java)
    }

}