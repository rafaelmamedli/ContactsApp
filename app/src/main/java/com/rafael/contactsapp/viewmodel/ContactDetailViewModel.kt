package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.rafael.contactsapp.data.repository.ContactsRepository
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val repository: ContactsRepositoryImp) :ViewModel() {





}