package io.github.byteblizzard.dddsample.library.domain.用户

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class 用户聚合测试 {
    @Test
    fun `逾期达到3次会暂停账号`() {
        // 若
        val 测试目标 = 用户聚合实现(
            用户ID = 用户ID("one"),
            暂停 = false,
            逾期次数 = 2
        )
        val 事件队列 = mockk<临时事件队列>()
        every { 事件队列.入队(any(用户还书已逾期::class)) } just runs
        every { 事件队列.入队(any(用户账号已暂停::class)) } just runs

        // 当
        测试目标.增加逾期次数(事件队列)

        // 则
        assertEquals(3, 测试目标.逾期次数)
        assertTrue(测试目标.暂停)

        verifySequence {
            事件队列.入队(match {
                val 事件 = it as? 用户还书已逾期 ?: return@match false
                事件.用户ID.value == "one"
            })
            事件队列.入队(match {
                val 事件 = it as? 用户账号已暂停 ?: return@match false
                事件.用户ID.value == "one"
            })
        }

        confirmVerified(事件队列)
    }

    @Test
    fun `启用账号`() {
        // 若
        val 测试目标 = 用户聚合实现(
            用户ID = 用户ID("one"),
            暂停 = true,
            逾期次数 = 3
        )
        val 事件队列 = mockk<临时事件队列>()
        every { 事件队列.入队(any(用户账号已启用::class)) } just runs

        // 当
        测试目标.启用(事件队列)

        // 则
        assertEquals(0, 测试目标.逾期次数)
        assertFalse(测试目标.暂停)

        verifySequence {
            事件队列.入队(match {
                val 事件 = it as? 用户账号已启用 ?: return@match false
                事件.用户ID.value == "one"
            })
        }

        confirmVerified(事件队列)
    }
}