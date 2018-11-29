package com.walkud.app.rx.transformer

import com.classic.common.MultipleStatusView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * 进度、错误、内容切换View事务，免去在接口回调中添加控制逻辑
 * Created by Zhuliya on 2018/11/21
 */
class MultipleStatusViewTransformer<T>(var multipleStatusView: MultipleStatusView?) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    multipleStatusView?.showLoading()
                }
                .doOnError {
                    finish()
                }.doOnComplete {
                    finish()
                }

    }

    /**
     * 加载完成，结束下拉刷新或上拉加载
     */
    fun finish() {
        multipleStatusView?.showContent()
        multipleStatusView = null
    }

}