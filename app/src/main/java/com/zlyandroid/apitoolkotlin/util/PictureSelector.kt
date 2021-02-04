package com.zlyandroid.apitoolkotlin.util

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.CursorLoader
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.zlyandroid.apitoolkotlin.util.file.FileUtils
import java.io.File

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 *  desc:  拍照，相册，录像
 */
object PictureSelector {

    /**
     * @param activity    当前activity
     * @param imageUri    拍照后照片存储路径
     * @param requestCode 调用系统相机请求码
     */
    @JvmStatic
    fun takePicture(activity: Activity, imageUri: Uri, requestCode: Int) = apply {
        activity.startActivityForResult(takePictureIntent(imageUri), requestCode)
    }
    /**
     * @param fragment    当前fragment
     * @param imageUri    拍照后照片存储路径
     * @param requestCode 调用系统相机请求码
     */
    @JvmStatic
    fun takePicture(fragment: Fragment, imageUri: Uri, requestCode: Int) = apply {
        fragment.startActivityForResult(takePictureIntent(imageUri), requestCode)
    }
    /**
     * @param activity    当前activity
     * @param requestCode 打开相册的请求码
     */
    @JvmStatic
    fun openPic(activity: Activity, requestCode: Int) = apply {
        activity.startActivityForResult(openPicIntent(), requestCode)
    }
    /**
     * @param activity    当前activity
     * @param requestCode 打开相册的请求码
     */
    @JvmStatic
    fun openPic(fragment: Fragment, requestCode: Int) = apply {
        fragment.startActivityForResult(openPicIntent(), requestCode)
    }

    /**
     * @param activity    当前activity
     * @param mediaUri    录像后视频存储路径
     * @param requestCode 调用系统相机请求码
     */
    @JvmStatic
    fun takeMedia(activity: Activity, imageUri: Uri, requestCode: Int) = apply {
        activity.startActivityForResult(takeMediaIntent(imageUri), requestCode)
    }

    /**
     * @param activity    当前activity
     * @param mediaUri    录像后视频存储路径
     * @param requestCode 调用系统相机请求码
     */
    @JvmStatic
    fun takeMedia(fragment: Fragment, imageUri: Uri, requestCode: Int) = apply {
        fragment.startActivityForResult(takeMediaIntent(imageUri), requestCode)
    }


    @JvmStatic
    fun crop(activity: Activity, fileSource: File, fileClip: File, requestCode: Int) = apply {
        activity.startActivityForResult(cropIntent(activity, fileSource, fileClip), requestCode)
    }

    @JvmStatic
    fun crop(fragment: Fragment, fileSource: File, fileClip: File, requestCode: Int) = apply {
        val context = fragment.context ?: return@apply
        fragment.startActivityForResult(cropIntent(context, fileSource, fileClip), requestCode)
    }

    private fun openPicIntent(): Intent {
        return Intent(Intent.ACTION_PICK).apply {
            setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*")
        }
    }

    private fun takePictureIntent(imageUri: Uri): Intent {
        return Intent().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
    }

    private fun takeMediaIntent(imageUri: Uri): Intent {
        return Intent().apply {
            setAction(MediaStore.ACTION_VIDEO_CAPTURE);
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
    }

    private fun cropIntent(context: Context, fileSource: File, fileClip: File): Intent {
        return Intent("com.android.camera.action.CROP").apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setDataAndType(getUriFromFile(context, fileSource), "image/*")
            } else {
                setDataAndType(Uri.fromFile(fileSource), "image/*")
            }
            putExtra("crop", "true")
            putExtra("scale", true)
            putExtra("aspectX", 1)
            putExtra("aspectY", 1)
            putExtra("outputX", 200)
            putExtra("outputY", 200)
            putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileClip))
            putExtra("noFaceDetection", true)
            putExtra("scaleUpIfNeeded", true)
            putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            putExtra("return-data", false)
        }
    }

    @JvmStatic
    fun result(resultCode: Int, data: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK) return null
        if (null == data) return null
        if (null == data.data) return null
        return data.data
    }

    @JvmStatic
    fun getFileFormUri(context: Context, uri: Uri): File? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        CursorLoader(context, uri, projection, null, null, null).loadInBackground()?.apply {
            getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }?.use { cursor ->
            val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return File(cursor.getString(index))
        }
        var path = uri.path ?: return null
        return when (uri.scheme) {
            "file" -> {
                val buff = StringBuffer()
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                    .append("'$path'").append(
                        ")"
                    )
                context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(
                        MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA
                    ), buff.toString(), null, null
                )?.use { cursor ->
                    var dataIdx: Int
                    while (!cursor.isAfterLast) {
                        dataIdx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                        path = cursor.getString(dataIdx)
                        cursor.moveToNext()
                    }
                    File(path)
                }
            }
            "content" -> {
                context.contentResolver.query(
                    uri,
                    arrayOf(MediaStore.Images.Media.DATA),
                    null,
                    null,
                    null
                )?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        path = cursor.getString(columnIndex)
                    }
                    File(path)
                }
            }
            else -> {
                null
            }
        }
    }

    fun getUri(mContext: Context): Uri? {
        val imageUri: Uri?
        if (FileUtils.isSDCardAlive()) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                imageUri = mContext.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    ContentValues()
                )
            } else {
                val fileUri: File =
                    File(FileUtils.getAppFile() + "/" + System.currentTimeMillis() + ".jpg")
                //通过FileProvider创建一个content类型的Uri
                imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(
                        mContext,
                        mContext.packageName + ".provider",
                        fileUri
                    )
                } else {
                    Uri.fromFile(fileUri)
                }
                Log.e("sss", "ImageUrl = $imageUri")
            }
            return imageUri
        }
        return null
    }


    @JvmStatic
    fun getUriFromFile(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ",
            arrayOf(filePath), null
        )
        cursor.use { _ ->
            return if (cursor != null && cursor.moveToFirst()) {
                val id = cursor.getInt(
                    cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID)
                )
                val baseUri = Uri.parse("content://media/external/images/media")
                Uri.withAppendedPath(baseUri, "" + id)
            } else {
                if (imageFile.exists()) {
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.DATA, filePath)
                    context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                    )
                } else {
                    null
                }
            }
        }
    }

}