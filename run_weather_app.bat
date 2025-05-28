@echo off
REM Javaソースを全てコンパイル
javac -cp .;json-20250107.jar WeatherForecastApp.java WeatherApiClient.java WeatherForecast.java
if %errorlevel% neq 0 (
    echo コンパイルエラーが発生しました。
    exit /b %errorlevel%
)

REM アプリケーションを実行
java -cp .;json-20250107.jar WeatherForecastApp

pause

REM コンパイル後のクラスファイルを削除
del *.class
