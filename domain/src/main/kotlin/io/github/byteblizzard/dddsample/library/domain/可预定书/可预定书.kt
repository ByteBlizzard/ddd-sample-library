package io.github.byteblizzard.dddsample.library.domain.可预定书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.领域事件

interface 可预定书 {
    fun 是否包含书(二维码: String): Boolean
    fun 添加(事件队列: 临时事件队列, 二维码: String)
    fun 移除(事件队列: 临时事件队列, 二维码: String)
    fun 不够了(): Boolean
    fun 随机选取一本书(): String
}

class 可预定书实现(
    val isbn: String,
    val 二维码集合: MutableSet<String>
): 可预定书 {
    override fun 是否包含书(二维码: String): Boolean {
        return this.二维码集合.contains(二维码)
    }

    override fun 添加(事件队列: 临时事件队列, 二维码: String) {
        if (this.二维码集合.contains(二维码)) {
            return
        }
        this.二维码集合.add(二维码)
        事件队列.入队(书已可预定(isbn, 二维码))
    }

    override fun 移除(事件队列: 临时事件队列, 二维码: String) {
        if (!this.二维码集合.contains(二维码)) {
            return
        }
        this.二维码集合.remove(二维码)
        事件队列.入队(书已不可预定(isbn, 二维码))
    }

    override fun 不够了(): Boolean {
        return this.二维码集合.isEmpty()
    }

    override fun 随机选取一本书(): String {
        return this.二维码集合.random()
    }
}

interface 可预定书仓库 {
    fun 按照id查找或报错(isbn: String): 可预定书
    fun 保存(可预定书: 可预定书)
}

class 书已不可预定(
    val isbn: String,
    val 二维码: String
): 领域事件

class 书已可预定(
    val isbn: String,
    val 二维码: String
): 领域事件
