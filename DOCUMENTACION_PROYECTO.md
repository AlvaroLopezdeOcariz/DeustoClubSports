# Documentación del Proyecto: DeustoClubSports

## 1. Idea Detallada del Proyecto

**DeustoClubSports** es una aplicación de escritorio desarrollada en Java que tiene como objetivo gestionar las actividades y servicios de un club deportivo. La aplicación está diseñada para facilitar tanto a los usuarios (socios) como a los administradores la gestión de las instalaciones, compras y reservas.

El sistema permite a los usuarios:
- **Registrarse e Iniciar Sesión**: Acceso seguro mediante credenciales.
- **Reservar Instalaciones**: Visualizar pistas deportivas disponibles y realizar reservas.
- **Tienda y Cafetería**: Comprar equipamiento deportivo y productos de cafetería.
- **Gestión de Compras**: Añadir productos a un carrito y procesar el pago.
- **Gestión de Cuotas**: Realizar el pago de las cuotas de socio.

Para los administradores, el sistema ofrece una interfaz dedicada (`VentanaAdministrador`) para gestionar usuarios, reservas y el inventario de productos.

## 2. Planificación Inicial (Gantt)

A continuación se presenta una planificación inicial del proyecto dividida por fases.

```mermaid
gantt
    title Planificación DeustoClubSports
    dateFormat  YYYY-MM-DD
    axisFormat  %d/%m

    section Fase 1: Análisis y Diseño
    Definición de Requisitos       :done, a1, 2025-12-01, 3d
    Diseño de Arquitectura (UML)   :active, a2, after a1, 4d
    Diseño de Interfaces (Mockups) :active, a3, after a1, 5d

    section Fase 2: Implementación Dominio
    Clases Modelo (Usuario, Producto) :crit, d1, 2025-12-08, 5d
    Gestión de Datos (Ficheros/BD)    :d2, after d1, 5d

    section Fase 3: Interfaz de Usuario
    Ventanas Principales (Inicio, Principal) :u1, 2025-12-15, 5d
    Ventanas Específicas (Tienda, Reserva)   :u2, after u1, 7d
    Integración Lógica-UI                    :u3, after u2, 7d

    section Fase 4: Lógica Avanzada
    Sistema de Carrito y Pagos     :l1, 2026-01-05, 7d
    Gestión de Hilos y Concurrencia:l2, 2026-01-10, 5d

    section Fase 5: Pruebas y Entrega
    Pruebas Unitarias y Sistema    :t1, 2026-01-15, 5d
    Documentación Final            :t2, after t1, 3d
```

## 3. Funcionalidades Destacadas

1.  **Géstión de Usuarios y Seguridad**:
    *   Registro de nuevos usuarios con validación de datos.
    *   Login seguro para usuarios y administradores.
    *   Perfil de usuario editable.

2.  **Sistema de Reservas**:
    *   Selección de tipo de deporte (Fútbol, Baloncesto, Tenis, etc.).
    *   Visualización de horarios disponibles.
    *   Confirmación de reserva vinculada al usuario.

3.  **E-commerce (Tienda y Cafetería)**:
    *   Catálogo de productos (Ropa, Accesorios, Comida, Bebida).
    *   **Carrito de Compra**: Permite acumular productos (`ItemCarrito`) y calcular el total.
    *   Proceso de checkout simulado.

4.  **Panel de Administración**:
    *   Control total sobre los datos de la aplicación (Usuarios, Productos).
    *   Capacidad para dar de baja usuarios o cancelar reservas.

## 4. Boceto de Interfaces

La navegación de la aplicación sigue una estructura jerárquica a partir de una ventana principal.

### Flujo de Navegación

```mermaid
graph TD
    Login(VentanaInicio) -->|Credenciales OK| Principal(VentanaPrincipal)
    Login -->|Nuevo Usuario| Registro(Registro)
    Registro --> Login

    Principal -->|Reservar| Instalaciones(VentanaInstalaciones)
    Principal -->|Comprar| Tienda(VentanaTienda)
    Principal -->|Comer| Cafeteria(VentanaCafeteria)
    Principal -->|Mi Cuenta| Perfil[Gestión Perfil/Pagos]
    Principal -->|Admin| Admin(VentanaAdministrador)

    Instalaciones -->|Seleccionar| Reserva(VentanaReserva)
    
    Tienda -->|Seleccionar Producto| Detalle(VentanaCompraProducto)
    Tienda -->|Ver Carrito| Carrito(VentanaCarrito)
    
    Cafeteria -->|Ver Carrito| Carrito
    
    Carrito -->|Pagar| Pasarela[Proceso de Pago]
```

### Descripción de Ventanas Clave

*   **VentanaInicio**: Pantalla de bienvenida con campos para usuario y contraseña. Botón para ir a `Registro`.
*   **VentanaPrincipal**: Dashboard central con botones grandes o un menú para acceder a las secciones principales (Instalaciones, Tienda, Cafetería).
*   **VentanaInstalaciones**: Muestra una lista o rejilla con las instalaciones disponibles. Filtros por deporte.
*   **VentanaReserva**: Formulario para seleccionar fecha y hora. Muestra confirmación.
*   **VentanaTienda / VentanaCafeteria**: Catálogo visual con imágenes de productos, nombre y precio.
*   **VentanaCarrito**: Tabla resumen de productos seleccionados, cantidades y precio total. Botón para finalizar compra.
