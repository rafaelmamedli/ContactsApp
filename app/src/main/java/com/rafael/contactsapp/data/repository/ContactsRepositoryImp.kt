package com.rafael.contactsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ContactsRepositoryImp
@Inject constructor(private val apiService: ApiService) : ContactsRepository {

    var contactsList : MutableLiveData<List<Contacts>?> = MutableLiveData()

    override suspend fun getAllResults(): LiveData<List<ContactsAnswer>> {
        val resultLiveData = MutableLiveData<List<ContactsAnswer>>()

        apiService.getAllContacts().enqueue(object : Callback<ContactsAnswer> {
            override fun onResponse(
                call: Call<ContactsAnswer>,
                response: Response<ContactsAnswer>
            ) {
                val list = response.body()?.contacts
                contactsList.value = list

            }

            override fun onFailure(call: Call<ContactsAnswer>, t: Throwable) {
            }
        })
        return resultLiveData
    }

}