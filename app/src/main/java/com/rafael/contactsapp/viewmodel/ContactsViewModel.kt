package com.rafael.contactsapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafael.contactsapp.data.model.Answer
import com.rafael.contactsapp.data.model.Contacts
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repo: ContactsRepositoryImp) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _getContacts = MutableLiveData<UiState<List<Contacts>>>()
    private val _deleteContacts = MutableLiveData<UiState<Answer>>()



    val getContacts: LiveData<UiState<List<Contacts>>>
        get() = _getContacts

    val deleteContact: LiveData<UiState<Answer>>
        get() = _deleteContacts

    fun fetchContacts() {
        _getContacts.value = UiState.Loading

        val disposable = repo.getAllResults()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _getContacts.value = UiState.Success(response.contacts)
            }, { error ->
                _getContacts.value = UiState.Failure(error.localizedMessage)
            })

        compositeDisposable.add(disposable)
    }

    fun deleteContact(contactId: Int) {
      val disposable =  repo.deleteContact(contactId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _deleteContacts.value = UiState.Success(response)
            }, { error ->
                _deleteContacts.value = UiState.Failure(error.localizedMessage)
            })
        compositeDisposable.add(disposable)

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


