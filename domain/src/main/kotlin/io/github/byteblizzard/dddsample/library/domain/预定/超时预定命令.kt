package io.github.byteblizzard.dddsample.library.domain.预定

import org.springframework.stereotype.Component

class 超时预定命令(
    val 预定id: String
)

@Component
class 超时预定命令处理器 {
    fun 处理(命令: 超时预定命令) {

    }
}