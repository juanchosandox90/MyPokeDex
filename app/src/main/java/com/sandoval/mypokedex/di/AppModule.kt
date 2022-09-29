package com.sandoval.mypokedex.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.sandoval.mypokedex.BuildConfig
import com.sandoval.mypokedex.commons.BASE_URL_POKEMON
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.remote.repository.pokedex_detail.RemoteDataPokedexDetailRepository
import com.sandoval.mypokedex.data.remote.repository.pokedex_evolution.RemoteDataPokedexEvolutionRepository
import com.sandoval.mypokedex.data.remote.repository.pokedex_list.RemoteDataPokedexListRepository
import com.sandoval.mypokedex.data.remote.repository.pokedex_location.RemoteDataPokedexLocationRepository
import com.sandoval.mypokedex.domain.repository.pokedex_detail.IGetPokedexDetailRepository
import com.sandoval.mypokedex.domain.repository.pokedex_evolution.IGetPokedexEvolutionRepository
import com.sandoval.mypokedex.domain.repository.pokedex_list.IGetPokedexListRepository
import com.sandoval.mypokedex.domain.repository.pokedex_location.IGetPokedexLocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl() = BASE_URL_POKEMON

    @Provides
    fun provideOkHttpClient(cache: Cache) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    } else {
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .cache(cache)
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

    /*Caching data */
    @Provides
    @Singleton
    fun provideCache(@ApplicationContext appContext: Context): Cache {

        return Cache(
            File(appContext.applicationContext.cacheDir, "pokemon_cache"),
            10 * 1024 * 1024
        )
    }

    private val cacheInterceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(30, TimeUnit.DAYS)
                .build()
            return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    @Provides
    @Singleton
    fun providesGetPokedexList(remoteDataPokedexListRepository: RemoteDataPokedexListRepository): IGetPokedexListRepository =
        remoteDataPokedexListRepository

    @Provides
    @Singleton
    fun providesGetPokedexDetail(remoteDataPokedexDetailRepository: RemoteDataPokedexDetailRepository): IGetPokedexDetailRepository =
        remoteDataPokedexDetailRepository

    @Provides
    @Singleton
    fun providesGetPokedexEvolution(remoteDataPokedexEvolutionRepository: RemoteDataPokedexEvolutionRepository): IGetPokedexEvolutionRepository =
        remoteDataPokedexEvolutionRepository

    @Provides
    @Singleton
    fun providesGetPokedexLocation(remoteDataPokedexLocationRepository: RemoteDataPokedexLocationRepository): IGetPokedexLocationRepository =
        remoteDataPokedexLocationRepository
}