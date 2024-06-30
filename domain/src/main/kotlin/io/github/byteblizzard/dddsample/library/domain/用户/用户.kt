package io.github.byteblizzard.dddsample.library.domain.用户

import io.github.byteblizzard.dddsample.library.domain.applyEvent
import io.github.byteblizzard.dddsample.library.domain.领域事件
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate

interface 用户聚合 {
    fun 增加逾期次数(命令: 增加逾期次数命令)
    fun 启用(命令: 启用会员账号命令)
    fun 暂停(命令: 暂停会员账号命令)
    fun 已禁用(): Boolean
}

@Aggregate(repository = "用户聚合仓储")
class 用户聚合实现(): 用户聚合 {
    @AggregateIdentifier
    lateinit var 用户ID: 用户ID

    var 逾期次数: Int = 0
    var 暂停: Boolean = false

    constructor(命令: 创建会员账号命令): this() {
        this.用户ID = 命令.用户ID
        applyEvent(用户账号已创建(this.用户ID, 命令.名字))
    }

    @CommandHandler
    override fun 增加逾期次数(命令: 增加逾期次数命令) {
        this.逾期次数++
        applyEvent(用户还书已逾期(this.用户ID))

        if (this.逾期次数 == 3 && !this.暂停) {
            this.暂停 = true
            applyEvent(用户账号已暂停(this.用户ID))
        }
    }

    @CommandHandler
    override fun 启用(命令: 启用会员账号命令) {
        if (!this.暂停) {
            return
        }

        this.暂停 = false
        this.逾期次数 = 0
        applyEvent(用户账号已启用(this.用户ID))
    }

    @CommandHandler
    override fun 暂停(命令: 暂停会员账号命令) {
        if (this.暂停) {
            return
        }

        this.暂停 = true
        applyEvent(用户账号已暂停(this.用户ID))
    }

    override fun 已禁用(): Boolean {
        return this.暂停
    }

    @EventSourcingHandler
    fun on(事件: 用户账号已创建) {
        this.用户ID = 事件.用户ID
        this.暂停 = false
        this.逾期次数 = 0
    }

    @EventSourcingHandler
    fun on(事件: 用户账号已暂停) {
        this.暂停 = true
    }

    @EventSourcingHandler
    fun on(事件: 用户账号已启用) {
        this.暂停 = false
        this.逾期次数 = 0
    }

    @EventSourcingHandler
    fun on(事件: 用户还书已逾期) {
        this.逾期次数++
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

