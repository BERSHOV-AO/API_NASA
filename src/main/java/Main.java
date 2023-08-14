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

/**
 * Нужно воспользоваться публичным API NASA и скачать ежедневно выгружаемые им изображение или другой контент
 * (например видео). Несмотря на то, что API публичный, доступ к нему предоставляется по ключу, который достаточно
 * просто получить по адресу: https://api.nasa.gov/.
 * <p>
 * Перейдя по ссылке, заполняем личными данными поля: First Name, Last Name, Email и в ответ
 * (а так же на почтовый адрес) будет выслан ключ. С этим ключом нужно делать запросы к API.
 * <p>
 * Итак, чтобы получить ссылку на картинку или другой контент, нужно:
 * <p>
 * Сделать запрос по адресу: https://api.nasa.gov/planetary/apod?api_key=ВАШ_КЛЮЧ
 * Разобрать полученный ответ
 * В ответе найти поле url - оно содержит адрес на изображение или другой контент (например видео),
 * который нужно скачать и сохранить локально (на своем компьютере), имя сохраняемого файла нужно взять из части url
 * (из примера ниже DSC1028_PetersNEOWISEAuroralSpike_800.jpg)
 * Проверить что сохраненный файл открывается.
 *
 * key 1 : DO11mbGtFLwBtnbrzTcepxy9Z8b8bTGAxzRdkDXo
 * key 2 : ma6vACpDJM8dk1jbY5VDjIb7y4zukGOudsMTWbej
 */


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

        HttpGet requestNASA = new HttpGet(nasa.getHdurl());
        CloseableHttpResponse responseNASA = httpClient.execute(requestNASA);

        String[] parts = nasa.getHdurl().split("/");
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
