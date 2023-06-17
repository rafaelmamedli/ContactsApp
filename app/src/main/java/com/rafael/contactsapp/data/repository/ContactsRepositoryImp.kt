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

             val   list = response.body()?.contacts as MutableList<Contacts>
                Log.d(SUCCESS,list.toString())
                result.invoke(
                    UiState.Success(list)
                )
            }
            override fun onFailure(call: Call<ContactsAnswer>, t: Throwable) {
                Log.d(SUCCESS,"Error")

            }
        })
    }

    override suspend fun deleteContact(contact_id: Int, result: (UiState<List<Contacts>>) -> Unit) {
        apiService.deleteContact(contact_id).enqueue(object : Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                val success = response.body()!!.success
                val message = response.body()!!.message
                Log.d(SUCCESS,"$success  $message")
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}




