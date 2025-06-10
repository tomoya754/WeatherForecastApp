import requests
from datetime import datetime
from typing import List


class WeatherForecast:
    """
    日時と天気情報を保持するデータクラス
    """

    def __init__(self, date_time: datetime, weather: str):
        self._date_time = date_time
        self._weather = weather

    def get_date_time(self) -> datetime:
        return self._date_time

    def get_weather(self) -> str:
        return self._weather


class WeatherApiClient:
    """
    天気予報APIクライアント
    APIから天気データ(JSON)を取得し、WeatherForecastのリストに変換する
    """

    def __init__(self, api_url: str):
        self.api_url = api_url

    def fetch_weather_forecasts(self) -> List[WeatherForecast]:
        response = requests.get(self.api_url)
        if response.status_code != 200:
            raise Exception(
                f"データの取得に失敗しました! レスポンスコード: {response.status_code}"
            )
        root_array = response.json()
        time_series = root_array[0]["timeSeries"][0]
        time_defines = time_series["timeDefines"]
        weathers = time_series["areas"][0]["weathers"]
        forecasts = []
        for date_str, weather in zip(time_defines, weathers):
            date_time = datetime.fromisoformat(date_str)
            forecasts.append(WeatherForecast(date_time, weather))
        return forecasts


if __name__ == "__main__":
    TARGET_URL = "https://www.jma.go.jp/bosai/forecast/data/forecast/270000.json"
    print("大阪府の天気予報を取得します...")
    try:
        client = WeatherApiClient(TARGET_URL)
        forecasts = client.fetch_weather_forecasts()
        for forecast in forecasts:
            print(
                forecast.get_date_time().strftime("%Y/%m/%d")
                + " "
                + forecast.get_weather()
            )
    except Exception as e:
        print(f"エラー: {e}")
