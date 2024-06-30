package io.github.byteblizzard.dddsample.library.domain.用户

import org.axonframework.modelling.command.TargetAggregateIdentifier


class 启用会员账号命令(
    @TargetAggregateIdentifier
    val 用户ID: 用户ID
)
