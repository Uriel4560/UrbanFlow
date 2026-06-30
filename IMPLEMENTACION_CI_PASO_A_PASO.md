# GUÍA RÁPIDA: Activar CI/CD en UrbanFlow

## ✅ PASO 1: Preparativos locales

### 1.1 Verificar que tengas Git configurado
```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu@email.com"
```

### 1.2 Navegar a la carpeta del proyecto
```bash
cd c:\Users\leo45\OneDrive\Escritorio\UrbanFlow
```

### 1.3 Inicializar como repositorio Git (si no lo es)
```bash
git init
git add .
git commit -m "Initial commit - UrbanFlow"
```

---

## ✅ PASO 2: Crear repositorio en GitHub

### 2.1 Ir a https://github.com/new

### 2.2 Llenar formulario:
- **Repository name:** UrbanFlow
- **Description:** Sistema de ventas de ropa
- **Visibility:** Public (para poder usar GitHub Actions gratis)
- **Initialize:** Dejar sin marcar

### 2.3 Hacer clic en "Create repository"

---

## ✅ PASO 3: Conectar repositorio local con GitHub

### 3.1 En terminal, agregar remoto
```bash
git remote add origin https://github.com/TU_USERNAME/UrbanFlow.git
```

**Reemplazar TU_USERNAME con tu usuario de GitHub**

### 3.2 Cambiar rama a develop
```bash
git branch -M develop
```

### 3.3 Hacer push del código
```bash
git push -u origin develop
```

**Nota:** Te pedirá autenticación. Usar:
- **Usuario:** Tu usuario de GitHub
- **Contraseña:** Personal Access Token (no contraseña)

---

## ✅ PASO 4: Verificar que los archivos estén en GitHub

1. Ir a https://github.com/TU_USERNAME/UrbanFlow
2. Verificar que aparezca carpeta `.github/workflows/`
3. Verificar que `.github/workflows/ci.yml` esté presente

---

## ✅ PASO 5: Ver el workflow ejecutándose

### 5.1 Ir a la pestaña "Actions"
https://github.com/TU_USERNAME/UrbanFlow/actions

### 5.2 Deberías ver el workflow:
```
CI Spring Boot - UrbanFlow
- Ejecutándose 🔄
- o Completado ✅
- o Fallido ❌
```

---

## ✅ PASO 6: Interpretar resultados

### Si aparece ✅ (Verde)
```
✅ Checkout código
✅ Setup Java JDK
✅ Validar pom.xml
✅ Compilar código
✅ Ejecutar tests unitarios
✅ Empaquetar JAR
✅ Verificar JAR generado
✅ Build completado exitosamente
```

**Significado:** Tu código está listo para producción

### Si aparece ❌ (Rojo)
```
✅ Checkout código
✅ Setup Java JDK
✅ Validar pom.xml
✅ Compilar código
❌ Ejecutar tests unitarios
   ERROR: Test ClienteControladorTest falló
```

**Qué hacer:**
1. Hacer clic en "Ejecutar tests unitarios" para ver detalles
2. Ver qué test falló
3. Arreglarlo localmente: `mvn test`
4. Hacer commit y push: `git push origin develop`
5. El workflow se ejecuta automáticamente de nuevo

---

## ✅ PASO 7: Verificar artefactos generados

### En la ejecución exitosa del workflow:

1. Ir a https://github.com/TU_USERNAME/UrbanFlow/actions
2. Hacer clic en la ejecución exitosa
3. Scrollear abajo y ver "Artifacts"
4. Descargar:
   - `test-reports-java17` → Reportes de tests
   - `urbanflow-jar-java17` → JAR ejecutable

---

## 📋 CHECKLIST DE IMPLEMENTACIÓN

- [ ] Git configurado localmente
- [ ] Repositorio creado en GitHub
- [ ] Código pusheado a GitHub
- [ ] Carpeta `.github/workflows/` visible en GitHub
- [ ] Archivo `ci.yml` presente
- [ ] Primer workflow ejecutado
- [ ] Resultado: ✅ o ❌
- [ ] Si falló, errores corregidos
- [ ] Segundo push completado exitosamente

---

## 🚀 PRÓXIMOS PASOS (OPCIONAL)

Una vez que el CI funcione, pueden agregar:

### 1. Análisis de código con SonarCloud
```yaml
- name: SonarCloud Analysis
  uses: SonarSource/sonarcloud-github-action@master
```

### 2. Docker Build
```yaml
- name: Build Docker image
  run: docker build -t urbanflow:latest .
```

### 3. Despliegue automático
```yaml
- name: Deploy to Azure
  run: |
    az login --service-principal ...
    az webapp up ...
```

---

## 💡 COMANDOS ÚTILES

```bash
# Ver historial de commits
git log --oneline

# Ver estado actual
git status

# Ver ramas
git branch -a

# Cambiar de rama
git checkout develop

# Ver workflows localmente (sin push)
act -l

# Ejecutar workflow específico
act workflow_dispatch

# Descargar artefactos manualmente
gh run download <RUN_ID>
```

---

## ❓ PREGUNTAS FRECUENTES

**P: ¿Cuánto cuesta GitHub Actions?**
- R: Es gratis para repositorios públicos. Repos privados tienen 3,000 minutos/mes gratis.

**P: ¿Puedo ejecutar el workflow manualmente?**
- R: Sí, en la pestaña Actions hay botón "Run workflow"

**P: ¿Qué pasa si el workflow falla?**
- R: GitHub envía email, muestra ❌ en el commit, y podés ver logs detallados

**P: ¿Puedo detener un workflow en progreso?**
- R: Sí, en la página de la ejecución hay botón "Cancel run"

**P: ¿Dónde está el JAR compilado?**
- R: En los "Artifacts" de la ejecución (descargar) o en `target/urbanflow-*.jar`

---

## 📞 SOPORTE

Si el workflow no funciona:

1. Ver los logs en GitHub Actions (pestaña "Actions" → ejecución → logs)
2. Correr `mvn clean test` localmente y comparar errores
3. Verificar que Java 17 esté instalado: `java -version`
4. Verificar que Maven esté en PATH: `mvn -v`
5. Limpiar caché: `mvn clean`

---

**¡Listo! Tu proyecto UrbanFlow ahora tiene CI/CD automático** 🎉
