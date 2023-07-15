package com.rafael.contactsapp.data.repository

import com.rafael.contactsapp.data.model.Answer
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.util.UiState
import io.reactivex.Observable

interface ContactsRepository {
    fun getAllResults(): Observable<ContactsAnswer>
    fun deleteContact(contact_id: Int): Observable<Answer>
    fun addContact(contact_name: String, contact_number: String): Observable<Answer>
}