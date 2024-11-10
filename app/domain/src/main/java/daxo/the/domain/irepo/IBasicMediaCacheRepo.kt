package daxo.the.domain.irepo

import daxo.the.domain.model.BasicMediaPage

interface IBasicMediaCacheRepo {
    fun add(basicMediaPage: BasicMediaPage)
    fun add(basicMediaPageList: List<BasicMediaPage>)
    fun get(id: Int): BasicMediaPage
    fun get(ids: List<Int>): List<Int>
}