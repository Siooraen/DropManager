package me.siooraen.dropmanager

import ink.ptms.um.event.MobDeathEvent
import me.siooraen.dropmanager.DropManager.addItem
import me.siooraen.dropmanager.DropManager.checkItem
import me.siooraen.dropmanager.DropManager.removeItem
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * @author Siooraen
 * @since 2023/1/8 19:06
 */
object DropManagerEvent {

    @SubscribeEvent
    fun e(e: MobDeathEvent) {
        val drops = e.drop
        if (e.killer is Player) {
            val killer = (e.killer as Player)
            drops.forEach {
                it.addItem(killer)
            }
        }
    }

    @SubscribeEvent
    fun e(e: EntityPickupItemEvent) {
        if (e.entity is Player) {
            val player = e.entity as Player
            val item = e.item.itemStack
            if (item.checkItem(player)) {
                item.removeItem()
            } else {
                e.isCancelled = true
            }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        e.itemDrop.itemStack.addItem(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        PlayerLeaveServerEvent(e.player).call()
    }

    @SubscribeEvent
    fun e(e: PlayerKickEvent) {
        PlayerLeaveServerEvent(e.player).call()
    }
}