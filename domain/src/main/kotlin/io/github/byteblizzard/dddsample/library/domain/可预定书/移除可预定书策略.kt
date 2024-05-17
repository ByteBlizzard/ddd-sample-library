package io.github.byteblizzard.dddsample.library.domain.可预定书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.书.书仓库
import io.github.byteblizzard.dddsample.library.domain.书.书已下架
import io.github.byteblizzard.dddsample.library.domain.借出.书已被借出事件
import io.github.byteblizzard.dddsample.library.domain.预定.书已被预定
import io.github.byteblizzard.dddsample.library.domain.领域事件
import io.github.byteblizzard.dddsample.library.domain.领域事件监听器
import org.springframework.stereotype.Component

@Component
class 移除可预定书策略(
    private val 书仓库: 书仓库,
    private val 移除可预定书命令处理器: 移除可预定书命令处理器
) : 领域事件监听器{
    override fun 当已发生(事件队列: 临时事件队列, 事件: 领域事件) {
        when (事件) {
            is 书已被预定 -> 移除可预定书(事件队列, 事件.二维码)
            is 书已下架 -> 移除可预定书(事件队列, 事件.二维码)
            is 书已被借出事件 -> 移除可预定书(事件队列, 事件.二维码)
        }
    }

    private fun 移除可预定书(事件队列: 临时事件队列, 二维码: String) {
        val 书 = 书仓库.按照二维码查询或报错(二维码)
        移除可预定书命令处理器.处理(事件队列, 移除可预定书命令(
            isbn = 书.isbn,
            二维码 = 二维码
        ))
    }
}