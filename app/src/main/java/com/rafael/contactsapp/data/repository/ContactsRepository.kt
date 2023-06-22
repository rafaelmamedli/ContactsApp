package com.rafael.contactsapp.data.repository

import androidx.lifecycle.LiveData
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.util.UiState

interface ContactsRepository {
    suspend fun getAllResults(result: (UiState<List<Contacts>>) -> Unit)
    suspend fun deleteContact(contact_id :Int, result: (UiState<List<Contacts>>) -> Unit)
    suspend fun addContact(contact_name:String,contact_number:String, result: (UiState<String>) -> Unit)

}