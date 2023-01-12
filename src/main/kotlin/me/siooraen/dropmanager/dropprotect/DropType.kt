package me.siooraen.dropmanager.dropprotect

import me.siooraen.dropmanager.DropManager.conf

/**
 * @author Siooraen
 * @since 2023/1/11 22:23
 */
enum class DropType(val time: Long) {

    MOB(conf.getLong("protect-time.mob") * 1000), DROP(conf.getLong("protect-time.drop") * 1000)
}