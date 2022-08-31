package com.example.apptranslate2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.apptranslate2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        }

    private fun initListener() {
        binding.boton.setOnClickListener {
            val frase = binding.texto.text.toString()
            if (frase.isNotEmpty()) {
                showProgressBar()
                getText(frase)
            }
        }
    }

    private fun showProgressBar() {
        binding.carga.visibility = View.VISIBLE
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ws.detectlanguage.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getText(frase: String)  {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<LanguageResponse> =
                getRetrofit().create(APIService::class.java).getTextLanguage(frase)
            runOnUiThread {
                if (call.isSuccessful) {
                    val result = call.body()
                    if (result != null && !result.data.detections.isNullOrEmpty()) {
                        Toast.makeText(
                            this@MainActivity,
                            "El idioma es ${result.data.detections.first().language}",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        showError()
                    }

                } else {
                    showError()
                }
                hideProgressBar()
            }
        }
    }

    private fun hideProgressBar() {
        runOnUiThread {
            binding.carga.visibility = View.GONE
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}


