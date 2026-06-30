# HITO 3: DevSecOps e Integración Continua
## Informe Técnico - UrbanFlow

---

## SECCIÓN 1: DevSecOps y Objetivo del Hito 3
### Integrante: RENZO

---

### 1. ¿QUÉ ES DEVSECOPS?

**Definición:**
DevSecOps es un enfoque metodológico que integra **desarrollo (Dev)**, **seguridad (Sec)** y **operaciones (Ops)** en un único flujo de trabajo continuo. No es simplemente agregar seguridad al final del desarrollo, sino incorporarla desde el inicio en todas las etapas del ciclo de vida del software.

**Componentes principales:**

- **Desarrollo (Dev):** Los desarrolladores escriben código siguiendo estándares de seguridad
- **Seguridad (Sec):** Pruebas automatizadas de vulnerabilidades, análisis de código estático, auditorías
- **Operaciones (Ops):** Despliegue, monitoreo y mantenimiento en producción

**Filosofía clave:**
DevSecOps busca automatizar pruebas, análisis de calidad y despliegues para que la seguridad no sea un cuello de botella, sino parte integral del proceso.

---

### 2. OBJETIVOS DEL HITO 3

El Hito 3 se centra en **transformar código local en un artefacto listo para ejecutar**, utilizando automatización mediante CI/CD. Los objetivos específicos son:

#### 2.1 Transformar código local en artefacto
- Compilar el código fuente (Java/Spring Boot)
- Ejecutar pruebas unitarias e integración
- Empaquetar como JAR (Java Archive)
- Generar imagen Docker lista para desplegar

#### 2.2 Automatizar procesos mediante CI/CD
- **CI (Integración Continua):** Cada push a la rama develop dispara automáticamente pruebas
- **CD (Despliegue Continuo):** El artefacto se construye, analiza y está listo para producción
- **Zero-touch:** Minimizar intervención manual

#### 2.3 Garantizar calidad y seguridad
- Análisis de código estático (SonarCloud)
- Pruebas automatizadas
- Escaneo de vulnerabilidades
- Verificación de dependencias

---

### 3. FLUJO GENERAL: CÓDIGO A APLICACIÓN

```
┌─────────────┐
│   Código    │
│  (local)    │
└──────┬──────┘
       │ git push origin develop
       ▼
┌─────────────────────┐
│  GitHub Repository  │
│    (código base)    │
└──────┬──────────────┘
       │ Webhook trigger
       ▼
┌──────────────────────────┐
│   GitHub Actions        │
│   (CI Pipeline)         │
└──────┬───────────────────┘
       │
       ├─► Pull código
       ├─► Setup Java 17
       ├─► Ejecutar mvn clean test
       ├─► Ejecutar mvn clean package
       │
       ▼
┌─────────────────────────┐
│   SonarCloud            │
│   (Análisis estático)   │
│   - Bugs               │
│   - Vulnerabilidades  │
│   - Code smells       │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│   Docker Build          │
│   - Crear imagen        │
│   - Escanear           │
│   - Push a registry     │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│   Aplicación desplegada │
│   en entorno objetivo   │
└─────────────────────────┘
```

**Desglose del flujo:**

1. **Código local → GitHub**
   - Desarrollador hace `git commit` y `git push`
   - El código llega al repositorio central

2. **GitHub → GitHub Actions**
   - Webhook automático dispara el pipeline
   - Se crea un runner (máquina virtual)

3. **Pruebas automáticas**
   - Maven ejecuta `mvn clean test`
   - Si hay fallos, el pipeline se detiene

4. **Análisis de calidad (SonarCloud)**
   - Escanea bugs, vulnerabilidades
   - Genera reportes de deuda técnica

5. **Construcción de Docker**
   - Se crea imagen containerizada
   - Se escanean vulnerabilidades
   - Se publica en registro

6. **Despliegue**
   - Imagen lista para ejecutarse
   - Sin intervención manual

---

### 4. BENEFICIOS DE DEVSECOPS

#### 4.1 Menos errores humanos
- **Automatización:** Las pruebas se ejecutan automáticamente sin olvidar pasos
- **Consistencia:** El mismo proceso cada vez
- **Rapidez:** Reducción de tiempo entre development y producción

