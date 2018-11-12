package com.walkud.app.rx

import android.os.Looper
import com.orhanobut.logger.Logger
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * 二次封装Subscriber
 * Created by Zhuliya on 2018/11/8
 */
abstract class RxSubscribe<T> : Subscriber<T> {

    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription?) {
    }

    override fun onNext(t: T) {
        call(t)
    }

    override fun onError(t: Throwable?) {
        Logger.e("isUiThread:${Looper.getMainLooper() == Looper.myLooper()}", t)
    }

    abstract fun call(t: T)

}