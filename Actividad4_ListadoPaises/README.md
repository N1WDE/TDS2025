# Actividad No. 4 - Listado de Países

Aplicación Android nativa desarrollada en Kotlin para la asignatura **Introducción al desarrollo de aplicaciones móviles**.

**Estudiante:** Edwin Giancarlo Granados Juarez  
**Carné:** 25006933

## Funcionalidad
- Pantalla de inicio con nombre completo y número de carné.
- Botón Cargar países para abrir el catálogo.
- Consumo del servicio REST Countries.
- Listado desplazable con nombre, bandera, región y código ISO.
- Nombres en español cuando existe traducción disponible.
- Búsqueda por nombre, región o código.
- Indicador de carga, manejo de errores y opción de reintentar.

## Servicio REST
GET https://api.restcountries.com/countries/v5 con limit=100, offset y response_fields.  
Autenticación: Authorization: Bearer rc_live_demo (clave de demostración oficial).

La aplicación consulta las páginas sucesivas hasta completar el catálogo. La API v3.1 antigua está obsoleta; por eso se usa v5.

## Abrir en Android Studio
1. Descomprime el proyecto.
2. En Android Studio elige Open y selecciona esta carpeta.
3. Espera la sincronización de Gradle.
4. Ejecuta en un emulador o teléfono Android con Internet.

Requisitos: Android Gradle Plugin 8.13.2, Kotlin 2.2.21, Gradle 8.13, JDK 17 y Android SDK 36.

## Generar APK
Con Gradle 8.13 y Android SDK configurado, ejecuta:
gradle assembleDebug

Salida: app/build/outputs/apk/debug/app-debug.apk.
