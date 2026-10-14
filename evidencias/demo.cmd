@echo off
cd /d "%~dp0.."
chcp 65001 >nul
mode con: cols=112 lines=48
title Evidencia de ejecucion: Arbol Binario
javac -encoding UTF-8 -d out src/modelo/Nodo.java src/negocio/ArbolBinario.java src/app/Main.java
if errorlevel 1 (
    pause
    exit /b 1
)
(
    echo 1
    echo 2
    echo 8
) | java -Dfile.encoding=UTF-8 -cp out app.Main
echo.
echo Presione una tecla para cerrar esta ventana.
pause >nul