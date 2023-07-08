package com.inheritex.demo.api

import com.inheritex.demo.constant.ApiConstant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/// [NetworkModule] This class is use for retrofit instance
class NetworkModule {
    companion object {
        /// [provideCall] This method used for create retrofit instance
        private fun provideCall(): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(LoggInterceptor())
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", "max-age=30")
                                .build()
                        val response = chain.proceed(request)
                        response.cacheResponse()
                        response
                    }
                    .build()
            return Retrofit.Builder()
                    .baseUrl(ApiConstant.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }


        /// [providesNetworkService] This method used for network service
        fun providesNetworkService(): ApiService {
            return provideCall().create(ApiService::class.java)
        }
    }
}