**Ejemplo:** 
- Sin CI/CD: Desarrollador olvida ejecutar pruebas locales
- Con CI/CD: GitHub Actions ejecuta pruebas automáticamente

#### 4.2 Código más seguro
- **Detección temprana de vulnerabilidades** antes de producción
- **Análisis de dependencias:** Identifica librerías con CVEs (vulnerabilidades conocidas)
- **Code review obligatorio:** Integración de cambios requiere aprobación
- **Escaneo de secretos:** Evita que credenciales se suban a GitHub

**Ejemplo en UrbanFlow:**
```
- Dependencia: com.mysql:mysql-connector-java:8.0.33
  SonarCloud revisa si tiene CVE conocidos
- Si tiene vulnerabilidad: El pipeline falla y notifica
- El equipo arregla antes de llegar a producción
```

#### 4.3 Despliegues más rápidos
- **Automatización end-to-end:** De código a producción en minutos
- **Confianza:** Las pruebas dan confianza para desplegar frecuentemente
- **Rollback rápido:** Si hay problemas, se revierte en segundos
- **Time-to-market:** Características llegan a usuarios más rápido

**Comparativa:**
- **Sin CI/CD:** Despliegue manual = 2-3 horas (propenso a errores)
- **Con CI/CD:** Despliegue automático = 5-10 minutos

---

### 5. RESUMEN

DevSecOps es la integración de seguridad en el desarrollo desde el inicio. El Hito 3 implementa esto mediante:

| Aspecto | Acción |
|--------|--------|
| **Código local** | Desarrollador commits |
| **Integración** | GitHub Actions detecta cambio |
| **Testing** | Pruebas automáticas |
| **Seguridad** | SonarCloud analiza |
| **Artefacto** | Docker empaqueta |
| **Resultado** | App lista para producción |

---

---

## SECCIÓN 2: Integración Continua (CI) con GitHub Actions
### Integrante: URIEL

---

### 1. ¿QUÉ ES GITHUB ACTIONS?

**Definición:**
GitHub Actions es la herramienta nativa de GitHub que permite automatizar tareas en respuesta a eventos del repositorio. Es un **motor de automatización CI/CD** integrado directamente en el flujo de trabajo de Git.

**Características principales:**

- ✅ **Basado en eventos:** Se dispara con push, pull request, releases, etc.
- ✅ **Gratuito:** Minutos incluidos en el plan (3,000 min/mes en público)
- ✅ **Flexible:** Soporta cualquier lenguaje (Java, Python, Node.js, etc.)
- ✅ **Reutilizable:** Marketplace con acciones pre-construidas
- ✅ **Parallelizable:** Múltiples jobs simultáneos

---

### 2. COMPONENTES DEL WORKFLOW

Un workflow de GitHub Actions se define en archivos YAML dentro de `.github/workflows/`

#### 2.1 Estructura básica

```yaml
name: Nombre del workflow

# Cuándo se ejecuta
on:
  push:
    branches:
      - develop
  pull_request:
    branches:
      - develop

# Qué hacer
jobs:
  job-name:
    runs-on: ubuntu-latest
    steps:
      - name: Descripción del paso
        run: comando a ejecutar
```

#### 2.2 Desglose de secciones

| Sección | Propósito | Ejemplo |
|---------|-----------|---------|
| **name** | Identificador del workflow | "CI Spring Boot" |
| **on** | Eventos que lo disparan | push, pull_request, schedule |
| **jobs** | Tareas a ejecutar | test, build, deploy |
| **runs-on** | Sistema operativo | ubuntu-latest, windows-latest |
| **steps** | Pasos secuenciales | checkout, setup, test, build |

---

### 3. WORKFLOW CI PARA URBANFLOW

#### 3.1 Archivo: `.github/workflows/ci.yml`

