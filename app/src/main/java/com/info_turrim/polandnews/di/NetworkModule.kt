package com.info_turrim.polandnews.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.info_turrim.polandnews.BuildConfig
import com.info_turrim.polandnews.utils.extension.getAccessToken
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.*


private const val CONNECT_TIMEOUT_S = 15L
private const val READ_TIMEOUT_S = 15L
private const val WRITE_TIMEOUT_S = 15L

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("breaking-news-api")
    fun provideBreakingNewsRetrofit(
        @Named("breaking-news-client") httpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(gsonConverter)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("breaking-news-for-you-api")
    fun provideBreakingNewsForYouRetrofit(
        @Named("breaking-news-client") httpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.ADMIN_API_URL)
            .addConverterFactory(gsonConverter)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("ad-api")
    fun provideAdRetrofit(
        @Named("breaking-news-client") httpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AD_API_URL)
            .addConverterFactory(gsonConverter)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("breaking-news-client")
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        prefs: SharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_S, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_S, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(object : Interceptor {
                val regex = Regex(pattern = "/api/v1/news/[0-9]{5,9}/")
                override fun intercept(chain: Interceptor.Chain): Response {
                    val requestBuilder = chain.request().newBuilder()

                    if (regex.matches(input = chain.request().url.toUrl().path))
                        return chain.proceed(requestBuilder.build())

                    if (prefs.getAccessToken().isNotEmpty())
                        requestBuilder.addHeader(
                            "Authorization", "Bearer ${prefs.getAccessToken()}"
                        )
                    return chain.proceed(requestBuilder.build())
                }
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideGsonConverter(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}