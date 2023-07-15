package com.rafael.contactsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafael.contactsapp.data.model.Answer
import com.rafael.contactsapp.data.repository.ContactsRepositoryImp
import com.rafael.contactsapp.data.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val repo: ContactsRepositoryImp) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _addContacts = MutableLiveData<UiState<Answer>>()

    val addContacts: LiveData<UiState<Answer>>
        get() = _addContacts

    fun addContact(contact_name: String, contact_number: String) {
        _addContacts.value = UiState.Loading
        val disposable = repo.addContact(contact_name, contact_number)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _addContacts.value = UiState.Success(response)
            }, { error ->
                _addContacts.value = UiState.Failure(error.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

