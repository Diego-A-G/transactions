package com.example.transactions.services

import com.example.transactions.repository.dto.AnnulRequestDTO
import com.example.transactions.repository.dto.AnnulResponseDTO
import com.example.transactions.repository.dto.AuthRequestDTO
import com.example.transactions.repository.dto.AuthResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ITransactionsServices {

    @POST("authorization")
    suspend fun sendAuthorization(
        @Body request: AuthRequestDTO
    ): AuthResponseDTO


    @GET("annulment")
    suspend fun sendAnnulation(
        @Body request: AnnulRequestDTO
    ): AnnulResponseDTO

}