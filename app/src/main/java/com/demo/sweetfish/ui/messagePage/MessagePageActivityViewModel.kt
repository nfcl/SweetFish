package com.demo.sweetfish.ui.messagePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.ChatInfo
import com.demo.sweetfish.logic.model.Message
import com.demo.sweetfish.logic.repository.ChatInfoRepository
import com.demo.sweetfish.logic.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagePageActivityViewModel : ViewModel() {

    private val chatId: MutableLiveData<Long> = MutableLiveData(-1)
    private val mNewMessageContent: MutableLiveData<String> = MutableLiveData("")
    val newMessageContent: LiveData<String> get() = mNewMessageContent

    val messageList: LiveData<List<Message>> = Transformations.switchMap(chatId) { chatId ->
        MessageRepository.findByChatId(chatId)
    }

    val chatInfo: LiveData<ChatInfo> = Transformations.switchMap(chatId) { chatId ->
        ChatInfoRepository.findByChatId(chatId)
    }

    fun editContent(newContent: String) {
        mNewMessageContent.value = newContent
    }

    fun setChatId(chatId: Long) {
        this.chatId.value = chatId
    }

    fun publishNewMessage() {
        CoroutineScope(Dispatchers.IO).launch {
            MessageRepository.insert(
                Message(
                    mNewMessageContent.value!!,
                    0,
                    SweetFishApplication.loginUserId.value!!,
                    chatId.value!!
                )
            )
            forceRefresh()
        }
    }

    private fun forceRefresh() {
        chatId.postValue(chatId.value)
    }

    class MessageChatPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessagePageActivityViewModel() as T
        }
    }
}