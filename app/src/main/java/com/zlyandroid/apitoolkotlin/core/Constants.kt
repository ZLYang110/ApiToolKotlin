package com.zlyandroid.apitoolkotlin.core


/**
 * @author zhangliyang
 * @date 2020/11/13
 * GitHub: https://github.com/ZLYang110
 */
class Constants {


    /**
     * Tag fragment
     */
    object TAG_FRAGMENT {
        const val TYPE_HOME = 0
        const val TYPE_DISCOVERY = 1
        const val TYPE_CATEGORY = 2
        const val TYPE_MINE = 3
    }

    /**
     * 用户
     */
    object USER_KEY {
        const val SP_LOGIN = "xloong_user_info" //sp key
        const val USER_INFO = "xloong_mUserInfo" //用户信息
        const val AES = "xloong_mAES" //用户信息密钥
    }
}