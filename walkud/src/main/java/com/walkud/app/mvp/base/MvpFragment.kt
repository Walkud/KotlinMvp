package com.walkud.app.mvp.base

/**
 * Mvp Fragment 基类
 * Created by Zhuliya on 2018/11/8
 */
abstract class MvpFragment<P> : MvcFragment() {

    /**
     * 逻辑处理实例
     */
    val presenter: P by lazy {
        getP()
    }

    /**
     * 获取逻辑处理实例，子类实现
     */
    abstract fun getP(): P


}