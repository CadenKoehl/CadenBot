package com.cadenkoehl.cadenbot.fun;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ImageGenTest extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "image")) {
            try {
                BufferedImage image = ImageIO.read(new File(CadenBot.dataDirectory + "resources/image.png"));
                Graphics g = image.getGraphics();
                String message = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
                g.setFont(new Font("Arial", 1, 50));
                g.drawString(message, 24, 100);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outStream);
                ByteArrayInputStream finalImage = new ByteArrayInputStream(outStream.toByteArray());
                event.getChannel().sendFile(finalImage, "img.png").queue();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}