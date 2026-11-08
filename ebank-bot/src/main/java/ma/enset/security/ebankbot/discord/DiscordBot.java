package ma.enset.security.ebankbot.discord;

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import ma.enset.security.ebankbot.agents.EbankAiAgent;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.context.annotation.Profile;

@DiscordController
@Profile("discord")
public class DiscordBot {

    private EbankAiAgent ebankAiAgent;
    public DiscordBot(EbankAiAgent ebankAiAgent) {
        this.ebankAiAgent = ebankAiAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String query = event.getMessage().getContentRaw();
        try {
            event.getChannel().sendTyping().queue();
            String response = ebankAiAgent.chat(new Prompt(query),
                    "discord-" + event.getChannel().getId());
            sendLong(event.getChannel(), response);
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage("Sorry, an error occurred.").queue();
        }
    }

    private void sendLong(MessageChannel channel, String text) {
        StringBuilder chunk = new StringBuilder();
        for (String line : text.split("\n", -1)) {
            if (chunk.length() > 0 && chunk.length() + line.length() + 1 > 1900) {
                channel.sendMessage(chunk.toString()).queue();
                chunk.setLength(0);
            }
            chunk.append(line).append("\n");
        }
        if (chunk.length() > 0) channel.sendMessage(chunk.toString()).queue();
    }

}
