package com.rafael.contactsapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Answer(
    @SerializedName("success") var success: Int,
    @SerializedName("message") var message: String
) : Serializable {
}