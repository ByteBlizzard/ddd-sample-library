package io.github.byteblizzard.dddsample.library.domain.借出

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.书.书仓库
import org.springframework.stereotype.Component

class 还书命令(
    val 二维码: String
)

@Component
class 还书命令处理器 (
    private val 书仓库: 书仓库,
    private val 借出仓库: 借出仓库
) {
   fun 处理(事件队列: 临时事件队列, 命令: 还书命令) {
       书仓库.按照二维码查询或报错(命令.二维码)
       val 借出 = 借出仓库.按照二维码查询最近借出(命令.二维码)
       借出.归还(事件队列)
       借出仓库.更新(借出)
   }
}