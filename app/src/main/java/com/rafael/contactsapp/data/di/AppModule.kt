package com.rafael.contactsapp.data.di

import com.rafael.contactsapp.data.repository.ContactsRepository
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.retrofit.ApiService
import com.rafael.contactsapp.data.util.Constants.BASE_URL
import com.rafael.contactsapp.viewmodel.ContactsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava 2.x uyumlu çağrı adaptörünü ekle
            .build()
            .create(ApiService::class.java)

    @Provides
    fun provideContactsRepository(apiService: ApiService): ContactsRepository {
        return ContactsRepositoryImp(apiService)
    }

    @Provides
    fun provideContactsViewModel(repository: ContactsRepositoryImp): ContactsViewModel {
        return ContactsViewModel(repository)
    }

}