package io.github.byteblizzard.dddsample.library.domain

interface 领域事件监听器{
    fun 当已发生(事件队列: 临时事件队列, 事件: 领域事件)
}