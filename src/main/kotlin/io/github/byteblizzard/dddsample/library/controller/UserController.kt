package io.github.byteblizzard.dddsample.library.controller

import io.github.byteblizzard.dddsample.library.domain.用户.创建会员账号命令
import io.github.byteblizzard.dddsample.library.domain.用户.用户ID
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val commandGateway: CommandGateway
) {
    @PostMapping(path = ["/user"])
    fun newUser(form: UserForm) {
        commandGateway.sendAndWait<Void>(创建会员账号命令(
            用户ID = 用户ID(form.id),
            名字 = form.name
        ))
    }

    data class UserForm(val id: String, val name: String)
}