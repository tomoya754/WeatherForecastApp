import java.time.LocalDateTime;

/**
 * 日時と天気情報を保持するデータクラス
 */
public class WeatherForecast {
    private final LocalDateTime dateTime; // 天気予報の日時
    private final String weather; // 天気情報（晴れ、曇り、雨など）

    /**
     * コンストラクタ
     *
     * @param dateTime 天気予報の日時
     * @param weather  天気情報（晴れ、曇り、雨など）
     */
    public WeatherForecast(LocalDateTime dateTime, String weather) {
        this.dateTime = dateTime;
        this.weather = weather;
    }

    /**
     * 天気予報の日時を取得する
     *
     * @return 天気予報の日時
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * 天気情報を取得する
     *
     * @return 天気情報（晴れ、曇り、雨など）
     */
    public String getWeather() {
        return weather;
    }
}
