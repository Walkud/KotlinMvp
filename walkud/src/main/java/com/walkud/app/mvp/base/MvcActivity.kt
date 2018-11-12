package com.walkud.app.mvp.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.walkud.app.R

/**
 * Activity 基类
 * Created by Zhuliya on 2018/11/7
 */
open class MvcActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 显示Toast
     * @param resId 文本资源Id
     */
    fun toast(resId: Int) {
        toast(getString(resId))
    }

    /**
     * 显示Toast
     * @param msg 文本
     */
    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 跳转
     * @param cls 类
     */
    fun forward(cls: Class<*>) {
        forward(Intent(this, cls))
    }

    /**
     * 跳转
     * @param intent
     */
    fun forward(intent: Intent) {
        startActivity(intent)
    }

    /**
     * 跳转并关闭当前页面
     * @param intent
     */
    fun forwardAndFinish(cls: Class<*>) {
        forward(cls)
        finish()
    }

    /**
     * 跳转并关闭当前页面
     */
    fun forwardAndFinish(intent: Intent) {
        forward(intent)
        finish()
    }

    /**
     * 显示权限申请说明
     * @param permission 说明文本
     */
    fun showPermissionDialog(permission: String) {
        AlertDialog.Builder(this)
                .setMessage(permission)
                .setTitle(getString(R.string.string_help_text, permission))
                .setPositiveButton("设置") { dialog, which ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    forward(intent)
                }
                .create()
                .show()
    }


}