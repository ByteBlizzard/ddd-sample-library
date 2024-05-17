package io.github.byteblizzard.dddsample.library.domain.借出

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import org.springframework.stereotype.Component

class 还书逾期命令(
    val 借出ID: 借出ID
)

@Component
class 还书逾期命令处理器(
    val 借出仓库: 借出仓库
) {
    fun 处理(事件队列: 临时事件队列, 命令: 还书逾期命令) {
        val 借出 = 借出仓库.按照ID查找或保存(命令.借出ID)
        借出.尝试逾期(事件队列)
        借出仓库.更新(借出)
    }

}