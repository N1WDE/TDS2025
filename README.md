# Conversor de Divisas - DivisApp 25006933

Aplicación móvil híbrida desarrollada con **Ionic React + Capacitor** para convertir quetzales guatemaltecos (GTQ) a dólares estadounidenses (USD).

## Funcionalidad
- Solicita una cantidad en quetzales.
- Convierte automáticamente a dólares usando la tasa fija: **Q8.00 = US$1.00**.
- Muestra el carné **25006933** en el título.
- Permite limpiar la cantidad.
- Interfaz adaptable a dispositivos móviles.

## Ejecutar
Requisitos: Node.js 20+ y npm.

```bash
npm install
npm run dev
```

## Compilar web
```bash
npm run build
```

## Compilar Android localmente
Requisitos: JDK 21 y Android SDK.

```bash
npm install
npm run build
npx cap add android
npx cap sync android
cd android
./gradlew assembleDebug
```

El APK se genera en `android/app/build/outputs/apk/debug/app-debug.apk`.

## APK mediante GitHub Actions
El repositorio incluye el workflow **Generar APK Android - DivisApp**. Entra a **Actions**, abre la ejecución más reciente y descarga el artefacto `DivisApp-Android-APK` cuando finalice correctamente.

## Fórmula
```ts
const dolares = quetzales / 8;
```

## Datos académicos
- Estudiante: Edwin Giancarlo Granados Juarez
- Carné: 25006933
