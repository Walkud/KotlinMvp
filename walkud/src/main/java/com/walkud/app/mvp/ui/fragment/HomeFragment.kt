package com.walkud.app.mvp.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.walkud.app.R
import com.walkud.app.mvp.base.MvpFragment
import com.walkud.app.mvp.model.bean.HomeBean
import com.walkud.app.mvp.presenter.HomePresenter
import com.walkud.app.mvp.ui.adapter.HomeAdapter
import com.walkud.app.rx.transformer.SmartRefreshTransformer
import com.walkud.app.utils.StatusBarUtil
import io.reactivex.ObservableTransformer
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 首页精选
 * Created by Zhuliya on 2018/11/20
 */
class HomeFragment : MvpFragment<HomePresenter>() {

    private var mTitle: String? = null

    private var mHomeAdapter = HomeAdapter()

    private var loadingMore = false

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getP(): HomePresenter = HomePresenter().apply { view = this@HomeFragment }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }


    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    /**
     * 获取刷新控件事务
     */
    override fun <VT> getSmartRefreshTransformer(): ObservableTransformer<VT, VT> {
        return SmartRefreshTransformer(mRefreshLayout)
    }

    /**
     * 初始化 View
     */
    override fun initView(savedInstanceState: Bundle?, rootView: View) {
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            presenter.refreshListData()
        }

        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            presenter.loadListData()
                        }
                    }
                }
            }

            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter.itemCount > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter.data
                        val item = itemList[currentVisibleItemPosition - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }


            }
        })

        iv_search.setOnClickListener { openSearchActivity() }

        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
    }

    /**
     * 懒加载数据
     */
    override fun onLazyLoadOnce() {
        super.onLazyLoadOnce()
        mRefreshLayout.autoRefresh(0)
    }

    private fun openSearchActivity() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val options = activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName) }
//            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
//        } else {
//            startActivity(Intent(activity, SearchActivity::class.java))
//        }
    }

//    override fun lazyLoad() {
//        mPresenter.requestHomeData(num)
//    }


//    /**
//     * 显示 Loading （下拉刷新的时候不需要显示 Loading）
//     */
//    override fun showLoading() {
//        if (!isRefresh) {
//            isRefresh = false
//            mLayoutStatusView?.showLoading()
//        }
//    }
//

    /**
     * 更新列表UI
     */
    fun updateListUI(data: List<HomeBean.Issue.Item>) {
        loadingMore = false
        multipleStatusView.showContent()
        mHomeAdapter.setNewData(data)
    }

    /**
     * 显示错误信息
     */
    fun showError(errorCode: Int) {
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }


    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
//        val intent = Intent(activity, VideoDetailActivity::class.java)
//        intent.putExtra(SyncStateContract.Constants.BUNDLE_VIDEO_DATA, itemData)
//        intent.putExtra(VideoDetailActivity.TRANSITION, true)
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
//            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    activity, pair)
//            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
//        } else {
//            activity.startActivity(intent)
//            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
//        }
    }


}