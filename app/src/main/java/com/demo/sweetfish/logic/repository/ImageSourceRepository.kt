package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.ImageSource
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.await
import utils.DrawableUtils
import utils.DrawableUtils.Companion.toBytes

object ImageSourceRepository {

    suspend fun insert(imageSource: ImageSource): Long {
        return try {
            val byteArray = imageSource.content?.toBytes()!!
            val drawable = DrawableUtils.createDrawableFromByteArray(byteArray, "")
            imageSource.content = drawable
                val requestFile =
                    RequestBody.create(MediaType.parse("image/png"), imageSource.content?.toBytes()!!)
                val body = MultipartBody.Part.createFormData("file", "fileName.png", requestFile)
                imageSource.id = NetWorkService.imageSourceService().insert(body).await()
                AppDatabase.getDatabase().imageSourceDao().insert(imageSource)
                imageSource.id
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
    }

    fun findById(id: Long) = liveData(Dispatchers.IO) {
        emit(
            try {
                val imageSourceDao = AppDatabase.getDatabase().imageSourceDao()
                var result = imageSourceDao.findById(id)
                if (result == null) {
                    val responseBody = NetWorkService.imageSourceService().findById(id).await()
                    if (responseBody != null) {
                        result = ImageSource(
                            DrawableUtils.createDrawableFromByteArray(
                                responseBody.bytes(), id.toString()
                            ), id
                        )
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