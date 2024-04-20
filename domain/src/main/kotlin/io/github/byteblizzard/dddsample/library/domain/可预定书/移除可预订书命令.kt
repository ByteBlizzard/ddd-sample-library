package io.github.byteblizzard.dddsample.library.domain.可预定书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Component

class 移除可预定书命令(
    val isbn: String,
    val 二维码: String
)

@Component
class 移除可预定书命令处理器(
    val 可预定书仓库: 可预定书仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 添加可预定书命令) {
        val 可预定书 = this.可预定书仓库.按照id查找或报错(命令.isbn)
        可预定书.移除(事件队列, 命令.二维码)
        this.可预定书仓库.保存(可预定书)
    }
}