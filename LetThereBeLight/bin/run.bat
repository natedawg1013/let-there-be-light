@echo off
rem check java version
java -d64 -version
if "%ERRORLEVEL%" == "0" java -Djava.library.path=.\lib\Windows\mfz-rxtx-2.2-20081207-win-x64 -jar "Let There Be Light.jar" 
else java -Djava.library.path=.\lib\Windows\i368-mingw32 -jar "Let There Be Light.jar"