package net.byteboost.duck.utils;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import net.byteboost.duck.keys.ApiKeys;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static java.time.Duration.ofSeconds;
import static net.byteboost.duck.gui.UploadController.selectedFile;
import static net.byteboost.duck.gui.UploadController.stream;
import static net.byteboost.duck.utils.FileUtils.*;

public class AIUtils {
    public static String loadIntoHugging(Document file, String question){

        EmbeddingModel embeddingModel = HuggingFaceEmbeddingModel.builder()
                .accessToken(ApiKeys.HF_API_KEY)
                .modelId("sentence-transformers/all-MiniLM-L6-v2")
                .waitForModel(true)
                .timeout(ofSeconds(60))
                .build();


        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(200,0))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        ingestor.ingest(file);

        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
                .chatLanguageModel(HuggingFaceChatModel.withAccessToken(ApiKeys.HF_API_KEY))
                .retriever(EmbeddingStoreRetriever.from(embeddingStore, embeddingModel))
                // .chatMemory() // you can override default chat memory
                // .promptTemplate() // you can override default prompt template
                .build();
        String response = chain.execute(question).trim();
        return response.isEmpty()? "Sorry, I have no response available for that question." : response;
    }
    public static Path formatText(String path) {
        //Inspired by Mateus Madeira's code in https://github.com/C0demain/API-2-semestre/blob/master/bot/lib/src/main/java/utilitarios/LimpaArquivo.java

        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        String doccontent = null;
        try {

            String dir = null;

            if (checkTXT(selectedFile)) {
                dir = path.replace(".txt", "_cleaned.txt");
                parser.parse(stream, handler, metadata, context);
                doccontent = handler.toString();
                System.out.println("\nFormatted TXT file to TXT");
            }
            else if (checkPDF(selectedFile)){
                dir = path.replace(".pdf", "_cleaned.txt");
                parser.parse(stream, handler, metadata, context);
                doccontent = handler.toString();
                System.out.println("\nFormatted PDF file to TXT");
            }
            else if (checkDOCX(selectedFile)) {
                dir = path.replace(".docx", "_cleaned.txt");
                parser.parse(stream, handler, metadata, context);
                doccontent = handler.toString();
                System.out.println("\nFormatted DOCX file to TXT");
            }
            stream.close();

            assert doccontent != null;

            BufferedReader reader = new BufferedReader(new StringReader(doccontent));

            BufferedWriter writer = new BufferedWriter(new FileWriter(dir, StandardCharsets.UTF_8));
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.matches("\\d+")) {
                    content.append(line).append("\n");
                }
            }

            reader.close();
            content = new StringBuilder(content.toString().replace(".", ".\n"));
            content = new StringBuilder(content.toString().replace("\n\n", "\n"));
            content = new StringBuilder(content.toString().replace("\t", ""));
            writer.write(content.toString());
            writer.close();
            return Path.of(dir);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException | SAXException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}

