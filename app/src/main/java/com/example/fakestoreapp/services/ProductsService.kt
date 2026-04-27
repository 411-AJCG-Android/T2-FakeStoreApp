package com.example.fakestoreapp.services

import com.example.fakestoreapp.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    @GET("/products")
    suspend fun getAllProducts() : List<Product> //con suspend le digo que es asincrona

    //Path --> /1
    //Query --> ?name=Astrid&lastName=Castuera
    // Body -->  {"name":"Astrid"}
    @GET("/products/{id}")
    suspend fun getProductById(@Path("id")id:Int) : Product
}