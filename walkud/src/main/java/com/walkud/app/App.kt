package com.walkud.app

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.walkud.app.utils.DisplayManager
import kotlin.properties.Delegates

/**
 * Application
 * Created by Zhuliya on 2018/11/7
 */
class App : Application() {

    companion object {
        val TAG: String = App::class.java.simpleName

        var instance: App by Delegates.notNull()
            private set

    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        initLog()
        DisplayManager.init(this)//初始化UI显示工具类
    }


    /**
     * 初始化日志
     */
    private fun initLog() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Walkud")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}