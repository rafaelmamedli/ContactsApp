package com.rafael.contactsapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafael.contactsapp.data.model.Answer
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.retrofit.ApiService
import com.rafael.contactsapp.data.util.Tags.SUCCESS
import com.rafael.contactsapp.data.util.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ContactsRepositoryImp
@Inject constructor(private val apiService: ApiService) : ContactsRepository {


    override suspend fun getAllResults(result: (UiState<List<Contacts>>) -> Unit) {
        apiService.getAllContacts().enqueue(object : Callback<ContactsAnswer> {
            override fun onResponse(
                call: Call<ContactsAnswer>,
                response: Response<ContactsAnswer>
            ) {
                val contactsAnswer = response.body()
                if (response.isSuccessful && contactsAnswer != null) {
                    val list = contactsAnswer.contacts
                //    Log.d(SUCCESS, list.toString())
                    result.invoke(UiState.Success(list))
                } else {
                    result.invoke(UiState.Failure("Failed to get contacts"))
                }
            }

            override fun onFailure(call: Call<ContactsAnswer>, t: Throwable) {
                result.invoke(UiState.Failure(t.message))
            }
        })
    }


    override suspend fun deleteContact(contact_id: Int, result: (UiState<List<Contacts>>) -> Unit) {
        apiService.deleteContact(contact_id).enqueue(object : Callback<Answer> {
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                if (response.isSuccessful) {
                    val success = response.body()?.success ?: 0
                    val message = response.body()?.message
                    Log.d(SUCCESS, "$success  $message")
                } else {
                    result.invoke(UiState.Failure("Failed to delete contact"))
                }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                result.invoke(UiState.Failure(t.message))
            }
        })
    }


    override suspend fun addContact(
        contact_name: String,
        contact_number: String,
        result: (UiState<String>) -> Unit
    ) {
        apiService.addContact(contact_name, contact_number).enqueue(object : Callback<Answer> {
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                if (response.isSuccessful) {
                    val success = response.body()?.success ?: 0
                    val message = response.body()?.message
                    result.invoke(UiState.Success("Contact added"))
                } else {
                    result.invoke(UiState.Failure("Failed to add contact"))
                }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                result.invoke(UiState.Failure(t.message))
            }
        })
    }



}




