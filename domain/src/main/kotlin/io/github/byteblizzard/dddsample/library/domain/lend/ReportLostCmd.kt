package io.github.byteblizzard.dddsample.library.domain.lend

import org.springframework.stereotype.Component

class ReportLostCmd(
    val lendId: LendId
)

@Component
class ReportLostCmdHandler(
    private val lendRepository: LendRepository
) {
    fun handle(cmd: ReportLostCmd) {
        val lend = lendRepository.findByIdOrError(cmd.lendId)
        lend.reportLost()
        lendRepository.update(lend)
    }
}