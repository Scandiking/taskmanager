@echo off
echo Building and running TaskManager application...

if exist "C:\Program Files\Java\jdk-22\bin\java.exe" (
    echo Found Java 22
    set "JAVA_PATH=C:\Program Files\Java\jdk-22\bin\java"
    set "JAVA_HOME=C:\Program Files\Java\jdk-22"
    set "PATH=%JAVA_HOME%\bin;%PATH%"
) else (
    echo Error: Java 22 not found. Please install Java 22.
    pause
    exit /b 1
)

echo Java version:
%JAVA_PATH% -version

echo JAVA_HOME set to: %JAVA_HOME%

echo Building with Maven...
call mvn clean package

if exist "target\TaskManager-0.0.1-SNAPSHOT.jar" (
    echo Running application...
    "%JAVA_PATH%" -jar target\TaskManager-0.0.1-SNAPSHOT.jar
) else (
    echo Error: Failed to build the application.
)
pause
