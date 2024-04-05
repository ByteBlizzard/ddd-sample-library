package io.github.byteblizzard.dddsample.library.domain

import java.util.LinkedList

class Test临时事件队列: 临时事件队列 {
    val list = LinkedList<领域事件>()
    override fun 入队(event: 领域事件) {
        list.add(event)
    }

    override fun 获取事件列表(): List<领域事件> {
        return list
    }
}