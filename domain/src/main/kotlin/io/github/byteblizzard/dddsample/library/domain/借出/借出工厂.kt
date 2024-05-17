package io.github.byteblizzard.dddsample.library.domain.借出

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.书.书仓库
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import io.github.byteblizzard.dddsample.library.domain.用户.用户仓库
import io.github.byteblizzard.dddsample.library.domain.用户占用书.用户占用书仓库
import io.github.byteblizzard.dddsample.library.domain.预定.预定仓库
import io.github.byteblizzard.dddsample.library.domain.领域事件
import io.github.byteblizzard.dddsample.library.domain.领域异常
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class 借出工厂 (
    private val 用户仓库: 用户仓库,
    private val 书仓库: 书仓库,
    private val 预定仓库: 预定仓库,
    private val 用户占用书仓库: 用户占用书仓库,
    private val 借出ID生成器: 借出ID生成器
) {
    fun 创建借出(事件队列: 临时事件队列, 命令: 借出书命令): 借出实现 {
        val 用户 = 用户仓库.按照ID查找或报错(命令.借书人ID)
        if (用户.已禁用()) {
            throw 领域异常("用户账户已暂停，不能借书")
        }

        val 书 = 书仓库.按照二维码查询或报错(命令.二维码)
        if (!书.上架中()) {
            throw 领域异常("书已下架，不能借出")
        }

        val 预定 = this.预定仓库.按照二维码查找有效预定(命令.二维码)
        if (预定 != null && 预定.预订人ID() != 命令.借书人ID) {
            throw 领域异常("书已被别人预定，不能借出")
        }

        val 用户占用书 = this.用户占用书仓库.按照ID查找或报错(命令.借书人ID)
        if (用户占用书.占用书() >= 3) {
            throw 领域异常("用户已经占用3本书，不能借出更多书")
        }

        val 借出 = 借出实现(
            借出ID = 借出ID生成器.生成借出ID(),
            二维码 = 命令.二维码,
            上报过遗失 = false,
            待还书 = true,
            借出时间 = LocalDateTime.now(),
            借出人 = 命令.借书人ID
        )

        事件队列.入队(书已被借出事件(
            借出ID = 借出.借出ID,
            二维码 = 命令.二维码,
            借出人 = 命令.借书人ID,
            借出时间 = 借出.借出时间,
            应还书时间 = 借出.借出时间.plusDays(30)
        ))

        return 借出
    }
}

interface 借出ID生成器 {
    fun 生成借出ID(): 借出ID
}

class 书已被借出事件(
    val 借出ID: 借出ID,
    val 二维码: String,
    val 借出人: 用户ID,
    val 借出时间: LocalDateTime,
    val 应还书时间: LocalDateTime
): 领域事件