package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.app.AppConfig
import com.zlyandroid.apitoolkotlin.core.UserInfoManager
import com.zlyandroid.apitoolkotlin.ui.mine.bean.UserInfoBean
import com.zlyandroid.apitoolkotlin.ui.mine.dialog.SelectCityDialog
import com.zlyandroid.apitoolkotlin.ui.mine.dialog.SelectLabelDialog
import com.zlyandroid.apitoolkotlin.ui.mine.dialog.SelectTimeDialog
import com.zlyandroid.apitoolkotlin.ui.mine.presenter.UserInfoPresenter
import com.zlyandroid.apitoolkotlin.ui.mine.view.UserInfoView
import com.zlyandroid.apitoolkotlin.util.DateUtil
import com.zlyandroid.apitoolkotlin.util.PictureSelector
import com.zlyandroid.apitoolkotlin.util.file.FileUtils
import com.zlyandroid.basic.common.base.BaseMvpActivity
import com.zlyandroid.basic.common.dialog.ListDialog
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.util.PermissionUtils
import com.zlyandroid.basic.common.util.SmartRefreshUtils
import com.zlyandroid.basic.common.util.log.L

import com.zlylib.mypermissionlib.RequestListener
import kotlinx.android.synthetic.main.activity_edit_userinfo.*
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 * desc:修改个人信息
 */
class UserInfoActivity : BaseMvpActivity<UserInfoView, UserInfoPresenter>(),UserInfoView {

    private val REQUEST_CODE_PERMISSION_OPENPIC = 1
    private val REQUEST_CODE_PERMISSION_TIKEPIC = 2
    private val REQUEST_CODE_PERMISSION_CROP = 3

    lateinit var userInfo: UserInfoBean

    private var imageUri: Uri? = null//拍照uri

    private lateinit var mSmartRefreshUtils: SmartRefreshUtils

    //技能标签
    var allLabelDatas: MutableList<String> =  ArrayList()

    //行业标签
    var allIndustryDatas: MutableList<String> =  ArrayList()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, UserInfoActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun createPresenter(): UserInfoPresenter = UserInfoPresenter()

    override fun getLayoutID(): Int = R.layout.activity_edit_userinfo


    override fun initView() {
        mSmartRefreshUtils = SmartRefreshUtils.with(srl)
        mSmartRefreshUtils.pureScrollMode()
    }

    override fun initData() {
        AppConfig.sHandler.post(Runnable { SelectCityDialog.initJsonData(getActivity()) })

        iv_head.setOnClickListener(this)
        ll_user_namre.setOnClickListener(this)
        ll_user_sex.setOnClickListener(this)
        ll_user_birthday.setOnClickListener(this)
        ll_user_region.setOnClickListener(this)
        ll_user_synopsis.setOnClickListener(this)
        ll_user_label.setOnClickListener(this)
        ll_user_trueName.setOnClickListener(this)
        ll_user_position.setOnClickListener(this)
        ll_user_industry.setOnClickListener(this)
        ll_user_company.setOnClickListener(this)
        ll_user_interest.setOnClickListener(this)
    }

    //填充数据
   fun initUserInfo(){
        mPresenter?.getSkillList()
        mPresenter?.getHobbyList()

        UserInfoManager.getUserInfo()?.let {
             userInfo = it
            if(!TextUtils.isEmpty(it.headImg)){
                Glide.with(getActivity()).load(it.headImg).into(iv_head)
            }
            tv_user_namre.setText(it.userNickname)
            if(it.sex == 1){
                tv_user_sex.setText("女")
            }else{
                tv_user_sex.setText("男")
            }
            tv_user_birthday.setText(it.birthday)
            tv_user_label.setText(it.labelList.toString())
            tv_user_region.setText(it.region)
            tv_user_synopsis.setText(it.autograph)
            tv_user_true.setText(it.userName)
            tv_user_position.setText(it.position)
            tv_user_industry.setText(it.industry)
            tv_user_company.setText(it.company)
            tv_user_interest.setText(it.interestList.toString())
           // L.i(userInfo.toFormatJson())
        }!!
   }