```yaml
name: CI Spring Boot - UrbanFlow

on:
  push:
    branches:
      - develop
      - main
  pull_request:
    branches:
      - develop

jobs:
  test:
    name: Build and Test
    runs-on: ubuntu-latest
    
    strategy:
      matrix:
        java-version: [17]

    steps:
      # Paso 1: Descargar el código
      - name: Checkout código
        uses: actions/checkout@v4

      # Paso 2: Configurar Java
      - name: Setup Java JDK
        uses: actions/setup-java@v4
        with:
          java-version: ${{ matrix.java-version }}
          distribution: 'temurin'
          cache: maven

      # Paso 3: Validar estructura Maven
      - name: Validar pom.xml
        run: mvn validate

      # Paso 4: Compilar código
      - name: Compilar código
        run: mvn clean compile

      # Paso 5: Ejecutar pruebas
      - name: Ejecutar tests unitarios
        run: mvn test

      # Paso 6: Empaquetar aplicación
      - name: Empaquetar JAR
        run: mvn package -DskipTests

      # Paso 7: Verificar artefacto
      - name: Verificar JAR generado
        run: ls -lh target/*.jar

      # Paso 8: Reportar resultados
      - name: Publicar resultados de pruebas
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: test-reports-java${{ matrix.java-version }}
          path: target/surefire-reports/
```

---

### 4. EXPLICACIÓN PASO A PASO

#### **Paso 1: Descargar el código**
```yaml
- uses: actions/checkout@v4
```

**¿Qué hace?**
- Descarga el código del repositorio al runner (máquina virtual)
- Equivalente a hacer `git clone` en tu máquina local

**Por qué es importante:**
- Sin este paso, GitHub Actions no tendría acceso al código para compilar

---

#### **Paso 2: Configurar Java 17**
```yaml
- uses: actions/setup-java@v4
  with:
    java-version: '17'
    distribution: 'temurin'
    cache: maven
```

**¿Qué hace?**
- Instala Java Development Kit (JDK) versión 17
- Descarga la distribución "temurin" (OpenJDK confiable)
- Habilita caché de Maven para acelerar descargas

**Por qué es importante:**
- UrbanFlow usa Java 17 (ver en pom.xml)
- Sin Java, no se puede compilar el código Spring Boot
- El caché acelera construcciones futuras

**Verificar versión de Java en pom.xml:**
```xml
<properties>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>
```

---

#### **Paso 3: Validar estructura Maven**
```yaml
- run: mvn validate
```

**¿Qué hace?**
- Verifica que `pom.xml` esté bien formado
- Valida la estructura del proyecto

**Beneficio:**
- Detecta errores de configuración antes de compilar

---

#### **Paso 4: Compilar código**
```yaml
- run: mvn clean compile
```

**¿Qué hace?**
- `clean`: Limpia compilaciones anteriores
- `compile`: Traduce código Java a bytecode (archivos .class)

**Si falla aquí:**
- Error de sintaxis en Java
- Dependencia faltante
- Versión de Java incorrecta

---

#### **Paso 5: Ejecutar pruebas**
```yaml
- run: mvn test
```

**¿Qué hace?**
- Ejecuta todos los tests en `src/test/java/`
- Usa JUnit (framework de testing)
- Genera reportes en `target/surefire-reports/`

**Ejemplo de test en UrbanFlow:**
```java
@SpringBootTest
public class SistemaApplicationTests {
    
    @Test
    public void contextLoads() {
        // Si la app inicia correctamente, pasa el test
    }
    
    @Test
    public void testClienteCreation() {
        Cliente cliente = new Cliente("Juan", "juan@test.com");
        assertNotNull(cliente.getId());
    }
}
```

**Si un test falla:**
- El pipeline se detiene (no continúa con siguientes pasos)
- El desarrollador recibe notificación
- No se construye el JAR hasta arreglarlo

---

#### **Paso 6: Empaquetar aplicación**
```yaml
- run: mvn package -DskipTests
```

**¿Qué hace?**
- Crea un archivo JAR ejecutable
- `-DskipTests`: Salta tests (ya corrieron en paso anterior)
- Resultado: `target/urbanflow-1.0.0-SNAPSHOT.jar`

**Qué contiene el JAR:**
- Código compilado de UrbanFlow
- Librerías (Spring Boot, MySQL connector, etc.)
- Configuración (application.properties)
- Recursos estáticos (HTML, CSS, JS)

---

#### **Paso 7: Verificar artefacto**
```yaml
- run: ls -lh target/*.jar
```

**¿Qué hace?**
- Lista los archivos JAR generados
- Muestra tamaño (para detectar anomalías)

