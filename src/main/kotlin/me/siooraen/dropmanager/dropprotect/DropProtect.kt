package me.siooraen.dropmanager.dropprotect

import ink.ptms.um.event.MobDeathEvent
import me.siooraen.dropmanager.utils.getLong
import me.siooraen.dropmanager.utils.getString
import me.siooraen.dropmanager.utils.set
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import java.lang.System.currentTimeMillis

/**
 * @author Siooraen
 * @since 2023/1/12 18:18
 */
object DropProtect {

    private fun ItemStack.addItem(player: Player, type: DropType) {
        this.set("dm.owner", player.name)
        if (type == DropType.MOB) {
            this.set("dm.time", currentTimeMillis())
        }
    }

    private fun ItemStack.checkItem(player: Player): Boolean {
        val owner = this.getString("dm.owner")
        val type = this.getString("dm.type")
        val protectTime = DropType.getProtectTime(DropType.get(type)) ?: return false
        val time = this.getLong("dm.time")
        if (owner == player.name && time + protectTime >= currentTimeMillis()) {
            return true
        }
        if (time + protectTime < currentTimeMillis()) {
            return true
        }
        return false
    }

    @SubscribeEvent
    fun e(e: MobDeathEvent) {
        val drops = e.drop
        if (e.killer is Player) {
            val killer = e.killer as Player
            drops.forEach {
                it.addItem(killer, DropType.MOB)
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun e(e: EntityPickupItemEvent) {
        if (e.entity is Player) {
            val item = e.item.itemStack
            val player = e.entity as Player
            if (item.checkItem(player)) {
                item.set("dm", null)
            } else {
                e.isCancelled = true
            }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        val item = e.itemDrop.itemStack
        if (item.getString("ITEM_DROP") == "1") {
            return
        }
        item.addItem(e.player, DropType.DROP)
    }
}