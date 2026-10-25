package ma.enset.security.ebankbot.agents;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EbankAiAgent {

    private final ChatClient chatClient;

    public EbankAiAgent(ChatClient.Builder builder,
                        ChatMemory chatMemory,
                        ToolCallbackProvider tools) {
        this.chatClient = builder
                .defaultSystem("""
                        Tu es l'assistant d'une banque. Tu réponds aux questions sur les
                        clients et les comptes bancaires en utilisant les outils disponibles.
                        
                        Règles :
                        - Utilise toujours les outils pour obtenir les données. Ne devine jamais.
                        - N'invente JAMAIS un identifiant. Si tu n'as pas l'ID d'un client,
                          récupère d'abord la liste des clients et trouve son ID.
                        - Pour les comptes d'un client donné par son nom : 1) liste les clients,
                          2) trouve son ID, 3) appelle l'outil des comptes avec cet ID.
                        - Si, après avoir utilisé les outils, tu ne trouves rien, dis-le clairement.
                        - Réponds dans la langue de l'utilisateur.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(tools)   // voir la note ci-dessous
                .build();
    }

    public String chat(Prompt query, String conversationId) {
        return chatClient.prompt(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
