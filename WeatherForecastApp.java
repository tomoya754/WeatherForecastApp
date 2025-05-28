import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 天気予報アプリ - 本体
 * このアプリケーションは、気象庁のWeb APIから大阪府の天気予報データを取得して表示します
 * 
 * @author n.katayama
 * @version 1.0
 */
public class WeatherForecastApp {
    private static final String TARGET_URL = "https://www.jma.go.jp/bosai/forecast/data/forecast/270000.json";

    /**
     * メインメソッド
     * WeatherApiClientを使用して天気予報データを取得し、標準出力へ表示する
     * 
     * @param args コマンドライン引数（使用しません）
     */
    public static void main(String[] args) {
        try {
            WeatherApiClient client = new WeatherApiClient(TARGET_URL); // 天気予報APIクライアント
            List<WeatherForecast> forecasts = client.fetchWeatherForecasts(); // 天気予報データを取得
            // 取得した天気予報データを表示
            for (WeatherForecast forecast : forecasts) {
                System.out.println(forecast.getDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + " "
                        + forecast.getWeather());
            }
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
}