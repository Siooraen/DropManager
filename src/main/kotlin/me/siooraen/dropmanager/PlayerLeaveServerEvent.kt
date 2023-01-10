package me.siooraen.dropmanager

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * @author Siooraen
 * @since 2022/9/4 8:57
 */
class PlayerLeaveServerEvent(
    val player: Player,
) : BukkitProxyEvent()