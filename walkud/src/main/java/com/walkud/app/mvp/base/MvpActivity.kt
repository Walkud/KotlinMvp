package com.walkud.app.mvp.base

/**
 * Mvp Activity 基类
 * Created by Zhuliya on 2018/11/7
 */
abstract class MvpActivity<P> : MvcActivity() {

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

//    //声明周期处理
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        presenter.onCreate(savedInstanceState)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        presenter.onStart()
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        presenter.onRestart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        presenter.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        presenter.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        presenter.onStop()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        presenter.onDestroy()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        presenter.onSaveInstanceState(outState, outPersistentState)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        presenter.onActivityResult(requestCode, resultCode, data)
//    }
//
//    //声明周期处理 End
}