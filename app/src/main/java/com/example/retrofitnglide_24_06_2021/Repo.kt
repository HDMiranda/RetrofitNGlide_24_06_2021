package com.example.retrofitnglide_24_06_2021

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit


data class Repo (
  val name: String,
  val owner: Owner
)

data class Owner (
  val login: String,
  val avatar_url: String
)

//fun getRepoName(repos: String): String? {
//
//  val gson = Gson()
//
//  return gson.toJson(repos);
//}