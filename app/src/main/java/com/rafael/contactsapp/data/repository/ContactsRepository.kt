package com.rafael.contactsapp.data.repository

import androidx.lifecycle.LiveData
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.util.UiState
import io.reactivex.rxjava3.core.Observable

interface ContactsRepository {
    fun getAllResults(): Observable<UiState<List<Contacts>>>
    fun deleteContact(contact_id: Int): Observable<UiState<List<Contacts>>>
    fun addContact(contact_name: String, contact_number: String): Observable<UiState<String>>
}