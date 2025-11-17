@echo off
set BASE=%~dp0
echo ========================================
echo   LEVEL UP GAMER - BUILD TODOS LOS SERVICIOS
echo ========================================
echo.

REM Configurar Java 21
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo Verificando Java...
java -version
echo.

echo ========================================
echo   Compilando microservicios...
echo ========================================
echo.

REM Función para compilar un microservicio
set BUILD_COUNT=0
set FAILED_COUNT=0

REM Compilar Gateway
echo [1/14] Compilando Gateway...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-gateway
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Gateway compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Gateway
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Auth
echo [2/14] Compilando Auth...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-auth
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Auth compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Auth
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Usuario
echo [3/14] Compilando Usuario...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-usuario
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Usuario compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Usuario
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Productos
echo [4/14] Compilando Productos...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Productos compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Productos
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Inventario
echo [5/14] Compilando Inventario...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-inventario
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Inventario compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Inventario
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Referidos
echo [6/14] Compilando Referidos...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-referidos
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Referidos compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Referidos
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Notificaciones
echo [7/14] Compilando Notificaciones...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-notificaciones
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Notificaciones compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Notificaciones
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Carrito
echo [8/14] Compilando Carrito...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-carrito
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Carrito compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Carrito
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Reseñas
echo [9/14] Compilando Reseñas...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-resenia
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Reseñas compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Reseñas
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Pagos
echo [10/14] Compilando Pagos...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-pagos
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Pagos compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Pagos
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Pedido
echo [11/14] Compilando Pedido...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-pedido
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Pedido compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Pedido
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Promociones
echo [12/14] Compilando Promociones...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-promociones
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Promociones compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Promociones
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Eventos
echo [13/14] Compilando Eventos...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-eventos
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Eventos compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Eventos
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

REM Compilar Contenido
echo [14/14] Compilando Contenido...
cd /d %BASE%Backend_Java_Spring\Fullstack_Ecommerce\msvc-contenido
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% EQU 0 (
    echo [OK] Contenido compilado
    set /a BUILD_COUNT+=1
) else (
    echo [ERROR] Fallo al compilar Contenido
    set /a FAILED_COUNT+=1
)
cd %BASE%Backend_Java_Spring\Fullstack_Ecommerce
echo.

echo ========================================
echo   RESUMEN DE COMPILACION
echo ========================================
echo.
echo Compilados exitosamente: %BUILD_COUNT% de 14
echo Fallidos: %FAILED_COUNT% de 14
echo.
echo Los JARs estan en: msvc-*/target/*.jar
echo.
echo Para iniciar los servicios, ejecuta:
echo   START.bat
echo.
echo ========================================
pause

