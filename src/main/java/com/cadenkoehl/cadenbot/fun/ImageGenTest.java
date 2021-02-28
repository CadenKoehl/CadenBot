package com.cadenkoehl.cadenbot.fun;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ImageGenTest extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        try {
            BufferedImage image = ImageIO.read(new URL(event.getAuthor().getEffectiveAvatarUrl()));
            Graphics g = image.getGraphics();
            String message = Arrays.stream(event.getArgs()).skip(1).collect(Collectors.joining(" "));
            g.setFont(new Font("Arial", 1, 50));
            g.drawString(message, 24, 100);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outStream);
            ByteArrayInputStream finalImage = new ByteArrayInputStream(outStream.toByteArray());
            event.getChannel().sendFile(finalImage, event.getAuthor().getName() + ".png").queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "image";
    }

    @Override
    public String getDescription() {
        return "Test out the image generation feature!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "image` `[message]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}