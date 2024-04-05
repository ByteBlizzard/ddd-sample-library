package io.github.byteblizzard.dddsample.library.domain.书

import io.github.byteblizzard.dddsample.library.domain.领域异常
import io.github.byteblizzard.dddsample.library.domain.临时事件队列
import io.github.byteblizzard.dddsample.library.domain.领域事件
import org.springframework.stereotype.Component

@Component
class 书工厂(
    private val 书仓库: 书仓库
) {

    fun 新建(事件队列: 临时事件队列, 命令: 入库新书命令): 书聚合 {
        val 同名书 = this.书仓库.按照二维码查询(命令.二维码)
        if (同名书 != null) {
            throw 书名重复异常()
        }

        val 新书 = 书聚合实现(
            二维码 = 命令.二维码,
            isbn = 命令.isbn,
            上架 = false
        )
        事件队列.入队(新书已入库(
            二维码 = 命令.二维码,
            isbn = 命令.isbn,
            书名 = 命令.书名,
            图片 = 命令.图片,
            简介 = 命令.简介
        ))
        return 新书
    }
}

class 书名重复异常 : 领域异常("书名重复了")

class 新书已入库(
    val 二维码: String,
    val isbn: String,
    val 书名: String,
    val 图片: String,
    val 简介: String
): 领域事件