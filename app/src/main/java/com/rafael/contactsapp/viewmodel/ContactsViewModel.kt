package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repo: ContactsRepositoryImp) : ViewModel() {

    private val _getcontacts = MutableLiveData<UiState<List<Contacts>>>()




    init {
        getContacts()
    }
    val getContacts: LiveData<UiState<List<Contacts>>>
        get() = _getcontacts



    fun getContacts() = viewModelScope.launch(Dispatchers.Main) {
        _getcontacts.value = UiState.Loading
        repo.getAllResults() { result ->
            _getcontacts.value = result }

    }
}