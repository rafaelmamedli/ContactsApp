package com.rafael.contactsapp.data.repository

import androidx.lifecycle.LiveData
import com.rafael.contactsapp.data.model.ContactsAnswer

interface ContactsRepository {

    fun getAllResults(): LiveData<List<ContactsAnswer>>

}