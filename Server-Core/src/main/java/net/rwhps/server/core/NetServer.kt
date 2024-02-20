/*
 * Copyright 2020-2024 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */
package net.rwhps.server.core

import net.rwhps.server.core.thread.CallTimeTask
import net.rwhps.server.core.thread.Threads.newTimedTask
import net.rwhps.server.data.global.Data
import net.rwhps.server.data.global.NetStaticData
import net.rwhps.server.net.NetService
import net.rwhps.server.net.manage.HttpRequestManage.doPostRw
import net.rwhps.server.util.StringFilteringUtil.cutting
import net.rwhps.server.util.Time.utcMillis
import net.rwhps.server.util.algorithms.digest.DigestUtils.md5Hex
import net.rwhps.server.util.algorithms.digest.DigestUtils.sha256
import net.rwhps.server.util.log.Log.clog
import net.rwhps.server.util.math.RandomUtils.getRandomString
import java.math.BigInteger
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Dr (dr@der.kim)
 */
object NetServer {
    var userId: String? = null

    fun closeServer() {
        NetStaticData.netService.eachAll { obj: NetService -> obj.stop() }
    }

    fun reLoadServer() {
    }
}
