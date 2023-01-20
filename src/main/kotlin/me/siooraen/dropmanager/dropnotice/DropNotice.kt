package me.siooraen.dropmanager.dropnotice

import ink.ptms.um.event.MobDeathEvent
import me.siooraen.dropmanager.DropManager.conf
import me.siooraen.dropmanager.utils.getString
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.chat.uncolored
import taboolib.platform.util.buildItem

/**
 * @author Siooraen
 * @since 2023/1/13 18:34
 */
object DropNotice {

    private val noticeList = mutableMapOf<String, String>()

    @Awake(LifeCycle.LOAD)
    fun read() {
        noticeList.clear()
        conf.getStringList("notice-list").forEach {
            val parts = it.split("@")
            noticeList[parts[0]] = parts[1]
        }
    }

    @SubscribeEvent
    fun e(e: MobDeathEvent) {
        val drops = e.drop
        drops.forEach {
            val nbt = it.getString(conf.getString("notice-nbt-node")!!.uncolored())
            if (noticeList[nbt] != null
                && e.killer is Player) {
                val killer = e.killer as Player
                killer.sendMessage(noticeList[nbt]!!.replace("{item}", it.itemMeta!!.displayName).colored())
            }
        }
    }
}