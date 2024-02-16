/*
 * Copyright 2020-2024 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */

package net.rwhps.server.plugin.beta

import net.rwhps.server.game.event.EventGlobalManage
import net.rwhps.server.data.global.Data
import net.rwhps.server.data.totalizer.TimeAndNumber
import net.rwhps.server.game.event.core.EventListenerHost
import net.rwhps.server.game.event.global.NetConnectNewEvent
import net.rwhps.server.io.GameOutputStream
import net.rwhps.server.plugin.Plugin
import net.rwhps.server.struct.map.ObjectMap
import net.rwhps.server.util.PacketType
import net.rwhps.server.util.annotations.core.EventListenerHandler
import net.rwhps.server.util.log.Log

/**
 * @date 2023/7/21 10:45
 * @author Dr (dr@der.kim)
 */
class RelayCNLimit: Plugin() {
    override fun registerGlobalEvents(eventManage: EventGlobalManage) {
        eventManage.registerListener(FuckEvent())
    }

    private class FuckEvent: EventListenerHost {
        private val data = ObjectMap<String, TimeAndNumberX>(true)

        @EventListenerHandler
        fun limitConnect(netConnectNewEvent: NetConnectNewEvent) {
            // 1 Min 只能 10 次, 多了即断开但不 BAN (6s/1)
            val limit = data[netConnectNewEvent.connectionAgreement.ip, { TimeAndNumberX(60, 10) }]
            // 12 Hour 内连接次数过 300 次, 即 BAN (144s/1)

            if (limit.countLimit.checkStatus()) {
                limit.countLimit.count++
            } else {
                val o = GameOutputStream()
                o.writeString("[12H/300] 连接过于多, 被BAN了")
                netConnectNewEvent.connectionAgreement.send(o.createPacket(PacketType.KICK))

                Data.core.admin.bannedIP24.add(netConnectNewEvent.connectionAgreement.ipLong24)
                Log.clog("Auto Ban : ${netConnectNewEvent.connectionAgreement.ipLong24}")
                netConnectNewEvent.connectionAgreement.close(null)
            }

            if (!limit.checkStatus()) {
                val o = GameOutputStream()
                o.writeString("[60s/10] 连接过于频繁, 请休息一会")
                netConnectNewEvent.connectionAgreement.send(o.createPacket(PacketType.KICK))
                netConnectNewEvent.connectionAgreement.close(null)
            } else {
                limit.count++
            }
        }
    }

    private class TimeAndNumberX(timeOut: Int, conutMax: Int): TimeAndNumber(timeOut, conutMax) {
        var countLimit = TimeAndNumber(43200, 300)
    }
}