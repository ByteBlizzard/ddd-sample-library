package io.github.byteblizzard.dddsample.library.domain.书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Service

class 入库新书命令(
    val 二维码: String,
    val isbn: String,
    val 书名: String,
    val 图片: String,
    val 简介: String
)

@Service
class 入库新书命令处理器(
    private val 书仓库: 书仓库,
    private val 书工厂: 书工厂
) {
    fun 处理(事件队列: 临时事件队列, 命令: 入库新书命令) {
        val 书聚合 = this.书工厂.新建(事件队列, 命令)
        书仓库.保存(书聚合)
    }
}