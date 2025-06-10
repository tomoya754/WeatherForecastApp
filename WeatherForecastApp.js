// WeatherForecastApp.js
// 大阪府の天気予報を取得して表示するアプリ

const fetch = require('node-fetch');

class WeatherForecast {
    /**
     * @param {Date} dateTime 天気予報の日時
     * @param {string} weather 天気情報（晴れ、曇り、雨など）
     */
    constructor(dateTime, weather) {
        this.dateTime = dateTime;
        this.weather = weather;
    }
    getDateTime() {
        return this.dateTime;
    }
    getWeather() {
        return this.weather;
    }
}

class WeatherApiClient {
    /**
     * @param {string} apiUrl 天気予報APIのURL
     */
    constructor(apiUrl) {
        this.apiUrl = apiUrl;
    }
    /**
     * 天気予報データを取得し、WeatherForecastリストとして返す
     * @returns {Promise<WeatherForecast[]>}
     */
    async fetchWeatherForecasts() {
        const response = await fetch(this.apiUrl);
        if (!response.ok) {
            throw new Error(`データの取得に失敗しました! レスポンスコード: ${response.status}`);
        }
        const rootArray = await response.json();
        const timeSeries = rootArray[0]["timeSeries"][0];
        const timeDefines = timeSeries["timeDefines"];
        const weathers = timeSeries["areas"][0]["weathers"];
        const forecasts = [];
        for (let i = 0; i < timeDefines.length; i++) {
            const dateTime = new Date(timeDefines[i]);
            const weather = weathers[i];
            forecasts.push(new WeatherForecast(dateTime, weather));
        }
        return forecasts;
    }
}

(async () => {
    const TARGET_URL = "https://www.jma.go.jp/bosai/forecast/data/forecast/270000.json";
    console.log("大阪府の天気予報を取得します...");
    try {
        const client = new WeatherApiClient(TARGET_URL);
        const forecasts = await client.fetchWeatherForecasts();
        for (const forecast of forecasts) {
            const date = forecast.getDateTime();
            const formatted = `${date.getFullYear()}/${String(date.getMonth() + 1).padStart(2, '0')}/${String(date.getDate()).padStart(2, '0')}`;
            console.log(`${formatted} ${forecast.getWeather()}`);
        }
    } catch (e) {
        console.error("エラー:", e.message);
    }
})();
