# Informe formal del módulo Productos REST

## 1. Introducción

El presente documento presenta una evaluación formal del módulo de **Productos REST** desarrollado en el proyecto **UrbanFlow**. El objetivo es determinar el grado de conformidad del trabajo realizado respecto al alcance solicitado y contrastarlo con la implementación real del código fuente.

## 2. Alcance evaluado

Se evaluó el desarrollo del módulo de productos con énfasis en:

- la existencia de las capas principales del backend;
- la implementación de las operaciones CRUD;
- la ruta expuesta por la API;
- la verificación de compilación del proyecto;
- la coherencia del informe con el estado real del código.

## 3. Verificación del código fuente

La implementación real del módulo se encuentra en las siguientes rutas:

- `src/main/java/com/ventas/ropa/sistema/controlador/ProductoControlador.java`
- `src/main/java/com/ventas/ropa/sistema/servicio/ProductoServicio.java`
- `src/main/java/com/ventas/ropa/sistema/repositorio/ProductoRepository.java`
- `src/main/java/com/ventas/ropa/sistema/modelo/Producto.java`

## 4. Evaluación de conformidad

### 4.1 Estructura del módulo

La estructura solicitada para el módulo fue equivalente a:

- `controllers/ProductoController.java`
- `services/ProductoService.java`
- `repositories/ProductoRepository.java`
- `models/Producto.java`

En la implementación actual del proyecto, los archivos se encuentran bajo las siguientes rutas:

- `controlador/ProductoControlador.java`
- `servicio/ProductoServicio.java`
- `repositorio/ProductoRepository.java`
- `modelo/Producto.java`

**Conclusión:** el módulo sí existe y contiene las capas requeridas; sin embargo, **la estructura exacta de directorios no coincide con la nomenclatura solicitada**.

### 4.2 Operaciones CRUD

El controlador `ProductoControlador` implementa las siguientes operaciones:

- `GET /api/productos`
- `GET /api/productos/{id}`
- `POST /api/productos`
- `PUT /api/productos/{id}`
- `DELETE /api/productos/{id}`

El servicio `ProductoServicio` expone los métodos necesarios para:

- listar productos;
- obtener un producto por identificador;
- guardar productos;
- eliminar productos.

El repositorio `ProductoRepository` extiende `JpaRepository<Producto, Long>`, lo que habilita el acceso a datos mediante JPA.

**Conclusión:** el módulo cumple con la funcionalidad CRUD requerida.

### 4.3 Ruta expuesta por la API

La ruta real implementada por el código es:

- `/api/productos`

La ruta solicitada en el alcance fue:

- `/productos`

**Conclusión:** la funcionalidad está implementada, pero **la ruta base no coincide exactamente con la solicitada**.

## 5. Validación técnica

La compilación del proyecto fue verificada mediante el siguiente comando:

```bash
./mvnw -q -DskipTests compile
```

Salida verificada:

```text
EXIT:0
```

Esto confirma que la aplicación **compila correctamente** en el estado actual del repositorio.

## 6. Observaciones adicionales

Se identifican las siguientes observaciones relevantes:

1. El módulo implementa correctamente el CRUD funcional.
2. La ruta expuesta por la API utiliza el prefijo `/api`, lo cual difiere del alcance inicial.
3. El controlador valida campos obligatorios en la creación de productos.
4. El endpoint `DELETE` devuelve `204 No Content` cuando la eliminación es exitosa.
5. No se evidenció la ejecución de pruebas Postman ni su registro como evidencia específica en el informe.
6. El `POST` devuelve `200 OK` en lugar de `201 Created`, aunque la operación es funcional.
7. El `PUT` actualiza únicamente algunos campos del modelo, no todos los atributos disponibles.
8. No se observó una cobertura de pruebas específica del módulo de productos en `src/test/java`.

## 7. Conclusión

El módulo de productos **sí cumple con el requisito funcional de CRUD**, y el proyecto **se encuentra en estado compilable**. No obstante, **no cumple de forma exacta con el alcance inicial** en dos aspectos principales:

- la **ruta base** expuesta por la API (`/api/productos` vs `/productos`);
- la **nomenclatura exacta de directorios** (`controlador`, `servicio`, `repositorio`, `modelo` vs `controllers`, `services`, `repositories`, `models`).

Por lo tanto, el informe debe describir el estado como **cumplimiento funcional parcial** o **cumplimiento funcional con desviaciones respecto al alcance solicitado**.

## 8. Recomendación para el informe final

Se recomienda redactar el informe en términos formales y precisos, con el siguiente enfoque:

- indicar que el **CRUD está implementado**;
- mencionar que **la ruta real es `/api/productos`**;
- registrar que **el proyecto compila correctamente**;
- señalar que **no existen evidencias verificadas de pruebas Postman**;
- destacar las desviaciones respecto al alcance inicial de forma objetiva.

## 9. Redacción sugerida para el informe

> El módulo de Productos REST fue evaluado en el proyecto UrbanFlow. Se verificó que la aplicación contiene las capas `controlador`, `servicio`, `repositorio` y `modelo`, y que el CRUD está implementado mediante los endpoints `GET /api/productos`, `GET /api/productos/{id}`, `POST /api/productos`, `PUT /api/productos/{id}` y `DELETE /api/productos/{id}`. La compilación del proyecto fue verificada con `./mvnw -q -DskipTests compile`, obteniendo `EXIT:0`. No obstante, el alcance real difiere del solicitado en dos aspectos: la ruta expuesta utiliza el prefijo `/api`, y la estructura de carpetas utiliza los nombres `controlador`, `servicio`, `repositorio` y `modelo` en lugar de `controllers`, `services`, `repositories` y `models`. Además, no se registró evidencia verificable de pruebas Postman.

## 10. Observación final

El informe puede considerarse **válido y formal** si se presenta como una **evaluación objetiva del cumplimiento real del código**, en lugar de afirmar una conformidad total con el alcance solicitado.

- Crear producto con `codigo` vacío
- Crear producto con `precio <= 0`
- Actualizar producto inexistente
- Eliminar producto inexistente
- Consultar lista cuando exista data inicial

---

## 9. Conclusión

El informe **sí puede ser válido**, pero **debe corregirse para reflejar el comportamiento real del programa**.

### Veredicto final

- **Sí cumple en funcionalidad**
- **No cumple al 100% en ruta y estructura**
- **No debe afirmar pruebas Postman si no fueron ejecutadas**

Si quieres, puedo convertir este documento en una versión **más formal para entregar** o **ajustarlo a tu estilo exacto de informe**.
