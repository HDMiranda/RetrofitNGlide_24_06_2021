package com.example.retrofitnglide_24_06_2021

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
  @GET("users/{user}/repos")
  fun listRepos(@Path("user") user: String?): Call<List<Repo?>?>?
}