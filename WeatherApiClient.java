import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 天気予報APIクライアント
 * APIから天気データ(JSON)を取得し、WeatherForecastのリストに変換する
 */
public class WeatherApiClient {
    private final String apiUrl; // 天気予報APIのURL

    /**
     * コンストラクタ
     * 
     * @param apiUrl 天気予報APIのURL
     */
    public WeatherApiClient(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * 天気予報データを取得し、WeatherForecastリストとして返す
     * データ取得に関する例外処理は、呼び出し元にて行うこと
     * 
     * @param apiUrl 天気予報APIのURL
     * @return 天気予報のリスト
     */
    public List<WeatherForecast> fetchWeatherForecasts() throws IOException, java.net.URISyntaxException {
        URL url = new URI(apiUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("データの取得に失敗しました! レスポンスコード: " + responseCode);
        }
        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
        }
        JSONArray rootArray = new JSONArray(responseBody.toString());
        JSONObject timeStringObject = rootArray.getJSONObject(0)
                .getJSONArray("timeSeries").getJSONObject(0);
        JSONArray timeDefinesArray = timeStringObject.getJSONArray("timeDefines");
        JSONArray weathersArray = timeStringObject.getJSONArray("areas")
                .getJSONObject(0).getJSONArray("weathers");
        List<WeatherForecast> forecasts = new ArrayList<>();
        for (int i = 0; i < timeDefinesArray.length(); i++) {
            LocalDateTime dateTime = LocalDateTime.parse(
                    timeDefinesArray.getString(i),
                    DateTimeFormatter.ISO_DATE_TIME);
            String weather = weathersArray.getString(i);
            forecasts.add(new WeatherForecast(dateTime, weather));
        }
        return forecasts;
    }
}
