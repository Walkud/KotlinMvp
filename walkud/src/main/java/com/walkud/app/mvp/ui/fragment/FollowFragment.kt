package com.walkud.app.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.walkud.app.R
import com.walkud.app.mvp.base.MvpFragment
import com.walkud.app.mvp.model.bean.HomeBean
import com.walkud.app.mvp.presenter.FollowPresenter
import com.walkud.app.mvp.ui.activity.VideoDetailActivity
import com.walkud.app.mvp.ui.adapter.FollowAdapter
import com.walkud.app.rx.transformer.MultipleStatusViewTransformer
import io.reactivex.ObservableTransformer
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 *  关注 UI
 * Created by Zhuliya on 2018/11/29
 */
class FollowFragment : MvpFragment<FollowPresenter>() {

    private val followAdapter = FollowAdapter()

    private var loadingMore = false//是否正在加载更多

    companion object {
        fun getInstance() = FollowFragment()
    }

    override fun getP(): FollowPresenter = FollowPresenter().apply { view = this@FollowFragment }

    override fun getLayoutId(): Int = R.layout.layout_recyclerview

    /**
     * 获取加载进度切换事务
     */
    override fun <VT> multipleStatusViewTransformer(): ObservableTransformer<VT, VT> {
        return MultipleStatusViewTransformer(multipleStatusView)
    }

    /**
     * 初始化View
     */
    override fun initView(savedInstanceState: Bundle?, rootView: View) {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = followAdapter
        //实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    presenter.queryFollowList()
                }
            }
        })
    }

    /**
     * 添加点击事件
     */
    override fun addListener() {
        super.addListener()
        followAdapter.onItemClick = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val itemData = adapter.getItem(position) as HomeBean.Issue.Item
            goToVideoPlayer(view, itemData)
        }
    }

    /**
     * 懒加载数据
     */
    override fun onLazyLoadOnce() {
        super.onLazyLoadOnce()
        presenter.queryFollowList()
    }

    /**
     * 更新列表UI
     */
    fun updateListUi(issue: HomeBean.Issue) {
        loadingMore = false
        followAdapter.setNewData(issue.itemList)
    }


    /**
     * 显示错误信息
     */
    fun showErrorUi(errorCode: Int) {
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity!!, pair)
            ActivityCompat.startActivity(activity!!, intent, activityOptions.toBundle())
        } else {
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}