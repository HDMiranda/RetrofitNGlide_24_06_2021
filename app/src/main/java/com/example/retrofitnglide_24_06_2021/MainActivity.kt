package com.example.retrofitnglide_24_06_2021

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.text.StringBuilder


class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val ivAvatar: ImageView = findViewById<View>(R.id.ivAvatar) as ImageView
    val tvRepos: TextView = findViewById<TextView>(R.id.tvRepos)
    val tvOwnerName: TextView = findViewById<TextView>(R.id.tvOwnerName)
    val etUsername: TextView = findViewById<EditText>(R.id.etUsername)
    val btSearch: TextView = findViewById<Button>(R.id.btSearch)

    Glide
      .with(this)
      .load("https://github.com/bumptech/glide/blob/master/static/glide_logo.png?raw=true")
      .into(ivAvatar)

    val gson = GsonBuilder()
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
      .create()

    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()

    val service = retrofit.create(GitHubService::class.java)

    btSearch.setOnClickListener() {
      val userName: String = etUsername.text.toString().trim()

      if (userName.isNotEmpty() && userName.isNotBlank()) {
        println(userName)

        val repos: Call<List<Repo?>?>? = service.listRepos(userName)

        repos?.enqueue(
          object : Callback<List<Repo?>?> {
            override fun onResponse(call: Call<List<Repo?>?>, response: Response<List<Repo?>?>) {
              val bodyResponse = response?.body()
              val repoList = StringBuilder()

              for (item in bodyResponse!!) {
                println(item?.name)
                repoList.append(item?.name + "\n")
              }

              Glide
                .with(this@MainActivity)
                .load(bodyResponse?.get(0)?.owner?.avatar_url)
                .into(ivAvatar)

              tvRepos.setText(repoList)
              tvOwnerName.setText(bodyResponse?.get(0)?.owner?.login)
            }

            override fun onFailure(call: Call<List<Repo?>?>, t: Throwable) {
              tvRepos.setText("User not found")
            }
          }
        )
      }
    }
  }
}