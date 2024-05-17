package io.github.byteblizzard.dddsample.library.domain.借出

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import org.springframework.stereotype.Component

class 借出书命令(val 二维码: String, val 借书人ID: 用户ID)

@Component
class 借出书命令处理器(
    private val 借出工厂: 借出工厂,
    private val 借出仓库: 借出仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 借出书命令) {
        val 借出 = this.借出工厂.创建借出(事件队列, 命令)
        this.借出仓库.保存(借出)
    }
}