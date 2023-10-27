package net.byteboost.duck.utils;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceModelName;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import net.byteboost.duck.keys.ApiKeys;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.joining;

public class AIUtils {
    public static String loadIntoHugging(Document file, String question){
        // Load the document that includes the information you'd like to "chat" about with the model.
        // Embed segments (convert them into vectors that represent the meaning) using embedding model
        EmbeddingModel embeddingModel =
                HuggingFaceEmbeddingModel.builder()
                        .accessToken(ApiKeys.HF_API_KEY)
                        .modelId("sentence-transformers/all-MiniLM-L6-v2")
                        .waitForModel(true)
                        .timeout(ofSeconds(60))
                        .build();

        // Split document into segments 100 tokens each
        DocumentSplitter splitter = DocumentSplitters.recursive(
                200,
                0
                );

        List<TextSegment> segments = splitter.split(file);




        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        embeddingStore.addAll(embeddings, segments);

        // Embed the question
        Embedding questionEmbedding = embeddingModel.embed(question).content();

        // Find relevant embeddings in embedding store by semantic similarity
        // You can play with parameters below to find a sweet spot for your specific use case

        int maxResults = 5;
        double minScore = 0.7;
        List<EmbeddingMatch<TextSegment>> relevantEmbeddings = embeddingStore.findRelevant(questionEmbedding, maxResults, minScore);

        // Create a prompt for the model that includes question and relevant embeddings
        PromptTemplate promptTemplate = PromptTemplate.from(
                "Answer the following question to the best of your ability:\n"
                        + "\n"
                        + "Question:\n"
                        + "{{question}}\n"
                        + "\n"
                        + "Base your answer on the following information:\n"
                        + "{{information}}");

        String information = relevantEmbeddings.stream()
                .map(match -> match.embedded().text())
                .collect(joining("\n\n"));

        Map<String, Object> variables = new HashMap<>();
        variables.put("question", question);
        variables.put("information", information);

        Prompt prompt = promptTemplate.apply(variables);

        // Send the prompt to the OpenAI chat model

        ChatLanguageModel chatModel = HuggingFaceChatModel.withAccessToken(ApiKeys.HF_API_KEY);

        AiMessage aiMessage = chatModel.generate(prompt.toUserMessage()).content();

        // See an answer from the model
        String answer = aiMessage.text();

        return answer; // Charlie is a cheerful carrot living in VeggieVille...
    }
}

