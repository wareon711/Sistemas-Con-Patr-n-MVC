# Sistemas con Patrón Arquitectónico MVC (Modelo-Vista-Controlador)

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![IDE](https://img.shields.io/badge/IDE-Apache%20NetBeans-blue.svg)
![Architecture](https://img.shields.io/badge/Pattern-MVC%20%2B%20Observer%20%2B%20DAO-brightgreen.svg)
![Tests](https://img.shields.io/badge/Tests-10%20passing%20(JUnit)-success.svg)
![Database](https://img.shields.io/badge/SQLite-JDBC-lightblue.svg)

Repositorio que implementa el **patrón arquitectónico Modelo-Vista-Controlador (MVC)** en Java puro desarrollado en **Apache NetBeans**, demostrando la separación estricta de responsabilidades, la dirección unidireccional de dependencias, la sincronización en tiempo real mediante el **Patrón Observador** y el desacoplamiento de la capa de almacenamiento mediante el **Patrón DAO (Data Access Object)**.

El proyecto incluye dos sistemas independientes con interfaces duales (Consola y GUI Swing con NetBeans GUI Builder):
1. **Sistema de Control de Inventario:** Gestión de existencias, cálculo de valuación y ventas en tiempo real.
2. **Sistema de Gestión de Tareas (To-Do List):** Administración de pendientes con prioridades, límite de saturación y prevención de duplicados.

---

## 🏛️ Arquitectura y Principios de Diseño

### Flujo de Responsabilidades
* **Capa Vista (Presentación):** Representada por `VistaConsola` y `VentanaInventario` / `VentanaTareas`. Captura las acciones del usuario, las envía al controlador y muestra los datos formateados. No toma decisiones de negocio ni valida reglas.
* **Capa Controlador (Coordinación):** Representada por `ControladorInventario` y `ControladorTareas`. Recibe los eventos de las vistas, valida tipos de datos e invoca las operaciones correspondientes en el modelo.
* **Capa Modelo (Reglas de Negocio):** Representada por las entidades (`Producto`, `Tarea`) y los gestores (`Inventario`, `GestorTareas`). Contiene el estado, las validaciones e invariantes del negocio, notificando mutaciones mediante la interfaz `Observador`.
* **Capa de Persistencia (Almacenamiento):** Desacoplada tras las interfaces `ProductoDAO` y `TareaDAO`, permitiendo alternar entre almacenamiento en Memoria, Archivo plano (CSV) o Base de datos relacional (SQLite).

### Reglas Clave de la Arquitectura
* **Aislamiento Total del Modelo:** Ninguna clase del paquete `modelo` importa `javax.swing`, `java.awt`, `Scanner` ni ejecuta `System.out`. El modelo desconoce por completo la tecnología visual empleada.
* **Manejo de Errores por Excepciones Tipadas:** Las reglas de negocio no devuelven códigos de error ni imprimen texto; lanzan excepciones personalizadas (`StockInsuficienteException`, `LimiteTareasException`, `TareaDuplicadaException`, etc.).
* **Dirección Correcta de Dependencias:** La Vista y el Controlador conocen al Modelo, pero el Modelo **jamás** conoce a la Vista ni al Controlador.
* **Sincronización en Vivo (Observer):** Múltiples vistas (terminal y ventana gráfica) pueden correr simultáneamente sobre la misma instancia del modelo y actualizarse de forma reactiva al registrarse cualquier cambio.
* **Persistencia Intercambiable (DAO):** El mecanismo de persistencia (Memoria, Archivo plano o Base de datos relacional SQLite) se intercambia mediante inyección de dependencias en una sola línea de código sin modificar el modelo ni las vistas.

---

## 📦 Sistemas Implementados

### 1. Sistema de Control de Inventario (`com.sistemainventario`)
* **Entidad principal:** `Producto` (Nombre, Cantidad en stock, Precio unitario).
* **Reglas de Negocio:**
  * No se permite vender más unidades de las disponibles en inventario (`StockInsuficienteException`).
  * Validación estricta de precios positivos y cantidades no negativas (`DatoInvalidoException`).
  * Búsqueda segura con manejo de inexistencia (`NoExisteException`).
  * Valuación dinámica del inventario total (`valorTotal = ∑(cantidad * precio)`).
* **Persistencia:** `ProductoDAO` con `DAOMemoria`, `DAOArchivo` (`inventario.csv`) y `DAOSQLite` (`inventario.db`).
* **Vistas:**
  * Consola interactiva (`VistaConsola`).
  * Ventana gráfica Swing (`VentanaInventario` con `TablaProductos`).

### 2. Sistema de Gestión de Tareas (`com.sistematareas`)
* **Entidad principal:** `Tarea` (ID secuencial, Título, Prioridad [Alta/Media/Baja], Estado [Pendiente/Completada]).
* **Reglas de Negocio No Triviales:**
  1. **Límite de saturación:** Máximo 5 tareas pendientes simultáneas (`LimiteTareasException`).
  2. **Prevención de duplicados:** No permite registrar dos tareas pendientes con el mismo título (`TareaDuplicadaException`).
  3. **Control de transición de estado:** Solo tareas en estado `Pendiente` pueden completarse (`EstadoInvalidoException`), liberando un cupo del límite.
* **Persistencia:** `TareaDAO` con `TareaDAOMemoria`, `TareaDAOArchivo` (`tareas.csv`) y `TareaDAOSQLite` (`tareas.db`).
* **Vistas:**
  * Consola interactiva (`VistaConsolaTareas`).
  * Ventana gráfica Swing (`VentanaTareas` con NetBeans Form GUI Builder `.form`).

---

## 🗂️ Estructura del Proyecto en NetBeans

```text
Sistemas/
├── Source Packages/
│   ├── com.sistemainventario/
│   │   ├── App.java                        # Punto de entrada / selección de modo
│   │   ├── modelo/                         # Entidades, Gestor, Excepciones e Interfaces
│   │   │   ├── Producto.java
│   │   │   ├── Inventario.java
│   │   │   ├── ProductoDAO.java
│   │   │   ├── Observador.java
│   │   │   └── *Exception.java
│   │   ├── controlador/
│   │   │   └── ControladorInventario.java
│   │   ├── vista/                          # Presentación desacoplada
│   │   │   ├── Vista.java
│   │   │   ├── VistaConsola.java
│   │   │   ├── VentanaInventario.java
│   │   │   ├── VentanaInventario.form      # Formulario NetBeans GUI Builder
│   │   │   ├── TablaProductos.java
│   │   │   └── Formato.java
│   │   └── persistencia/                   # Implementaciones de almacenamiento
│   │       ├── DAOMemoria.java
│   │       ├── DAOArchivo.java
│   │       └── DAOSQLite.java
│   │
│   └── com.sistematareas/
│       ├── AppTareas.java                  # Punto de entrada / selección de modo
│       ├── modelo/                         # Reglas de negocio puras
│       │   ├── Tarea.java
│       │   ├── GestorTareas.java
│       │   ├── TareaDAO.java
│       │   ├── ObservadorTareas.java
│       │   └── *Exception.java
│       ├── controlador/
│       │   └── ControladorTareas.java
│       ├── vista/
│       │   ├── VistaTareas.java
│       │   ├── VistaConsolaTareas.java
│       │   ├── VentanaTareas.java
│       │   ├── VentanaTareas.form          # Formulario NetBeans GUI Builder
│       │   └── TablaTareas.java
│       └── persistencia/
│           ├── TareaDAOMemoria.java
│           ├── TareaDAOArchivo.java
│           └── TareaDAOSQLite.java
│
└── Test Packages/
    ├── InventarioTest.java                 # Pruebas unitarias de inventario
    └── TareaTest.java                      # Pruebas unitarias de tareas
```

---

## 💻 Ejecución y Pruebas en Apache NetBeans

### 1. Abrir el Proyecto en NetBeans
1. Abrir **Apache NetBeans**.
2. Ir a **File** > **Open Project...** (`Ctrl + Shift + O`).
3. Seleccionar la carpeta del proyecto y hacer clic en **Open Project**.

---

### 2. Ejecutar las Pruebas Unitarias en NetBeans
Para validar las reglas del negocio de ambos sistemas:
* Haz clic derecho sobre el proyecto en la barra de proyectos y selecciona **Test** (o presiona `Alt + F6`).
* También puedes hacer clic derecho directamente sobre `InventarioTest.java` o `TareaTest.java` y elegir **Test File** (`Ctrl + F6`).
* **Resultado:** Se abrirá la pestaña *Test Results* de NetBeans mostrando los 10 tests aprobados en color verde.

---

### 3. Ejecutar el Sistema de Inventario
1. En la pestaña de proyectos, expande `Source Packages` > `com.sistemainventario`.
2. Haz clic derecho sobre **`App.java`** y selecciona **Run File** (`Shift + F6`).
3. Aparecerá un cuadro de diálogo con tres modos de ejecución:
   * **Consola:** Menú interactivo en la pestaña de Output de NetBeans.
   * **Interfaz gráfica:** Ventana de escritorio de Swing.
   * **Ambas:** Ejecuta ambas vistas en simultáneo demostrando el **Patrón Observador** (las ventas o altas en consola actualizan la ventana de inmediato y viceversa).

---

### 4. Ejecutar el Sistema de Tareas (To-Do List)
1. En `Source Packages`, expande `com.sistematareas`.
2. Haz clic derecho sobre **`AppTareas.java`** y selecciona **Run File** (`Shift + F6`).
3. Podrás seleccionar igualmente el modo Consola, Gráfico o Ambos de forma concurrente.

---

## 🧪 Cobertura de Pruebas Unitarias

| Caso de Prueba | Clase Testeada | Objetivo del Negocio |
|---|---|---|
| `venderMasDeLoDisponibleLanzaExcepcionYConservaStock` | `Inventario` | Impide vender si stock < cantidad; asegura que no se descuente nada. |
| `ventaValidaDescuentaCantidadExacta` | `Inventario` | Reduce el stock exactamente en la cantidad vendida. |
| `precioNegativoOCeroLanzaDatoInvalidoException` | `Inventario` | Rechaza precios `<= 0.0`. |
| `valorTotalCalculaCorrectamenteVariosProductos` | `Inventario` | Comprueba sumatoria `∑(cantidad * precio)` con múltiples ítems. |
| `buscarInexistenteLanzaNoExisteException` | `Inventario` | Garantiza manejo de excepciones al buscar productos ausentes. |
| `superarLimiteLanzaLimiteTareasException` | `GestorTareas` | Bloquea el registro de más de 5 tareas pendientes simultáneas. |
| `agregarTareaDuplicadaPendienteLanzaExcepcion` | `GestorTareas` | Rechaza tareas pendientes con títulos repetidos. |
| `completarTareaCambiaEstadoYLiberaCupo` | `GestorTareas` | Valida transición a `Completada` y apertura de nuevo cupo. |
| `completarTareaYaCompletadaLanzaEstadoInvalidoException`| `GestorTareas`| Impide completar dos veces la misma tarea. |
| `eliminarTareaDisminuyeTotalYPermiteReemplazo` | `GestorTareas` | Verifica eliminación física y actualización de cupo. |

---

## 🛠️ Entorno y Tecnologías

* **Entorno de Desarrollo:** Apache NetBeans IDE
* **Lenguaje:** Java 21 (LTS)
* **Diseñador Gráfico:** NetBeans GUI Builder (Matisse / Swing Form)
* **Base de Datos Embebida:** SQLite 3 (`sqlite-jdbc`)
* **Pruebas Unitarias:** JUnit en NetBeans Test Runner