    override fun onClick2(v: View) {
        when (v.id) {
            R.id.iv_head -> {
                requestAlbumPermission(object : SimpleListener {
                    override fun onResult() {
                        /*
                        //拍照
                        imageUri = PictureSelector.getUri(getActivity())
                        imageUri?.let {
                            PictureSelector.takePicture(
                                getActivity(),
                                it,
                                REQUEST_CODE_PERMISSION_TIKEPIC
                            )
                        }
*/
                        PictureSelector.openPic(getActivity(), REQUEST_CODE_PERMISSION_OPENPIC)
                    }
                })
            }
            R.id.ll_user_namre -> {
                //昵称
                EditActivity.start(getActivity(), EditActivity.REQUEST_CODE_UPDATA_NAME)
            }
            R.id.ll_user_sex -> {
                //性别
                ListDialog.with(getActivity())
                    .cancelable(true)
                    .noYseBtn()
                    .datas("男", "女")
                    .currSelectPos(UserInfoManager.getUserInfo()!!.sex)
                    .listener(object : ListDialog.OnItemSelectedListener {
                        override fun onSelect(data: String, pos: Int) {
                            tv_user_sex.setText(data)
                            UserInfoManager.getUserInfo()?.apply {
                                sex = pos
                                UserInfoManager.saveUserInfo(this@apply)
                            }
                        }
                    })
                    .show()
            }
            R.id.ll_user_birthday -> {
                UserInfoManager.getUserInfo()?.apply {
                    SelectTimeDialog.show(getActivity(), DateUtil.strToDateLong(
                        birthday,
                        DateUtil.yyyy_mm_dd
                    ),
                        object : SimpleCallback<Date> {
                            override fun onResult(data: Date) {
                                birthday = DateUtil.getTime(DateUtil.yyyy_mm_dd, data)
                                age = DateUtil.BirthdayToAge(data)
                                UserInfoManager.saveUserInfo(this@apply)
                                tv_user_birthday.setText(birthday)
                            }
                        })
                }

            }
            R.id.ll_user_region -> {
                UserInfoManager.getUserInfo()?.apply {
                    SelectCityDialog.show(getActivity(), "", object : SimpleCallback<String> {
                        override fun onResult(data: String) {
                            region = data
                            tv_user_region.setText(data)
                            UserInfoManager.saveUserInfo(this@apply)
                        }
                    })
                }
            }
            R.id.ll_user_synopsis -> {
                //简介
                EditDesActivity.start(getActivity())
            }
            R.id.ll_user_label -> {
                //标签
                UserInfoManager.getUserInfo()?.apply {
                    SelectLabelDialog.with(getActivity()).allDatas(allLabelDatas).selectDatas(labelList).listener(object :SelectLabelDialog.OnItemSelectedListener{
                        override fun onSelect(data: List<String>, pos: Int) {
                            if(pos == -3){
                                //自定义
                                EditActivity.start(getActivity(), EditActivity.REQUEST_CODE_UPDATA_LABEL)
                            }else {
                                labelList = data as MutableList<String>
                                labelList = ArrayList()
                                labelList.addAll(data)
                                tv_user_label.setText(labelList.toString())
                                UserInfoManager.saveUserInfo(this@apply)
                            }
                        }

                    }).show()
                }

            }
            R.id.ll_user_trueName -> {
                //真实姓名
                EditActivity.start(getActivity(), EditActivity.REQUEST_CODE_UPDATA_TRUENAME)
            }
            R.id.ll_user_position -> {
                //职位
                EditActivity.start(getActivity(), EditActivity.REQUEST_CODE_UPDATA_POSITION)
            }
            R.id.ll_user_industry -> {
                //行业
                UserInfoManager.getUserInfo()?.apply {
                    SelectLabelDialog.with(getActivity()).allDatas(allIndustryDatas) .singleSelelct().selectData(industry).listener(object : SelectLabelDialog.OnItemSelectedListener {
                            override fun onSelect(data: List<String>, pos: Int) {
                                if(pos == -3){
                                    //自定义
                                    EditActivity.start(getActivity(), EditActivity.REQUEST_CODE_UPDATA_INDUSTRY)
                                }else{
                                    if(data.size>0){
                                        industry = data[0]
                                        tv_user_industry.setText(industry)
                                        UserInfoManager.saveUserInfo(this@apply)
                                    }
                                }

                            }

                        }).show()
                }
            }
            R.id.ll_user_company -> {
                //公司
                EditActivity.start(getActivity(), EditActivity.REQUEST_CODE_UPDATA_COMPANY)
            }
            R.id.ll_user_interest -> {
                //兴趣
                EditActivity.start(getActivity(), EditActivity.REQUEST_CODE_UPDATA_COMPANY)
            }
        }

    }

