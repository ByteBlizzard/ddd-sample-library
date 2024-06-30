package io.github.byteblizzard.dddsample.library.domain.user

import org.springframework.stereotype.Component

class CreateUserCmd(
    val userId: UserId,
    val name: String
)

@Component
class CreateUserCmdHandler(
    private val userRepository: UserRepository,
    private val userFactory: UserFactory
) {
    fun handle(cmd: CreateUserCmd) {
        val user = userFactory.create(cmd)
        this.userRepository.save(user)
    }
}