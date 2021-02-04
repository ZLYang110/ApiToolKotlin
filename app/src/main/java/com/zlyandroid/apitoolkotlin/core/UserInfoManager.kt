package com.zlyandroid.apitoolkotlin.core

import android.content.Context
import android.text.TextUtils
import android.util.Base64
import com.zlyandroid.apitoolkotlin.ui.mine.bean.UserInfoBean
import com.zlyandroid.basic.common.util.AesEncryptionUtils
import com.zlyandroid.basic.common.util.PreUtils
import com.zlyandroid.basic.common.util.log.L
import java.io.*
import javax.crypto.spec.SecretKeySpec

object UserInfoManager {
    private const val TAG = "UserInfoManager"
    private val mSPUtils: PreUtils = PreUtils.newInstance(Constants.USER_KEY.USER_INFO)

    /**
     * 获取用户信息
     * @return
     */
    fun getUserInfo(): UserInfoBean? {
        val keySpec:SecretKeySpec
        try {
            keySpec = getAesKey()
        }catch (e: IllegalArgumentException ){
            return null
        }

        val userInfo: String = AesEncryptionUtils.decrypt(
            keySpec,
            mSPUtils.get(Constants.USER_KEY.USER_INFO, "")
        )
        if (TextUtils.isEmpty(userInfo))
            return null
        try {
            return translateStringTOUserInfo(userInfo)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 保存用户信息
     * @param user
     */
    fun saveUserInfo(user: UserInfoBean) {
        try {
            val userInfo = translateUserInfoTOString(user)
            val key: SecretKeySpec = AesEncryptionUtils.createKey()
            val aesContent: String = AesEncryptionUtils.encrypt(key, userInfo)
            //保存用户信息
            mSPUtils.save(Constants.USER_KEY.USER_INFO, aesContent)
            //保存密钥
            saveAesKey(key)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun logout() {
        mSPUtils.clear()
    }

    private fun saveAesKey(keySpec: SecretKeySpec) {
        mSPUtils.save(
            Constants.USER_KEY.AES,
            Base64.encodeToString(keySpec.encoded, Base64.DEFAULT)
        )
    }

    private fun getAesKey(): SecretKeySpec {
        val keyStr = mSPUtils.get(Constants.USER_KEY.AES, "") as String
        return AesEncryptionUtils.getSecretKey(Base64.decode(keyStr, Base64.DEFAULT))
    }



    /**
     * User 转 String
     * @param user
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun translateUserInfoTOString(user: UserInfoBean): String {
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(user)
        return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)
    }

    /**
     * String 转 User
     * @param userStr
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun translateStringTOUserInfo(userStr: String?): UserInfoBean? {
        if (userStr == null) return null
        val base64Bytes = Base64.decode(userStr, Base64.DEFAULT)
        val bis = ByteArrayInputStream(base64Bytes)
        val ois = ObjectInputStream(bis)
        return ois.readObject() as UserInfoBean
    }

    fun getUserId(): String {
        val loginBean: UserInfoBean = getUserInfo() ?: return "0"
        return loginBean.userId
    }

}