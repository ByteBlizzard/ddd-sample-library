package io.github.byteblizzard.dddsample.library.domain.预定

import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import java.time.LocalDateTime

interface 预定 {
}

class 预定实现(
    val 预定id: String,
    val 预定时间: LocalDateTime,
    val 预定人: 用户ID,
    val 二维码: String,
    var 是否有效: Boolean
): 预定 {

}