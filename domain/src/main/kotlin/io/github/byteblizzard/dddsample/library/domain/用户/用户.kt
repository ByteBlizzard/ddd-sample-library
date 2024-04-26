package io.github.byteblizzard.dddsample.library.domain.用户

import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.领域事件

interface 用户聚合 {
    fun 增加逾期次数(事件队列: 临时事件队列)
    fun 启用(事件队列: 临时事件队列)
    fun 已禁用(): Boolean
}

class 用户聚合实现(
    private val 用户ID: 用户ID,
    var 逾期次数: Int,
    var 暂停: Boolean
): 用户聚合 {

    override fun 增加逾期次数(事件队列: 临时事件队列) {
        this.逾期次数++
        事件队列.入队(用户还书已逾期(this.用户ID))
        if (this.逾期次数 == 3 && !this.暂停) {
            this.暂停 = true
            事件队列.入队(用户账号已暂停(this.用户ID))
        }
    }

    override fun 启用(事件队列: 临时事件队列) {
        if (!this.暂停) {
            return
        }

        this.暂停 = false
        this.逾期次数 = 0
        事件队列.入队(用户账号已启用(this.用户ID))
    }

    override fun 已禁用(): Boolean {
        return this.暂停
    }

}

interface 用户仓库 {
    fun 按照ID查找或报错(用户ID: 用户ID): 用户聚合
    fun 保存(用户聚合: 用户聚合)
}

class 用户账号已暂停(val 用户ID: 用户ID): 领域事件

class 用户还书已逾期(val 用户ID: 用户ID): 领域事件
class 用户账号已启用(val 用户ID: 用户ID): 领域事件
class 用户账号已创建(val 用户ID: 用户ID, val 名字: String): 领域事件

