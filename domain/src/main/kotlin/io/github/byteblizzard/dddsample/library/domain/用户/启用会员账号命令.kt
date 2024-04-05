package io.github.byteblizzard.dddsample.library.domain.用户

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Service

class 启用会员账号命令(val 用户ID: 用户ID) {
}

@Service
class 启用会员账号命令处理器(
    private val 用户仓库: 用户仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 启用会员账号命令) {
        val 用户聚合 = 用户仓库.按照ID查找或报错(命令.用户ID)
        用户聚合.启用(事件队列)
        用户仓库.保存(用户聚合)
    }
}