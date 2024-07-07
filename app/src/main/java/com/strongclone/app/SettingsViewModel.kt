package com.strongclone.app

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SettingsViewModel: ViewModel() {

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}