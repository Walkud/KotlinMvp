package com.walkud.app.mvp.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle

/**
 * Activity生命周期及部分回调，可添加
 * Created by Zhuliya on 2018/11/7
 */
abstract class ViewLifecycle {

    /**
     * MvpActivity OnCreate 时调用，需要时子类实现
     */
    fun onCreate(savedInstanceState: Bundle?) {
    }

    /**
     * MvpActivity onStart 时调用，需要时子类实现
     */
    fun onStart() {
    }

    /**
     * MvpActivity onRestart 时调用，需要时子类实现
     */
    fun onRestart() {
    }

    /**
     * MvpActivity onResume 时调用，需要时子类实现
     */
    fun onResume() {
    }

    /**
     * MvpActivity onPause 时调用，需要时子类实现
     */
    fun onPause() {
    }

    /**
     * MvpActivity onStop 时调用，需要时子类实现
     */
    fun onStop() {
    }

    /**
     * MvpActivity onDestroy 时调用，需要时子类实现
     */
    fun onDestroy() {
    }

    /**
     * MvpActivity onSaveInstanceState 时调用，需要时子类实现
     */
    fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
    }

    /**
     * MvpActivity onActivityResult 时调用，需要时子类实现
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

}
