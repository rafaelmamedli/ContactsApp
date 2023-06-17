package com.rafael.contactsapp.data.repository

import androidx.lifecycle.LiveData
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.util.UiState

interface ContactsRepository {
     fun getAllResults(result: (UiState<List<Contacts>>) -> Unit)
}