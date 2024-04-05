package io.github.byteblizzard.dddsample.library.domain.书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Service

class 下架命令(
    val 二维码: String
)

@Service
class 下架命令处理器(
    private val 书仓库: 书仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 下架命令) {
        val 书 = this.书仓库.按照二维码查询或报错(命令.二维码)
        书.下架(事件队列)
        书仓库.保存(书)
    }

}