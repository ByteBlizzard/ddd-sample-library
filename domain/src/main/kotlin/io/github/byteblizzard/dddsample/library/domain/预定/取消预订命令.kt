package io.github.byteblizzard.dddsample.library.domain.预定

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Component

class 取消预订命令(
    val 预定ID: 预定ID
)

@Component
class 取消预订命令处理器(
    val 预定仓库: 预定仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 取消预订命令) {
        val 预定 = this.预定仓库.按照id查找或报错(命令.预定ID)
        预定.取消(事件队列, 命令)
        this.预定仓库.保存(预定)
    }
}