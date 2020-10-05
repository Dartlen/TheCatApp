package bydartlen.thecatapp.data

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatEndPoint {

    @Headers("x-api-key: 5c4ce77c-ebb4-43c1-8892-ffb69c1108a8")
    @GET("/v1/images/search")
    fun getCats(@Query("limit") limit: Int, @Query("page") page: Long): Single<List<Cat>>

    companion object {
        fun getService(): CatEndPoint {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(CatEndPoint::class.java)
        }
    }
}