/*
 * Copyright 2020-2024 RW-HPS Team and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/RW-HPS/RW-HPS/blob/master/LICENSE
 */

package net.rwhps.server.data.bean

import net.rwhps.server.util.file.plugin.PluginData
import net.rwhps.server.util.file.plugin.value

/**
 *
 *
 * @date 2024/2/15 15:36
 * @author Dr (dr@der.kim)
 */
class Test(
    data: PluginData
) {
    var a by data.value("")
    var dddd by data.value(77)
    var l by data.value(77L)
}