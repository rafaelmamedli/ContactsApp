package com.rafael.contactsapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    override fun getAllResults(result: (UiState<List<Contacts>>) -> Unit) {
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

//    override fun getAllResults(result: (UiState<List<ContactsAnswer>>) -> Unit) {
//        TODO("Not yet implemented")
//    }
}




