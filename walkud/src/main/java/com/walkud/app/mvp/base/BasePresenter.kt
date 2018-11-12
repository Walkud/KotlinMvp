package com.walkud.app.mvp.base

import com.walkud.app.utils.ReflectionUtils


/**
 * Mvp Presenter基类
 * Created by Zhuliya on 2018/11/2
 */
open class BasePresenter<V : MvpActivity<*>, out M> {

    /**
     * UI视图，即Activity
     */
    var view: V? = null

    /**
     * 业务模型，即XXXModel
     */
    val model: M by lazy {
        ReflectionUtils.getSuperClassGenricType<M>(this, 1)
    }


}