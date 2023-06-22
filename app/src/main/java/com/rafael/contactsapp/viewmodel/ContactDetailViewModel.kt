package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.repository.ContactsRepository
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val repo: ContactsRepositoryImp) :ViewModel() {


    private val _addContacts = MutableLiveData<UiState<String>>()

    val addContacts: LiveData<UiState<String>>
        get() = _addContacts

    fun addContact(contact_name: String,contact_number:String) = viewModelScope.launch(Dispatchers.Main){
        _addContacts.value = UiState.Loading
        repo.addContact(contact_name,contact_number) { result ->
            _addContacts.value = result

        }
    }

}