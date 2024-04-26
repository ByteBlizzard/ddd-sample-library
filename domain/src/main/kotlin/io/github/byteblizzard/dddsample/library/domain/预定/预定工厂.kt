package io.github.byteblizzard.dddsample.library.domain.预定

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.可预定书.可预定书仓库
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import io.github.byteblizzard.dddsample.library.domain.用户.用户仓库
import io.github.byteblizzard.dddsample.library.domain.用户占用书.用户占用书仓库
import io.github.byteblizzard.dddsample.library.domain.领域事件
import io.github.byteblizzard.dddsample.library.domain.领域异常
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class 预定工厂(
    private val 预定ID生成器: 预定ID生成器,
    private val 用户仓库: 用户仓库,
    private val 可预定书仓库: 可预定书仓库,
    private val 用户占用书仓库: 用户占用书仓库
) {
    fun 创建(事件队列: 临时事件队列, 预定命令: 预定命令): 预定 {
        val 预定人 = this.用户仓库.按照ID查找或报错(预定命令.预定人)
        if (预定人.已禁用()) {
            throw 领域异常("预定人账户已禁用，不可预定书")
        }

        val 可预订书 = this.可预定书仓库.按照id查找或报错(预定命令.isbn)
        if (可预订书.不够了()) {
            throw 领域异常("没有可预订的书了")
        }

        if (this.用户占用书仓库.按照ID查找或报错(预定命令.预定人).占用书() >= 3) {
            throw 领域异常("每个用户最多占用3本书，你已达到限制，不能再预定啦")
        }

        val 被选中的预定的书的二维码 = 可预订书.随机选取一本书()

        val 预定 = 预定实现(
            预定id = 预定ID生成器.生成预定ID(),
            预定时间 = LocalDateTime.now(),
            预定人 = 预定命令.预定人,
            二维码 = 被选中的预定的书的二维码,
            有效 = true
        )

        事件队列.入队(书已被预定(
            预定ID = 预定.预定id,
            二维码 = 预定.二维码,
            预定人 = 预定.预定人,
            预定时间 = 预定.预定时间
        ))

        return 预定
    }
}

interface 预定ID生成器 {
    fun 生成预定ID(): 预定ID
}

class 书已被预定(
    val 预定ID: 预定ID,
    val 二维码: String,
    val 预定人: 用户ID,
    val 预定时间: LocalDateTime
) : 领域事件
