package io.github.byteblizzard.dddsample.library.domain.用户

import org.springframework.stereotype.Component

@Component
class 用户聚合工厂 {
    fun 初始化用户聚合(事件: 用户账号已创建): 用户聚合 {
        return 用户聚合实现(
            用户ID = 事件.用户ID,
            暂停 = false,
            逾期次数 = 0
        )
    }
}