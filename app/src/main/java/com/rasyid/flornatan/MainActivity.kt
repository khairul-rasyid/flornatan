package com.rasyid.flornatan

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvFloraFauna: RecyclerView
    private var list = ArrayList<FloraFauna>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvFloraFauna = findViewById(R.id.rv_flora_fauna)

        list.addAll(getFloraFauna())
        showRecyclerList()

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
    }

    @SuppressLint("Recycle")
    private fun getFloraFauna(): ArrayList<FloraFauna> {
        val photo = resources.obtainTypedArray(R.array.photo)
        val name = resources.getStringArray(R.array.name)
        val desc = resources.getStringArray(R.array.desc)
        val listFloraFauna = ArrayList<FloraFauna>()
        for (i in name.indices) {
            val floraFauna = FloraFauna(name[i], desc[i], photo.getResourceId(i, - 1))
            listFloraFauna.add(floraFauna)
        }
        return listFloraFauna
    }

    private fun showRecyclerList() {
        rvFloraFauna.layoutManager = LinearLayoutManager(this)
        val floraFaunaAdapter = FloraFaunaAdapter(list)
        rvFloraFauna.adapter = floraFaunaAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_page -> {
                val moveIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}