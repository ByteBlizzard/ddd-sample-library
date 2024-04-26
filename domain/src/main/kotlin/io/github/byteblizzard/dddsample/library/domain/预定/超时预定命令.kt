package io.github.byteblizzard.dddsample.library.domain.预定

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Component

class 超时预定命令(
    val 预定id: 预定ID
)

@Component
class 超时预定命令处理器(
    private val 预定仓库: 预定仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 超时预定命令) {
        val 预定 = this.预定仓库.按照id查找或报错(命令.预定id)
        预定.尝试超时(事件队列)
        this.预定仓库.保存(预定)
    }
}