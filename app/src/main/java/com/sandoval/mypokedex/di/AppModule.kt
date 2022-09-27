package com.sandoval.mypokedex.di

import com.google.gson.GsonBuilder
import com.sandoval.mypokedex.BuildConfig
import com.sandoval.mypokedex.commons.BASE_URL_POKEMON
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.remote.repository.pokedex_detail.RemoteDataPokedexDetailRepository
import com.sandoval.mypokedex.data.remote.repository.pokedex_list.RemoteDataPokedexListRepository
import com.sandoval.mypokedex.domain.repository.pokedex_detail.IGetPokedexDetailRepository
import com.sandoval.mypokedex.domain.repository.pokedex_list.IGetPokedexListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl() = BASE_URL_POKEMON

    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL_POKEMON: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .baseUrl(BASE_URL_POKEMON)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PokedexService =
        retrofit.create(PokedexService::class.java)

    @Provides
    @Singleton
    fun providesGetPokedexList(remoteDataPokedexListRepository: RemoteDataPokedexListRepository): IGetPokedexListRepository =
        remoteDataPokedexListRepository

    @Provides
    @Singleton
    fun providesGetPokedexDetail(remoteDataPokedexDetailRepository: RemoteDataPokedexDetailRepository): IGetPokedexDetailRepository =
        remoteDataPokedexDetailRepository
}