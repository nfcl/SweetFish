package com.demo.sweetfish.ui.chatPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.MessageInfo
import com.demo.sweetfish.logic.repository.MessageInfoRepository

class ChatPageActivityViewModel : ViewModel() {

    private val userId: LiveData<Long> = MutableLiveData(SweetFishApplication.loginUserId.value!!)

    val chatList: LiveData<List<MessageInfo>> = Transformations.switchMap(userId) { userId ->
        MessageInfoRepository.findByUserId(userId)
    }

    class MessagePageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatPageActivityViewModel() as T
        }
    }
}