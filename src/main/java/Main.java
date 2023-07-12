import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static final String REMOTE_SERVICE_URL = "https://api.nasa.gov/planetary/" +
            "apod?api_key=DO11mbGtFLwBtnbrzTcepxy9Z8b8bTGAxzRdkDXo";

    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);

        NASA nasa = mapper.readValue(
                response.getEntity().getContent(), NASA.class);

        HttpGet requestNASA = new HttpGet(nasa.getUrl());
        CloseableHttpResponse responseNASA = httpClient.execute(requestNASA);

        String[] parts = nasa.getUrl().split("/");
        String nameFile = parts[6];

        Path path = Paths.get(nameFile);
        try {
            Files.write(path, responseNASA.getEntity().getContent().readAllBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        httpClient.close();
        response.close();
        responseNASA.close();
    }
}
