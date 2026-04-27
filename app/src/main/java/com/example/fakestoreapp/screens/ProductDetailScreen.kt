package com.example.fakestoreapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.fakestoreapp.models.Product
import com.example.fakestoreapp.services.ProductsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun ProductDetailScreen(id: Int) {
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(true) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsService::class.java)
        product = service.getProductById(id)
    }

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFFF6B6B))
        }
    } else {
        Scaffold(
            bottomBar = {
                BottomAppBar(containerColor = Color.White) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$${product?.price}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Text(
                                text = "Buy Now",
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FA))
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = product?.image,
                        contentDescription = product?.title,
                        modifier = Modifier.size(280.dp).padding(20.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Column(modifier = Modifier
                    .padding(24.dp)) {
                    Text(
                        text = product?.category?.uppercase() ?: "",
                        color = Color(0xFFFF6B6B),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = product?.title ?: "",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(0.5.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp))
                                .padding(horizontal = 24.dp, vertical = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("RATE", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Text("${product?.rating?.rate} ★", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .border(0.5.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp))
                                .padding(horizontal = 24.dp, vertical = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("REVIEWS", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Text("${product?.rating?.count}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Description",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = product?.description ?: "",
                        color = Color(0xFF666666),
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(top = 8.dp),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}
