package me.siooraen.dropmanager

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

/**
 * @author Siooraen
 * @since 2023/1/8 13:27
 */
object DropManager : Plugin() {

    override fun onEnable() {
        console().sendMessage("DropManager成功运行!")
    }

    @Config
    lateinit var conf: ConfigFile
        private set
}