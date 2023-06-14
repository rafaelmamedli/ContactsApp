package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.model.ContactsAnswer
import com.rafael.contactsapp.data.repository.ContactsRepository
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repo: ContactsRepositoryImp):ViewModel() {




    fun getList(): MutableLiveData<List<Contacts>?> {

        viewModelScope.launch {
            repo.getAllResults()
        }
       return repo.contactsList

}}