package io.github.byteblizzard.dddsample.library.domain

import io.github.byteblizzard.dddsample.library.domain.书.书仓库
import io.github.byteblizzard.dddsample.library.domain.可预定书.可预定书仓库
import org.springframework.context.ApplicationContext

object 领域对象注册表 {
    lateinit var spring: ApplicationContext

    fun 获取可预订书仓库() : 可预定书仓库 = spring.getBean(可预定书仓库::class.java)
    fun 获取书仓库() : 书仓库 = spring.getBean(书仓库::class.java)
}