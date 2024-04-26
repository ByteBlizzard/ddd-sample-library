package io.github.byteblizzard.dddsample.library.domain.用户占用书

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import io.github.byteblizzard.dddsample.library.domain.领域事件

interface 用户占用书 {
    fun 调整(事件队列: 临时事件队列, 调整值: Int)
    fun 占用书(): Int
}

class 用户占用书实现(
    val 用户id: 用户ID,
    var 占用数量: Int
): 用户占用书 {
    override fun 调整(事件队列: 临时事件队列, 调整值: Int) {
        val 旧占用量 = this.占用数量
        this.占用数量 += 调整值
        if (this.占用数量 < 0) {
            this.占用数量 = 0
        }
        事件队列.入队(用户占用书已变化(this.用户id, this.占用数量, 旧占用量))
    }

    override fun 占用书(): Int {
        return this.占用数量
    }

}

interface 用户占用书仓库 {
    fun 按照ID查找或报错(用户id: 用户ID): 用户占用书
    fun 保存(用户占用书: 用户占用书)
}

class 用户占用书已变化(
    val 用户id: 用户ID,
    val 新占用量: Int,
    val 旧占用量: Int
): 领域事件