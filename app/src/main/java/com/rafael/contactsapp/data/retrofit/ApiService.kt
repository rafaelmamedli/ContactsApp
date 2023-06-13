package com.rafael.contactsapp.data.retrofit

import com.rafael.contactsapp.data.model.ContactsAnswer
import retrofit2.Call
import retrofit2.http.GET

interface ApiService
{
    @GET("kisiler/tum_kisiler.php")
    fun getAllContacts(): Call<ContactsAnswer>
}