package com.walkud.app.mvp.presenter

import com.trello.rxlifecycle2.android.FragmentEvent
import com.walkud.app.common.exception.ExceptionHandle
import com.walkud.app.mvp.base.BasePresenter
import com.walkud.app.mvp.model.MainModel
import com.walkud.app.mvp.model.bean.HomeBean
import com.walkud.app.mvp.ui.fragment.HomeFragment
import com.walkud.app.rx.RxSubscribe
import com.walkud.app.rx.transformer.NetTransformer
import io.reactivex.Observable

/**
 * Created by Zhuliya on 2018/11/20
 */
class HomePresenter : BasePresenter<HomeFragment, MainModel>() {

    var homeBean: HomeBean? = null//第一页Banner及列表数据
    var nextPageUrl: String? = null//下一页请求Url

    /**
     * 刷新列表数据
     */
    fun refreshListData() {
        homeBean = null
        nextPageUrl = null
        loadListData()
    }

    /**
     * 获取列表数据
     */
    fun loadListData() {
        //如果下一页url为空，则获取首页列表数据，否则为加载更多
        val observable: Observable<HomeBean> = if (nextPageUrl == null) {
            model.getFirstHomeData()
        } else {
            model.getMoreHomeData(nextPageUrl!!)
        }

        observable.compose(NetTransformer())
                .compose(view.getSmartRefreshTransformer())
                .compose(bindFragmentUntilEvent(FragmentEvent.DESTROY))
                .subscribe(object : RxSubscribe<HomeBean>() {
                    override fun call(result: HomeBean) {
                        if (homeBean == null) {
                            //第一页
                            homeBean = result
                            //更新BannerUI
                            view.updateBannerUi(result.bannerData?.issueList!![0].itemList)
                        } else {
                            //加载更多，添加至缓存列表中
                            homeBean!!.issueList[0].itemList.addAll(result.issueList[0].itemList)
                        }

                        nextPageUrl = result.nextPageUrl

                        view.updateListUi(homeBean!!.issueList[0].itemList)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view.showToast(ExceptionHandle.handleException(e))
                        view.showError(ExceptionHandle.errorCode)
                    }
                })
    }
}