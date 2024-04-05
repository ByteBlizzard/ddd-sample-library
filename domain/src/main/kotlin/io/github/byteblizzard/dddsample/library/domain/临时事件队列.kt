package io.github.byteblizzard.dddsample.library.domain

interface 临时事件队列 {
    fun 入队(event: 领域事件)

    fun 获取事件列表(): List<领域事件>
}