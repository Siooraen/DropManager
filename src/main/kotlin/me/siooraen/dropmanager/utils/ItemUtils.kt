package me.siooraen.dropmanager.utils

import org.bukkit.inventory.ItemStack
import taboolib.module.nms.ItemTagData
import taboolib.module.nms.getItemTag
import taboolib.platform.util.isAir

/**
 * @author Siooraen
 * @since 2023/1/11 21:29
 */
fun ItemStack.getString(key: String, def: String = "null"): String {
    if (this.isAir) {
        return def
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeepOrElse(key, ItemTagData(def)).asString()
    }
    return this.getItemTag().getOrElse(key, ItemTagData(def)).asString()
}

fun ItemStack.getLong(key: String, def: Long = -1): Long {
    if (this.isAir) {
        return def
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeepOrElse(key, ItemTagData(def)).asLong()
    }
    return this.getItemTag().getOrElse(key, ItemTagData(def)).asLong()
}

fun ItemStack.set(key: String, value: Any?) {
    val tag = getItemTag()
    if (key.contains(".")) {
        if (value == null) {
            tag.removeDeep(key)
        } else {
            tag.putDeep(key, value)
        }
    } else {
        if (value == null) {
            tag.remove(key)
        } else {
            tag.put(key, value)
        }
    }
    tag.saveTo(this)
}