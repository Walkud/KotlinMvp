package com.walkud.app.mvp.base

import android.content.Intent
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Mvc Fragment 基类
 * Created by Zhuliya on 2018/11/8
 */
open class MvcFragment : RxFragment() {

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
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 跳转
     * @param cls 类
     */
    fun forword(cls: Class<*>) {
        forword(Intent(activity, cls))
    }

    /**
     * 跳转
     * @param intent
     */
    fun forword(intent: Intent) {
        startActivity(intent)
    }
}