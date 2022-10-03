package com.example.dryogeshbatra.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    var retrofitService: AuthInterface? = null

    fun getInstance(): AuthInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://159.65.9.74/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitService = retrofit.create(AuthInterface::class.java)
        return retrofitService as AuthInterface

    }
}