package com.example.linkedlearning.views

sealed class UIevents {
    data class ShowErrorSnackBar(val msg : String):UIevents()
    data class ShowSuccessSnackBar(val msg: String):UIevents()
}