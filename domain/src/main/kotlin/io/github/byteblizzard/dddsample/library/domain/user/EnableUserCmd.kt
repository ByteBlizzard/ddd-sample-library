package io.github.byteblizzard.dddsample.library.domain.user

import org.springframework.stereotype.Service

class EnableUserCmd(val userId: UserId) {
}

@Service
class EnableUserCmdHandler(
    private val userRepository: UserRepository
) {
    fun handle(cmd: EnableUserCmd) {
        val user = userRepository.findByIdOrError(cmd.userId)
        user.enable()
        userRepository.save(user)
    }
}