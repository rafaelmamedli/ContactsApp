package com.rafael.contactsapp.data.repository

import android.util.Log
import com.rafael.contactsapp.data.model.Answer
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.retrofit.ApiService
import com.rafael.contactsapp.data.util.Tags.TAG
import com.rafael.contactsapp.data.util.UiState
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class ContactsRepositoryImp
@Inject constructor(private val apiService: ApiService) : ContactsRepository {

    override fun getAllResults(): Observable<ContactsAnswer> {
        return apiService.getAllContacts()

    }

    override fun deleteContact(contact_id: Int): Observable<Answer> {
        return apiService.deleteContact(contact_id)
    }

    override fun addContact(contact_name: String, contact_number: String): Observable<Answer> {
        return  apiService.addContact(contact_name, contact_number)
    }
}