**Ejemplo de salida:**
```
-rw-r--r-- 1 runner docker 45M Jul 24 10:30 target/urbanflow-1.0.0-SNAPSHOT.jar
```

---

#### **Paso 8: Reportar resultados**
```yaml
- uses: actions/upload-artifact@v4
  with:
    name: test-reports-java${{ matrix.java-version }}
    path: target/surefire-reports/
```

**¿Qué hace?**
- Sube los reportes de pruebas como artefactos
- Disponibles en la interfaz web de GitHub
- Permite ver detalles de qué tests pasaron/fallaron

---

### 5. FLUJO DE EJECUCIÓN VISUAL

```
[Desarrollador hace git push]
           ↓
[GitHub detecta evento]
           ↓
[GitHub Actions crea runner]
           ↓
┌─────────────────────────────┐
│   CHECKOUT                  │
│  Descargar código           │
└──────────┬──────────────────┘
           ↓
┌─────────────────────────────┐
│   SETUP JAVA                │
│  Instalar JDK 17            │
└──────────┬──────────────────┘
           ↓
┌─────────────────────────────┐
│   VALIDATE                  │
│  Verificar pom.xml          │
└──────────┬──────────────────┘
           ↓
┌─────────────────────────────┐
│   COMPILE                   │
│  mvn clean compile          │
└──────────┬──────────────────┘
           ↓
        ¿OK?
        / \
       /   \
      NO   SÍ
      |     |
      |     ↓
      |  ┌─────────────────────────────┐
      |  │   TEST                      │
      |  │  mvn test                   │
      |  └──────────┬──────────────────┘
      |             ↓
      |          ¿OK?
      |          / \
      |         /   \
      |        NO   SÍ
      |        |     |
      |        |     ↓
      |        |  ┌─────────────────────────────┐
      |        |  │   PACKAGE                   │
      |        |  │  mvn package                │
      |        |  └──────────┬──────────────────┘
      |        |             ↓
      |        |  ┌─────────────────────────────┐
      |        |  │   UPLOAD REPORTS            │
      |        |  │  test-reports/              │
      |        |  └──────────┬──────────────────┘
      |        |             ↓
      ↓        ↓        [ÉXITO: JAR listo]
   [NOTIFICACIÓN DE FALLO]
    (correo/Slack)
```

---

### 6. EJEMPLO REAL: PRIMER PUSH

**Escenario:** Desarrollador hace push a rama develop

**Paso a paso:**

1. **Desarrollador en su máquina:**
```bash
$ git add .
$ git commit -m "Agregar validación de cliente"
$ git push origin develop
```

2. **GitHub recibe el push:**
```
✓ Commit recibido
✓ Rama: develop
✓ Dispara webhook → GitHub Actions
```

3. **GitHub Actions crea runner:**
```
🔄 Iniciando runner: ubuntu-latest
⏱ Tiempo estimado: 8-12 minutos
```

4. **Se ejecutan los steps:**
```
✅ Checkout: Código descargado (30s)
✅ Setup Java: JDK 17 instalado (45s)
✅ Validate: pom.xml OK (10s)
✅ Compile: 45 archivos compilados (1m 30s)
✅ Test: 12 tests ejecutados - 11 ✓, 1 ✗

❌ FALLÓ: ClienteControladorTest.testActualizarCliente()
   Error: esperaba 200 pero recibió 400
```

5. **Resultado:**
```
Pipeline: FALLIDO ❌
JAR: No se generó
Notificación: Desarrollador recibe email
```

6. **Desarrollador arregla el bug:**
```bash
$ git add .
$ git commit -m "Arreglar validación de actualizacion de cliente"
$ git push origin develop
```

7. **Se ejecuta de nuevo, esta vez:**
```
✅ Todos los tests pasaron
✅ JAR generado: urbanflow-1.0.0-SNAPSHOT.jar (45MB)
✅ Pipeline: EXITOSO ✓
```

---

### 7. CONFIGURACIÓN MÍNIMA PARA URBANFLOW

**Archivo a crear:** `.github/workflows/ci.yml`

```yaml
name: CI

on:
  push:
    branches: [ develop, main ]
  pull_request:
    branches: [ develop ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4
    
    - uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven
    
    - run: mvn clean test
    - run: mvn clean package -DskipTests
```

