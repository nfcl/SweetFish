package com.demo.sweetfish.ui.loginPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.repository.UserRepository

class LoginPageActivityViewModel : ViewModel() {

    private var loginAccount = ""
    private var loginPassword = ""

    fun editAccount(account: String) {
        loginAccount = account
    }

    fun editPassword(password: String) {
        loginPassword = password
    }

    fun login(): Result<LiveData<User?>> {
        if (loginAccount.isEmpty()) {
            return Result.failure(Exception("账号为空"))
        } else if (loginPassword.isEmpty()) {
            return Result.failure(Exception("密码为空"))
        }
        return Result.success(UserRepository.login(loginAccount, loginPassword))
    }

    class LoginPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginPageActivityViewModel() as T
        }
    }

}