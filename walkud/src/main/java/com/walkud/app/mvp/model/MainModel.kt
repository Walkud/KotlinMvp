package com.walkud.app.mvp.model

import com.walkud.app.mvp.model.bean.HomeBean
import com.walkud.app.net.RetrofitManager
import com.walkud.app.rx.transformer.FilterAdTransformer
import io.reactivex.Observable

/**
 * 业务模型
 * Created by Zhuliya on 2018/11/7
 */
class MainModel {

    /**
     * 获取首页 Banner 数据
     */
    fun getFirstHomeData(): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(1)//1、先请求Banner数据
                .compose(FilterAdTransformer())//2、过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                .flatMap { bannerHomeBean ->
                    //3、根据 nextPageUrl 请求下一页数据
                    getMoreHomeData(bannerHomeBean.nextPageUrl)
                            .flatMap {
                                it.bannerData = bannerHomeBean//设置Banner数据

                                Observable.just(it)
                            }
                }
    }

    /**
     * 获取首页列表数据
     */
    fun getMoreHomeData(url: String): Observable<HomeBean> = RetrofitManager.service.getMoreHomeData(url).compose(FilterAdTransformer())


    /**
     * 请求热门关键词的数据
     */
    fun getHotWordData(): Observable<ArrayList<String>> = RetrofitManager.service.getHotWord()

    /**
     * 搜索关键词返回的结果
     */
    fun getSearchResult(words: String): Observable<HomeBean.Issue> = RetrofitManager.service.getSearchData(words)

    /**
     * 搜索结果列表加载更多数据
     */
    fun getIssueData(url: String): Observable<HomeBean.Issue> = RetrofitManager.service.getIssueData(url)

    /**
     * 根据item id获取相关视频
     */
    fun getRelatedData(id: Long): Observable<HomeBean.Issue> = RetrofitManager.service.getRelatedData(id)
}
