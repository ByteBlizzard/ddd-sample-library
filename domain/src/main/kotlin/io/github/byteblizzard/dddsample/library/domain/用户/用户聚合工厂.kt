package io.github.byteblizzard.dddsample.library.domain.用户

import org.springframework.stereotype.Component

@Component
class 用户聚合工厂 {
    fun 初始化用户聚合(事件: 用户账号已创建): 用户聚合 {
        val 聚合 = 用户聚合实现()

        聚合.用户ID = 事件.用户ID
        聚合.暂停 = false
        聚合.逾期次数 = 0
        return 聚合
    }
}