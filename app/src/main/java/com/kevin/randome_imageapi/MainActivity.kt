package com.kevin.randome_imageapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.kevin.randome_imageapi.API.ApiClient
import com.kevin.randome_imageapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: WallpaperAdapter
    var auth = "tJCyTiaz7mjtevZkfdemL7rGVEkUnNpcumf60Stl3KTX4gL32sW6r77G"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WallpaperAdapter()
        binding.btnsearch.setOnClickListener {
            callApi()
        }
    }

    private fun callApi() {
        var txt = binding.edtsearch.text.toString()

        var api: APIinterface = ApiClient.getApiClient().create(APIinterface::class.java)
        api.getPhotos(auth,txt).enqueue(object : Callback<PhotoModel> {
            override fun onResponse(call: Call<PhotoModel>, response: Response<PhotoModel>) {
                if (response.isSuccessful) {
                    var photos = response.body()?.photos
                    adapter.setPhotos(photos as List<PhotosItem>?)
                    binding.rcvphotos.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    binding.rcvphotos.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PhotoModel>, t: Throwable) {

            }
        })
    }
}