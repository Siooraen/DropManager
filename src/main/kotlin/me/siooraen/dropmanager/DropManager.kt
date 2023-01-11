package me.siooraen.dropmanager

import me.siooraen.dropmanager.utils.getLong
import me.siooraen.dropmanager.utils.getString
import me.siooraen.dropmanager.utils.set
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.util.isAir
import java.lang.System.currentTimeMillis

/**
 * @author Siooraen
 * @since 2023/1/8 13:27
 */
object DropManager : Plugin() {

    override fun onEnable() {
        console().sendMessage("DropManager成功运行!")
    }

    @Config
    lateinit var conf: Configuration
        private set

    private val protectTime by lazy {
        conf.getLong("protect-time-in-second") * 1000
    }

    fun ItemStack.addItem(player: Player) {
        if (!this.isAir) {
            val owner = player.name
            val time = currentTimeMillis()
            this.set("dm.owner", owner)
            this.set("dm.time", time)
            console().sendMessage("$owner $time")
        }
    }

    fun ItemStack.removeItem() {
        if (!this.isAir) {
            this.set("dm.owner", null)
            this.set("dm.time", null)
        }
    }

    fun ItemStack.checkItem(player: Player): Boolean {
        if (!this.isAir) {
            val owner = this.getString("dm.owner")
            val time = this.getLong("dm.time")
            if (owner == player.name && time + protectTime >= currentTimeMillis()) {
                return true
            }
            if (time + protectTime < currentTimeMillis()) {
                return true
            }
        }
        return false
    }
}