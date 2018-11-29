package com.walkud.app.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.walkud.app.R
import com.walkud.app.mvp.base.MvcFragment

/**
 * 分类 UI
 * Created by Zhuliya on 2018/11/29
 */
class CategoryFragment : MvcFragment() {

    companion object {
        fun getInstance() = CategoryFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun initView(savedInstanceState: Bundle?, rootView: View) {
    }
}