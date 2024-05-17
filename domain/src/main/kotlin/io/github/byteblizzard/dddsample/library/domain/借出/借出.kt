package io.github.byteblizzard.dddsample.library.domain.借出

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import io.github.byteblizzard.dddsample.library.domain.领域事件
import io.github.byteblizzard.dddsample.library.domain.领域异常
import java.time.LocalDateTime

class 借出ID(val value: String)

interface 借出 {
    fun 归还(事件队列: 临时事件队列)
    fun 上报遗失(事件队列: 临时事件队列)
    fun 尝试逾期(事件队列: 临时事件队列)

}

class 借出实现(
    val 借出ID: 借出ID,
    val 二维码: String,
    var 上报过遗失: Boolean,
    var 待还书: Boolean,
    val 借出时间: LocalDateTime,
    val 借出人: 用户ID
): 借出 {
    override fun 归还(事件队列: 临时事件队列) {
        if (!待还书) {
            return
        }

        this.待还书 = false
        事件队列.入队(书已被归还(
            借出ID = this.借出ID,
            二维码 = this.二维码,
            借出人 = 借出人,
            归还前已遗失 = this.上报过遗失
        ))

        if (当前已逾期()) {
            事件队列.入队(还书已逾期(
                借出ID = this.借出ID,
                二维码 = this.二维码,
                借出人 = 借出人,
            ))
        }
    }

    private fun 当前已逾期(): Boolean =  this.借出时间.plusMonths(1).isBefore(LocalDateTime.now())

    override fun 上报遗失(事件队列: 临时事件队列) {
        if (this.上报过遗失) {
            return
        }
        if (!待还书) {
            throw 领域异常("书已被归还，不能上报遗失")
        }

        this.上报过遗失 = true
        事件队列.入队(书已被遗失(
            借出ID = this.借出ID,
            二维码 = this.二维码,
            借出人 = 借出人,
        ))
    }

    override fun 尝试逾期(事件队列: 临时事件队列) {
        if (!待还书) {
            return
        }

        if (当前已逾期()) {
            事件队列.入队(还书已逾期(
                借出ID = this.借出ID,
                二维码 = this.二维码,
                借出人 = 借出人,
            ))
        }
    }

}

interface 借出仓库 {
    fun 保存(借出: 借出)
    fun 更新(借出: 借出)
    fun 按照二维码查询最近借出(二维码: String): 借出
    fun 按照ID查找或保存(借出ID: 借出ID): 借出
}

class 书已被归还(
    val 借出ID: 借出ID,
    val 二维码: String,
    val 借出人: 用户ID,
    val 归还前已遗失: Boolean
): 领域事件

class 还书已逾期(
    val 借出ID: 借出ID,
    val 二维码: String,
    val 借出人: 用户ID
): 领域事件

class 书已被遗失(
    val 借出ID: 借出ID,
    val 二维码: String,
    val 借出人: 用户ID
): 领域事件
