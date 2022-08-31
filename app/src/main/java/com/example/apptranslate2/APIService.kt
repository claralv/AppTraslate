package com.example.apptranslate2

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {
    //Cabecera con nuestra API key:
    @Headers("Authorization: Bearer 8fa224b69b8ef6e22911515c0752c291")
    //Pasamos el parámetro q encodeado:
    @FormUrlEncoded
    //Petición POST:
    @POST("/0.2/detect")
    //Enviamos el parámetro q, y recibimos la respuesta de la petición
    suspend fun getTextLanguage(@Field("q") text:String):Response<LanguageResponse>
}

