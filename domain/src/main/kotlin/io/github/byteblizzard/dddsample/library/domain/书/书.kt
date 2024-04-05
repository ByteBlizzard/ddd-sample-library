package io.github.byteblizzard.dddsample.library.domain.书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.领域事件

interface 书聚合 {
    fun 上架(事件队列: 临时事件队列)
    fun 下架(事件队列: 临时事件队列)
}

class 书聚合实现(
    val 二维码: String,
    val isbn: String,
    var 上架: Boolean
): 书聚合 {
    override fun 上架(事件队列: 临时事件队列) {
        if (上架) {
            return
        }

        this.上架 = true
        事件队列.入队(书已上架(this.二维码))
    }

    override fun 下架(事件队列: 临时事件队列) {
        if (!上架) {
            return
        }

        this.上架 = false
        事件队列.入队(书已下架(this.二维码))
    }

}

interface 书仓库 {
    fun 按照二维码查询或报错(二维码: String): 书聚合
    fun 按照二维码查询(二维码: String): 书聚合?
    fun 保存(书: 书聚合)
}

class 书已上架(二维码: String): 领域事件
class 书已下架(二维码: String): 领域事件
