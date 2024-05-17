package io.github.byteblizzard.dddsample.library.domain

import io.github.byteblizzard.dddsample.library.domain.书.书仓库
import io.github.byteblizzard.dddsample.library.domain.借出.书已被归还
import io.github.byteblizzard.dddsample.library.domain.借出.还书命令
import io.github.byteblizzard.dddsample.library.domain.借出.还书命令处理器
import io.github.byteblizzard.dddsample.library.domain.可预定书.添加可预定书命令
import io.github.byteblizzard.dddsample.library.domain.可预定书.添加可预定书命令处理器
import org.springframework.stereotype.Component

@Component
class 还书服务(
    private val 还书命令处理器: 还书命令处理器,
    private val 添加可预定书命令处理器: 添加可预定书命令处理器,
    private val 书仓库: 书仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 还书命令) {
        this.还书命令处理器.处理(事件队列, 命令)

        事件队列.获取事件列表().forEach {
            if (it is 书已被归还) {
                this.添加可预定书命令处理器.处理(事件队列, 添加可预定书命令(
                    二维码 = it.二维码,
                    isbn = 书仓库.按照二维码查询或报错(it.二维码).isbn
                ))
            }
        }
    }
}