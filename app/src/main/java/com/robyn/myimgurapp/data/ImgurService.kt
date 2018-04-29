package com.robyn.myimgurapp.data

import com.robyn.myimgurapp.data.response.GalleryResponse
import com.robyn.myimgurapp.data.response.ImageResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Imgur api services.
 */
interface ImgurService {

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET(ROUTE_SEARCH)
    fun search(@Query("q") query: String): Observable<GalleryResponse>

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET(ROUTE_GALLERY_TIME)
    fun galleryNewest(@Query("page") page: Int): Observable<GalleryResponse>

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET(ROUTE_GALLERY_VIRAL)
    fun galleryViral(@Query("page") page: Int): Observable<GalleryResponse>

    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET(ROUTE_IMAGE)
    fun image(@Path("id") imageHash: String): Observable<ImageResponse>

    companion object {

        const val CLIENT_ID = "ef018f166448bb0"

        const val ROUTE_SEARCH = "gallery/search"
        const val ROUTE_GALLERY_TIME = "gallery/hot/time"
        const val ROUTE_GALLERY_VIRAL = "gallery/hot/viral"
        const val ROUTE_IMAGE = "image/{id}"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val service = retrofit.create(ImgurService::class.java) // data source
    }
}