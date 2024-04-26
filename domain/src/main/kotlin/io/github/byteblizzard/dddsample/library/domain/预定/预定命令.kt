package io.github.byteblizzard.dddsample.library.domain.预定

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import org.springframework.stereotype.Component

class 预定命令 (
    val isbn: String,
    val 预定人: 用户ID
)

@Component
class 预定命令处理器(
    private val 预定工厂: 预定工厂,
    private val 预定仓库: 预定仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 预定命令) {
        val 预定 = this.预定工厂.创建(事件队列, 命令)
        this.预定仓库.保存(预定)
    }
}