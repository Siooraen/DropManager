package me.siooraen.dropmanager

import org.bukkit.Bukkit
import org.bukkit.Bukkit.getPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import java.lang.System.currentTimeMillis

/**
 * @author Siooraen
 * @since 2023/1/8 13:27
 */
object DropManager : Plugin() {

    override fun onEnable() {
        console().sendMessage("DropManager成功运行!")
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().forEach { PlayerLeaveServerEvent(it).call() }
    }

    @Config
    lateinit var conf: Configuration
        private set

    fun ItemStack.addItem(player: Player) {
        if (this.hasItemMeta() && this.itemMeta!!.hasLore()) {
            val meta = this.itemMeta!!
            val lores = meta.lore!!
            lores.add("DropManager ${player.name} ${currentTimeMillis()}")
            this.itemMeta = meta
        } else {
            val meta = this.itemMeta!!
            val lores = mutableListOf<String>()
            lores.add("DropManager ${player.name} ${currentTimeMillis()}")
            this.itemMeta = meta
        }
    }

    fun ItemStack.removeItem() {
        if (this.hasItemMeta() && this.itemMeta!!.hasLore()) {
            val meta = this.itemMeta!!
            val lores = meta.lore!!
            var i = 0
            lores.forEach {
                if (it.contains("DropManager")) {
                    lores.removeAt(i)
                    meta.lore = lores
                    this.itemMeta = meta
                    return
                }
                i++
            }
        }
    }

    fun ItemStack.checkItem(player: Player): Boolean {
        if (this.hasItemMeta() && this.itemMeta!!.hasLore()) {
            val meta = this.itemMeta!!
            val lores = meta.lore!!
            val protectTime = conf.getLong("protect-time-in-second") * 1000
            var owner: Player? = null
            var time: Long? = null
            lores.forEach {
                if (it.contains("DropManager")) {
                    val parts = it.split(" ")
                    owner = getPlayer(parts[1])
                    time = parts[2].toLong()
                }
            }
            if (owner!!.name == player.name
                && time!! + protectTime >= currentTimeMillis()
            ) return true
            if (time!! + protectTime < currentTimeMillis()) return true
        }
        return false
    }
}