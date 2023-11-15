package com.example.vingenerator.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
@GET("/api/vehicles/decodevinvalues/{vin}?format=json")
fun getData(
    @Path("vin")
    vinNumber: String
    ):Call<data>
}