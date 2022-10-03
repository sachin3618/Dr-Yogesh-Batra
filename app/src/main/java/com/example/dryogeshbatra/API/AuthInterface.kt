package com.example.dryogeshbatra.API

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthInterface {

    @GET("rtc/{channel}/{role}/{tokenType}/{UID}")
    fun getToken(
        @Path("channel") channel: String,
        @Path("role") role: String,
        @Path("tokenType") tokenType: String,
        @Path("UID") UID: Int
    ): retrofit2.Call<Auth>
}