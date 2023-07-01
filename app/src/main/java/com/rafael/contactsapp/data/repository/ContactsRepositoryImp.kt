package com.rafael.contactsapp.data.repository

import android.util.Log
import com.rafael.contactsapp.data.model.Answer
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.retrofit.ApiService
import com.rafael.contactsapp.data.util.Tags.TAG
import com.rafael.contactsapp.data.util.UiState
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ContactsRepositoryImp
@Inject constructor(private val apiService: ApiService) : ContactsRepository {

    override fun getAllResults(): Observable<UiState<List<Contacts>>> {
        return Observable.create { emitter ->
            apiService.getAllContacts().enqueue(object : Callback<ContactsAnswer> {
                override fun onResponse(call: Call<ContactsAnswer>, response: Response<ContactsAnswer>) {
                    if (response.isSuccessful) {
                        val contactsAnswer = response.body()
                        val list = contactsAnswer?.contacts ?: emptyList()
                        emitter.onNext(UiState.Success(list))
                    } else {
                        emitter.onNext(UiState.Failure("Failed to get contacts"))
                    }
                    emitter.onComplete()
                }

                override fun onFailure(call: Call<ContactsAnswer>, t: Throwable) {
                    emitter.onNext(UiState.Failure(t.message))
                    emitter.onComplete()
                }
            })
        }
    }

    override fun deleteContact(contact_id: Int): Observable<UiState<List<Contacts>>> {
        return Observable.create { emitter ->
            apiService.deleteContact(contact_id).enqueue(object : Callback<Answer> {
                override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                    if (response.isSuccessful) {
                        val success = response.body()?.success ?: 0
                        val message = response.body()?.message
                        Log.d(TAG, "$success  $message")
                        emitter.onNext(UiState.Success(emptyList()))
                    } else {
                        emitter.onNext(UiState.Failure("Failed to delete contact"))
                    }
                    emitter.onComplete()
                }

                override fun onFailure(call: Call<Answer>, t: Throwable) {
                    emitter.onNext(UiState.Failure(t.message))
                    emitter.onComplete()
                }
            })
        }
    }

    override fun addContact(contact_name: String, contact_number: String): Observable<UiState<String>> {
        return Observable.create { emitter ->
            apiService.addContact(contact_name, contact_number).enqueue(object : Callback<Answer> {
                override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                    if (response.isSuccessful) {
                        val success = response.body()?.success ?: 0
                        val message = response.body()?.message
                        emitter.onNext(UiState.Success("Contact added"))
                    } else {
                        emitter.onNext(UiState.Failure("Failed to add contact"))
                    }
                    emitter.onComplete()
                }

                override fun onFailure(call: Call<Answer>, t: Throwable) {
                    emitter.onNext(UiState.Failure(t.message))
                    emitter.onComplete()
                }
            })
        }
    }
}



