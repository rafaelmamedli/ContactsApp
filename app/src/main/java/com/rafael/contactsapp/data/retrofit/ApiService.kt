package com.rafael.contactsapp.data.retrofit

import com.rafael.contactsapp.data.model.Answer
import com.rafael.contactsapp.data.model.ContactsAnswer
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService
{
    @GET("kisiler/tum_kisiler.php")
    fun getAllContacts(): Call<ContactsAnswer>

    @POST("kisiler/delete_kisiler.php")
    @FormUrlEncoded
    fun deleteContact(@Field("kisi_id") contact_id: Int) : Call<Answer>

    @POST("kisiler/insert_kisiler.php")
    @FormUrlEncoded
    fun addContact(@Field("kisi_ad") contact_name:String,@Field("kisi_tel") contact_number:String) :Call<Answer>

}