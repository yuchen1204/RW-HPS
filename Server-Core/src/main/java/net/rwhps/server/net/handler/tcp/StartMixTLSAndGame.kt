/*
 * Copyright 2020-2024 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */

package net.rwhps.server.net.handler.tcp

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.*
import net.rwhps.server.util.PacketType


/**
 * 混合转发器, 检查数据是不是游戏数据
 *
 * @author Dr (dr@der.kim)
 */

@ChannelHandler.Sharable
class StartMixTLSAndGame: StartHttp() {
    override fun initChannel(socketChannel: SocketChannel) {
        fun superInitChannel(socketChannel: SocketChannel) {
            super.initChannel(socketChannel)
        }
        socketChannel.pipeline().addLast(object: ChannelInboundHandlerAdapter() {
            @Throws(Exception::class)
            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                val firstData: ByteBuf = msg as ByteBuf

                val readerIndex = firstData.readerIndex()
                val readPacketLengthCache = firstData.readInt()
                val readPacketTypeCache = firstData.readInt()
                firstData.readerIndex(readerIndex)

                if (readPacketLengthCache > 0 &&
                    readPacketTypeCache > 0 &&
                    (PacketType.from(readPacketTypeCache).typeInt > 1000 ||
                     PacketType.from(readPacketTypeCache) == PacketType.PREREGISTER_INFO_RECEIVE
                    )
                ) {
                    rwinit(socketChannel.pipeline())
                } else {
                    firstData.retain()
                    superInitChannel(socketChannel)
                }
                ctx.pipeline().remove(this)
                super.channelRead(ctx, msg)
            }
        })
    }
}