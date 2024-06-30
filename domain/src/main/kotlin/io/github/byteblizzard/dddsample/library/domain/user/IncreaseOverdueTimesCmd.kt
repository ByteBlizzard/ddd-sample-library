package io.github.byteblizzard.dddsample.library.domain.user

import jakarta.persistence.Column
import org.springframework.stereotype.Service

class UserId(
    @Column(name = "id")
    val value: String
)
class IncreaseOverdueTimesCmd(
    val userId: UserId
)


@Service
class IncreaseOverdueTimesCmdHandler(
    private val userRepository: UserRepository
) {
    fun handle(cmd: IncreaseOverdueTimesCmd) {
        val user = userRepository.findByIdOrError(cmd.userId)
        user.increaseOverdueTimes()
        userRepository.save(user)
    }

}