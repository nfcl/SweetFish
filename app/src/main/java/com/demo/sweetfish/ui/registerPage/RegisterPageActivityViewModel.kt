package com.demo.sweetfish.ui.registerPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.repository.UserRepository

class RegisterPageActivityViewModel : ViewModel() {

    private val registerUserInfo: User = User("", "", 0, 0)

    fun editAccount(newAccount: String) {
        registerUserInfo.account = newAccount
    }

    fun editPassword(newPassword: String) {
        registerUserInfo.password = newPassword
    }

    fun register(): Result<LiveData<Long>> {
        if (registerUserInfo.account.isEmpty()) {
            return Result.failure(Exception("账号为空"))
        } else if (registerUserInfo.password.isEmpty()) {
            return Result.failure(Exception("密码为空"))
        }
        return Result.success(UserRepository.register(registerUserInfo))
    }

    class RegisterPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterPageActivityViewModel() as T
        }
    }

}