package com.rasyid.flornatan

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rasyid.flornatan.databinding.ActivityDetailBinding
import java.io.File
import java.io.FileOutputStream

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FLORA_FAUNA = "extra_flora_fauna"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataFloraFauna = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_FLORA_FAUNA, FloraFauna::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_FLORA_FAUNA)
        }

        if (dataFloraFauna != null) {
            binding.ivDtPhoto.setImageResource(dataFloraFauna.photo)
            binding.tvDtName.text = dataFloraFauna.name
            binding.tvDtDesc.text = dataFloraFauna.desc
        }

        binding.actionShare.setOnClickListener {
            if (dataFloraFauna != null) {
                shareContent(dataFloraFauna.photo, dataFloraFauna.name, dataFloraFauna.desc)
            }
        }

        supportActionBar?.title = "Detail"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun shareContent(imageResId: Int, title: String, description: String) {
        val drawable = getDrawable(imageResId)
        val bitmap = (drawable as BitmapDrawable).bitmap

        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()

        val contentUri: Uri = FileProvider.getUriForFile(this, "$packageName.file provider", file)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Bagikan Ke"))
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }
}