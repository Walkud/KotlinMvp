package com.walkud.app.mvp.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.LayoutRes
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.walkud.app.R
import com.walkud.app.common.extensions.closeKeyBoard
import com.walkud.app.rx.transformer.EmptyTransformer
import com.walkud.app.rx.transformer.ProgressTransformer
import io.reactivex.ObservableTransformer

/**
 * Activity 基类
 * Created by Zhuliya on 2018/11/7
 */
abstract class MvcActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initView(savedInstanceState)
        addListener()
        processLogic(savedInstanceState)
    }

    /**
     * 获取RootLayout布局Id
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化 View 控件
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 给 View 控件添加事件监听器
     */
    open fun addListener() {}

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    open fun processLogic(savedInstanceState: Bundle?) {}

    /**
     * 显示Toast
     * @param resId 文本资源Id
     */
    fun showToast(resId: Int) {
        showToast(getString(resId))
    }

    /**
     * 显示Toast
     * @param msg 文本
     */
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 跳转
     * @param cls 类
     */
    fun forward(cls: Class<*>) {
        closeKeyBoard()
        forward(Intent(this, cls))
    }

    /**
     * 跳转
     * @param intent
     */
    fun forward(intent: Intent) {
        closeKeyBoard()
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
     * 回退
     */
    fun backward() {
        closeKeyBoard()
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

    /**
     * 获取异步进度加载事务，子类复写
     * 默认返回 默认进度框事务
     */
    open fun <VT> getPrgressTransformer(): ObservableTransformer<VT, VT> = ProgressTransformer(this)

    /**
     * 获取异步进度下拉或上拉加载事务，子类复写
     * 默认返回 空事务
     */
    open fun <VT> getSmartRefreshTransformer(): ObservableTransformer<VT, VT> = EmptyTransformer()

    /**
     * 获取进度、错误、内容切换View事务，子类复写
     */
    open fun <VT> getMultipleStatusViewTransformer(): ObservableTransformer<VT, VT> = EmptyTransformer()
}