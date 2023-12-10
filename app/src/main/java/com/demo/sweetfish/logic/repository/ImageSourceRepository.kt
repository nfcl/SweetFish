package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.ImageSource
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object ImageSourceRepository {

    fun insert(imageSource: ImageSource) = liveData(Dispatchers.IO) {
        emit(
            try {
                NetWorkService.imageSourceService().insert(imageSource).await()
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        )
    }

    fun findById(id: Long) = liveData(Dispatchers.IO) {
        emit(
            try {
                val imageSourceDao = AppDatabase.getDatabase().imageSourceDao()
                var result = imageSourceDao.findById(id)
                if (result == null) {
                    result = NetWorkService.imageSourceService().findById(id).await()
                    if (result != null) {
                        imageSourceDao.insert(result)
                    }
                }
                result
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        )
    }

}