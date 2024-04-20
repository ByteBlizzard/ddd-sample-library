package io.github.byteblizzard.dddsample.library.domain.用户占用书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import org.springframework.stereotype.Component

class 调整用户占书量命令(
    val 用户ID: 用户ID,
    val 调整值: Int
)

@Component
class 调整用户占书命令处理器(
    val 用户占用书仓库: 用户占用书仓库
) {

    fun 处理(事件队列: 临时事件队列, 命令: 调整用户占书量命令) {
        val 用户占用书 = this.用户占用书仓库.按照ID查找或报错(命令.用户ID)
        用户占用书.调整(事件队列, 命令.调整值)
        this.用户占用书仓库.保存(用户占用书)
    }
}