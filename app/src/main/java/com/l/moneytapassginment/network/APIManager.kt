package com.l.moneytapassginment.network

import com.l.moneytapassginment.network.MyLoggingInterceptor.provideOkHttpLogging
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

class APIManager private constructor() {

    private val baseUrl = "https://api.github.com/"
    private fun createRetrofitService(): JoshApiClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(provideOkHttpLogging())
        val client = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(JoshApiClient::class.java)
    }

    private val service: JoshApiClient
        get() = createRetrofitService()

    @JvmSuppressWildcards
    private interface JoshApiClient {

        @GET("search/repositories")
        fun getRepos(@QueryMap queryMap: Map<String?, Any?>?): Call<ResponseBody>

        @GET
        fun getContributors(@Url url: String?): Call<ResponseBody>

        @GET
        fun getOwnerRepos(@Url url: String?): Call<ResponseBody>
    }

    fun searchRequest(queryMap: Map<String?, Any?>?): Call<ResponseBody> {
        return service.getRepos(queryMap)
    }

    fun contributorsRequest(baseUrl: String?): Call<ResponseBody> {
        return service.getContributors(baseUrl)
    }

    fun getOwnerReposRequest(baseUrl: String?): Call<ResponseBody> {
        return service.getOwnerRepos(baseUrl)
    }


    companion object {
        private var myInstance: APIManager? = null
        val instance: APIManager?
            get() {
                if (myInstance == null) {
                    myInstance =
                        APIManager()
                }
                return myInstance
            }
    }
}