package com.github.wisemann64.snapdishapp.ui.snap

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SnapViewModel : ViewModel() {
    companion object {
        private const val TAG = "SnapViewModel"
    }

    private val _picture = MutableLiveData<Uri?>()
    val picture: LiveData<Uri?> = _picture

    private lateinit var tempUri: Uri

    fun setPictureUri(uri: Uri) {
        _picture.postValue(uri)
    }

    fun clearPictureUri() {
        _picture.value = null
    }

    fun createImageUri(activity: Activity): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME,"JPEG_${timeStamp}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        tempUri =  activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?: throw IllegalStateException("Failed to create MediaStore entry")
        return tempUri
    }

    fun executeUriChange() {
        setPictureUri(tempUri)
    }


}