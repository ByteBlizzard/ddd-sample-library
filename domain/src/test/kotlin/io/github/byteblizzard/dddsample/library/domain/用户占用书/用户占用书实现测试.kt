package io.github.byteblizzard.dddsample.library.domain.用户占用书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import io.mockk.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class 用户占用书实现测试 {
    @Test
    fun 用例1() {
        // 若
        val 事件队列: 临时事件队列 = mockk()
        val 测试目标 = 用户占用书实现(用户ID("id"), 3)
        every { 事件队列.入队(any(用户占用书已变化::class)) } just runs

        // 当
        测试目标.调整(事件队列, -4)

        // 则
        assertEquals(0, 测试目标.占用数量)
        verify { 事件队列.入队(match { it is 用户占用书已变化 && it.新占用量 == 0 && it.旧占用量 == 3 && it.用户id.value == "id" }) }
    }
}