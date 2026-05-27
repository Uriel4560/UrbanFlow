# Informe Hito 2 (APF2) - UrbanFlow

## Integrantes
| Integrante | Rol |
|------------|-----|
| Christian Rivas Aquino | Diseño BD |
| Uriel Vásquez Quispe | Módulo Productos |
| Renzo Estefano Yupanqui | Gestión Ágil |
| David Jair Mendoza Figueroa | Swagger + Calidad |
| Aldair Ronaldo Casimiro Ramos | Merge + Documentación |

---

## Merge Conflict + Documentación

**Integrante:** Aldair Ronaldo Casimiro Ramos  
**Rama utilizada:** `feature/conflictos`

### Archivos en conflicto

| Archivo | Rama `feature/conflicto-A` | Rama `feature/conflicto-B` |
|---------|---------------------------|---------------------------|
| `config.txt` | version=2.0, puerto=9090 | version=3.0, puerto=7070 |

### Explicación del conflicto

**¿Qué cambios chocaron?**
- Línea 1: `version=2.0` vs `version=3.0`
- Línea 2: `puerto=9090` vs `puerto=7070`

**¿Qué ramas participaron?**
- `feature/conflicto-A`
- `feature/conflicto-B`
- `feature/conflictos`

### Resolución en VS Code

Se utilizó **"Accept Both Changes"** y ajuste manual.

### Conflicto generado

```bash
$ git merge feature/conflicto-B
Auto-merging config.txt
CONFLICT (content): Merge conflict in config.txt
Automatic merge failed; fix conflicts and then commit the result.