package com.example.apka

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("tworzenie_api.php")
    fun getProducts(): Call<List<Product>>
}