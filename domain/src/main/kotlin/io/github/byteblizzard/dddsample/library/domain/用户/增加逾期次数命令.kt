package io.github.byteblizzard.dddsample.library.domain.用户

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Service

class 用户ID(val value: String)
class 增加逾期次数命令(
    val 用户ID: 用户ID
)


@Service
class 增加逾期次数命令处理器(
    private val 用户仓库: 用户仓库
) {
    fun 处理(事件队列:临时事件队列, 命令: 增加逾期次数命令) {
        val 用户聚合 = 用户仓库.按照ID查找或报错(命令.用户ID)
        用户聚合.增加逾期次数(事件队列)
        用户仓库.保存(用户聚合)
    }

}