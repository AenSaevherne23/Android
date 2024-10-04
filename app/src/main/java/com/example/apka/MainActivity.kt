package com.example.apka

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchProducts()
    }

    private fun fetchProducts() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.250.69/kolektor_projekt/") // Zmień na odpowiedni adres serwera
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    response.body()?.let { products ->
                        productAdapter = ProductAdapter(products)
                        recyclerView.adapter = productAdapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "NIE DZiała: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("API Error", "Błąd: ${t.localizedMessage}", t) // Loguj pełny błąd
                Toast.makeText(this@MainActivity, "Błąd: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
