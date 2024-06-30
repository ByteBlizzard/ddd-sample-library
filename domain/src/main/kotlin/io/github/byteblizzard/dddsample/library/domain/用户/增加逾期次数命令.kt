package io.github.byteblizzard.dddsample.library.domain.用户

import org.axonframework.modelling.command.TargetAggregateIdentifier

class 用户ID(val value: String)
class 增加逾期次数命令(
    @TargetAggregateIdentifier
    val 用户ID: 用户ID
)
