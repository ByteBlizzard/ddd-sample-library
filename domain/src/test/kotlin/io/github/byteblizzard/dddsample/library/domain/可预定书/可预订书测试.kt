package io.github.byteblizzard.dddsample.library.domain.可预定书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.领域事件
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class 可预订书测试 {
    @Test
    fun 用例1() {
        // 若
        val 事件队列: 临时事件队列 = mockk()
        every { 事件队列.入队(any(领域事件::class)) } just runs
        val 测试目标 = 可预定书实现(isbn = "isbn", 二维码集合 = mutableSetOf())

        // 则
        assertFalse(测试目标.是否包含书("书1"))

        // 当
        测试目标.添加(事件队列, "书1")
        assertTrue(测试目标.是否包含书("书1"))

        测试目标.添加(事件队列, "书1")
        assertTrue(测试目标.是否包含书("书1"))

        测试目标.移除(事件队列, "书1")
        assertFalse(测试目标.是否包含书("书1"))

        测试目标.移除(事件队列, "书1")

        verify(exactly = 1) { 事件队列.入队(match { it is 书已可预定 && it.二维码 == "书1" && it.isbn == "isbn" }) }
        verify(exactly = 1) { 事件队列.入队(match { it is 书已不可预定 && it.二维码 == "书1" && it.isbn == "isbn" }) }
    }
}