**¿Por qué este es mínimo?**
- Solo 3 steps: checkout, setup, test, package
- No incluye análisis de seguridad (puede agregarse después)
- No incluye Docker (puede agregarse después)

---

### 8. CÓMO HABILITAR EN TU PROYECTO

**Pasos:**

1. **Crear estructura de carpetas:**
```bash
mkdir -p .github/workflows
```

2. **Crear archivo ci.yml:**
```bash
touch .github/workflows/ci.yml
```

3. **Pegar contenido YAML** (ver Sección 3.1)

4. **Hacer commit:**
```bash
git add .github/workflows/ci.yml
git commit -m "Agregar CI workflow"
git push origin develop
```

5. **Verificar en GitHub:**
   - Ir a: https://github.com/TU_USUARIO/UrbanFlow/actions
   - Deberías ver el workflow ejecutándose

6. **Esperar resultado:**
   - ✅ Verde = Éxito
   - ❌ Rojo = Fallo
   - 🟡 Amarillo = En progreso

---

### 9. TROUBLESHOOTING

| Problema | Causa | Solución |
|----------|-------|----------|
| **Setup Java falla** | Versión no disponible | Verificar Java 17 en pom.xml |
| **Compile falla** | Dependencia faltante | Ejecutar `mvn clean install` local |
| **Tests fallan** | Conexión a BD | Usar H2 in-memory para tests |
| **Package falla** | Errores de compilación | Ver logs del error |
| **Lento (>15 min)** | Descargas de Maven | Habilitar caché (ver línea `cache: maven`) |

---

### 10. RESUMEN

GitHub Actions automatiza el testing y construcción de UrbanFlow:

| Fase | Comando | Duración | Resultado |
|------|---------|----------|-----------|
| Checkout | `git clone` | 30s | Código disponible |
| Setup | Instalar JDK | 45s | Java 17 listo |
| Compile | `mvn compile` | 1m 30s | Validar sintaxis |
| Test | `mvn test` | 2m | Verificar funcionalidad |
| Package | `mvn package` | 1m | JAR ejecutable |
| **Total** | **Automático** | **8-12 min** | **Sin intervención manual** |

---

---

## SECCIÓN 3: GUÍA PRÁCTICA DE IMPLEMENTACIÓN

---

### PASO 1: Preparar el repositorio

```bash
# 1.1 Navegar al proyecto
cd c:\Users\leo45\OneDrive\Escritorio\UrbanFlow

# 1.2 Verificar que es un repo git
git status

# 1.3 Si no es repo, inicializar
git init
git add .
git commit -m "Commit inicial"
```

### PASO 2: Crear carpeta de workflows

```bash
# Windows (PowerShell)
New-Item -ItemType Directory -Path ".github/workflows" -Force

# Verificar
ls .github/workflows
```

### PASO 3: Crear el archivo CI

**Ruta:** `.github/workflows/ci.yml`

**Contenido:** (ver Sección 3.1 arriba)

### PASO 4: Hacer commit y push

```bash
git add .github/workflows/ci.yml
git commit -m "feat: Agregar GitHub Actions CI para Spring Boot"
git push origin develop
```

### PASO 5: Monitorear ejecución

1. Ir a GitHub (web)
2. Pestaña "Actions"
3. Ver el workflow ejecutándose en tiempo real

---

## CONCLUSIÓN

DevSecOps mediante GitHub Actions transforma el desarrollo de UrbanFlow en un proceso:

✅ **Automático:** Sin intervención manual
✅ **Seguro:** Detección temprana de problemas
✅ **Rápido:** De código a JAR en ~10 minutos
✅ **Confiable:** Mismo proceso cada vez
✅ **Escalable:** Fácil de agregar más pasos (Docker, SonarCloud, etc.)

---

**Autores del informe:**
- **RENZO:** Introducción DevSecOps y objetivos (Sección 1)
- **URIEL:** Integración Continua con GitHub Actions (Sección 2)

**Fecha:** 24 de junio de 2026
**Proyecto:** UrbanFlow
**Tecnologías:** Spring Boot 3.x, Java 17, Maven, GitHub Actions
