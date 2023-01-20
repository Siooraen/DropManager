package me.siooraen.dropmanager

import me.siooraen.dropmanager.DropManager.conf
import me.siooraen.dropmanager.dropnotice.DropNotice.read
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.chat.colored

/**
 * @author Siooraen
 * @since 2023/1/12 18:35
 */
@CommandHeader(name = "DropManager", aliases = ["dm"], permission = "*")
object DropManagerCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            conf.reload()
            read()
            sender.sendMessage("&c[DropManager] &7重载成功.".colored())
        }
    }
}