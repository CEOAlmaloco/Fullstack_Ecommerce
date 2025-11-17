@echo off
set BASE=%~dp0
echo ========================================
echo   LEVEL UP GAMER - BUILD Y START
echo ========================================
echo.

REM Verificar si los JARs ya existen
echo Verificando si los microservicios ya estan compilados...
set JARS_EXIST=1

if not exist "%BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-gateway\target\*.jar" set JARS_EXIST=0
if not exist "%BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos\target\*.jar" set JARS_EXIST=0

if %JARS_EXIST% EQU 1 (
    echo [OK] Los JARs ya existen
    set /p REBUILD="Deseas recompilar de todos modos? (s/N): "
    if /i "%REBUILD%"=="s" (
        echo Recompilando todos los microservicios...
        call "%BASE%Backend_Java_Spring\Fullstack_Ecommerce\build-all-services.bat"
    ) else (
        echo Usando JARs existentes...
    )
) else (
    echo [INFO] Algunos JARs no existen, compilando...
    call "%BASE%Backend_Java_Spring\Fullstack_Ecommerce\build-all-services.bat"
)

echo.
echo ========================================
echo   Iniciando servicios...
echo ========================================
echo.

REM Ejecutar START.bat
call "%BASE%START.bat"

