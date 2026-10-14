@echo off
cd /d "%~dp0.."
chcp 65001 >nul
mode con: cols=112 lines=48
title Evidencia de casos de prueba: Arbol Binario
javac -encoding UTF-8 -d out src/modelo/Nodo.java src/negocio/ArbolBinario.java test/PruebasArbolBinario.java
if errorlevel 1 (
    pause
    exit /b 1
)
(
    echo.
) | java -Dfile.encoding=UTF-8 -cp out test.PruebasArbolBinario
echo.
echo Presione una tecla para cerrar esta ventana.
pause >nul