    private fun requestAlbumPermission(listener: SimpleListener) {
        PermissionUtils.request(
            object : RequestListener {
                override fun onSuccess() {
                    listener.onResult()
                }

                override fun onFailed() {
                    L.i("获取存储设备读取权限失败")
                }
            },
            getActivity(),
            *PermissionUtils.PermissionGroup.PERMISSIONS_CAME
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_PERMISSION_OPENPIC -> {
                val userIconUri = PictureSelector.result(resultCode, data)
                if (userIconUri != null) {
                    val sourceFile = PictureSelector.getFileFormUri(getActivity(), userIconUri)
                    if (sourceFile != null) {
                        val file = File(FileUtils.getAppFile() + File.separator, "headImg")
                        if (!file.exists()) {
                            file.mkdirs()
                        }
                        val clipFile = File(file, "user_icon.jpeg")
                        try {
                            clipFile.createNewFile()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        PictureSelector.crop(
                            getActivity(),
                            sourceFile,
                            clipFile,
                            REQUEST_CODE_PERMISSION_CROP
                        )
                    }
                }
            }
            REQUEST_CODE_PERMISSION_TIKEPIC -> {

                if (imageUri != null) {
                    val sourceFile = PictureSelector.getFileFormUri(getActivity(), imageUri!!)
                    if (sourceFile != null) {
                        L.i(sourceFile)
                        val file = File(FileUtils.getAppFile() + File.separator, "headImg")
                        if (!file.exists()) {
                            file.mkdirs()
                        }
                        val clipFile = File(file, "user_icon.jpeg")
                        try {
                            clipFile.createNewFile()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        PictureSelector.crop(
                            getActivity(),
                            sourceFile,
                            clipFile,
                            REQUEST_CODE_PERMISSION_CROP
                        )
                    }
                }

            }
            REQUEST_CODE_PERMISSION_CROP -> {
                if (resultCode == RESULT_OK) {
                    val file = File(FileUtils.getAppFile() + File.separator, "headImg")
                    if (!file.exists()) {
                        file.mkdirs()
                    }
                    val clipFile = File(file, "user_icon.jpeg")
                    if (clipFile.exists()) {
                        val path = clipFile.absolutePath
                        UserInfoManager.getUserInfo()?.apply {
                            headImg = path
                            UserInfoManager.saveUserInfo(this@apply)
                        }

                        Glide.with(getActivity()).load(path).into(iv_head)

                    }
                }
            }


        }
    }

    override fun skillSuccess(data: MutableList<String>) {
        allLabelDatas=data
    }

    override fun skillFailed() {
    }

    override fun hobbySuccess(data: MutableList<String>) {
        allIndustryDatas=data
    }

    override fun hobbyFailed() {
    }


    override fun onResume() {
        super.onResume()
        initUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()

    }





}