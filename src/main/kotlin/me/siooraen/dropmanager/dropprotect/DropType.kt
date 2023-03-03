package me.siooraen.dropmanager.dropprotect

import me.siooraen.dropmanager.DropManager.conf

/**
 * @author Siooraen
 * @since 2023/3/3 21:15
 */
enum class DropType {

    MOB, DROP;

    companion object {
        fun get(str: String) = if (str == "MOB") MOB else DROP

        fun getProtectTime(type: DropType) =
            if (type == MOB) conf.getLong("protect-time.mob") * 1000 else null
    }
}