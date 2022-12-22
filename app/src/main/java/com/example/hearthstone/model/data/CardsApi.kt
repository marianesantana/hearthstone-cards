package com.example.hearthstone.model.data
import com.example.hearthstone.model.CardBacks
import com.example.hearthstone.model.SingleCard
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface CardsApi {

    companion object {
        const val BASE_URL = "https://omgvamp-hearthstone-v1.p.rapidapi.com/"
    }

    @GET("/cardbacks")
    @Headers("X-RapidAPI-Key: e9b55b1da6msh6dd8dea2cdcd9e0p1fef94jsn17cb5bfb70c5")
    suspend fun getCardsBack(): List<CardBacks?>


    @GET("/cards")
    @Headers("X-RapidAPI-Key: e9b55b1da6msh6dd8dea2cdcd9e0p1fef94jsn17cb5bfb70c5")
    suspend fun getAllCards(): List<SingleCard>

    @GET("/cards/{cardId}")
    @Headers("X-RapidAPI-Key: e9b55b1da6msh6dd8dea2cdcd9e0p1fef94jsn17cb5bfb70c5")
    suspend fun getCard(@Path("cardId") id: Int): SingleCard?

}