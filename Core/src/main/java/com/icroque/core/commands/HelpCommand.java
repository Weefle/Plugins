package com.icroque.core.commands;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.util.ChatPaginator;

/**
 * Created by Rémi on 17/01/2016.
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "core.help", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String command;
        int pageNumber;
        int pageHeight;
        int pageWidth;

        if (args.length == 0) {
            command = "";
            pageNumber = 1;
        }
        else if (NumberUtils.isDigits(args[args.length - 1])) {
            command = StringUtils.join(ArrayUtils.subarray(args, 0, args.length - 1), " ");
            try {
                pageNumber = NumberUtils.createInteger(args[args.length - 1]);
            }
            catch (NumberFormatException exception) {
                pageNumber = 1;
            }
            if (pageNumber <= 0) {
                pageNumber = 1;
            }
        }
        else {
            command = StringUtils.join(args, " ");
            pageNumber = 1;
        }

        pageHeight = ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1;
        pageWidth = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;

        HelpMap helpMap = Bukkit.getServer().getHelpMap();
        HelpTopic topic = helpMap.getHelpTopic(command);

        if (topic == null) {
            topic = helpMap.getHelpTopic("/zzz" + command);
        }

        if (topic == null || !topic.canSee(sender)) {
            sender.sendMessage("§f[§cErreur§f] §cAucune aide disponible pour '" + command+"'.");
            return;
        }

        ChatPaginator.ChatPage page = ChatPaginator.paginate(topic.getFullText(sender), pageNumber, pageWidth, pageHeight);

        StringBuilder header = new StringBuilder();
        header.append("§f====== §e");
        header.append("Aide page ");
        header.append(page.getPageNumber());
        header.append("/");
        header.append(page.getTotalPages());
        header.append(" ");
        header.append("§f======");

        sender.sendMessage(header.toString());
        sender.sendMessage(page.getLines());
    }
}
