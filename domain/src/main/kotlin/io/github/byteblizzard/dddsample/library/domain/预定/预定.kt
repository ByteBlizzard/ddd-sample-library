package io.github.byteblizzard.dddsample.library.domain.预定

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import io.github.byteblizzard.dddsample.library.domain.领域事件
import java.time.LocalDateTime

interface 预定 {
    fun 尝试超时(事件队列: 临时事件队列)
    fun 取消(事件队列: 临时事件队列, 命令: 取消预订命令)
}

class 预定ID(val value: String)

class 预定实现(
    val 预定id: 预定ID,
    val 预定时间: LocalDateTime,
    val 预定人: 用户ID,
    val 二维码: String,
    var 有效: Boolean
): 预定 {
    override fun 尝试超时(事件队列: 临时事件队列) {
        if (!有效) {
            return
        }

        if (this.预定时间.plusHours(24).isBefore(LocalDateTime.now())) {
            this.有效 = false

            事件队列.入队(预定已超时(
                预定id = this.预定id,
                二维码 = this.二维码,
                预定人 = this.预定人
            ))
        }
    }

    override fun 取消(事件队列: 临时事件队列, 命令: 取消预订命令) {
        if (!有效) {
            return
        }

        this.有效 = false
        事件队列.入队(预定已取消(
            预定id = this.预定id,
            二维码 = this.二维码,
            预定人 = this.预定人
        ))

    }

}

interface 预定仓库 {
    fun 按照id查找或报错(预定id: 预定ID): 预定
    fun 按照二维码查找或报错(二维码: String): 预定
    fun 保存(预定: 预定)
}

class 预定已超时(
    val 预定id: 预定ID,
    val 二维码: String,
    val 预定人: 用户ID
): 领域事件

class 预定已取消(
    val 预定id: 预定ID,
    val 二维码: String,
    val 预定人: 用户ID
): 领域事件
