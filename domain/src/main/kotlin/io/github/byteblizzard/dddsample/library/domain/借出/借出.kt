package io.github.byteblizzard.dddsample.library.domain.借出

import java.time.LocalDateTime

class 借出ID(val value: String)

interface 借出 {

}

class 借出实现(
    val 借出ID: 借出ID,
    val 二维码: String,
    val 上报过遗失: Boolean,
    val 是否待还书: Boolean,
    val 接触时间: LocalDateTime
): 借出 {

}