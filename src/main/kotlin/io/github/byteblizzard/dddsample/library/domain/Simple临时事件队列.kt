package io.github.byteblizzard.dddsample.library.domain

import java.util.LinkedList

class Simple临时事件队列: 临时事件队列 {
    private val queue: MutableList<领域事件> = LinkedList()

    override fun 入队(event: 领域事件) {
        queue.add(event)
    }

    override fun 获取事件列表(): List<领域事件> {
        return queue
    }
}