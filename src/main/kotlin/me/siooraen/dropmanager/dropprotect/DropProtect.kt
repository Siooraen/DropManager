package me.siooraen.dropmanager.dropprotect

import ink.ptms.um.event.MobDeathEvent
import me.siooraen.dropmanager.utils.getLong
import me.siooraen.dropmanager.utils.getString
import me.siooraen.dropmanager.utils.set
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent

/**
 * @author Siooraen
 * @since 2023/1/12 18:18
 */
object DropProtect {

    private fun ItemStack.addItem(player: Player, type: DropType) {
        val owner = player.name
        val time = System.currentTimeMillis()
        this.set("dm.owner", owner)
        this.set("dm.time", time)
        this.set("dm.type", type.toString())
    }

    private fun ItemStack.checkItem(player: Player): Boolean {
        val owner = this.getString("dm.owner")
        val time = this.getLong("dm.time")
        val type = this.getString("dm.type")
        val protectTime = DropType.valueOf(type).time
        if (owner == player.name && time + protectTime >= System.currentTimeMillis()) {
            return true
        }
        if (time + protectTime < System.currentTimeMillis()) {
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

    @SubscribeEvent
    fun e(e: EntityPickupItemEvent) {
        if (e.entity is Player) {
            val player = e.entity as Player
            val item = e.item.itemStack
            if (item.checkItem(player)) {
                item.set("dm", null)
            } else {
                e.isCancelled = true
            }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        e.itemDrop.itemStack.addItem(e.player, DropType.DROP)
    }
}