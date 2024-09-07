<h1 align="center">TAREA 52: Crear proyecto Produces Factura usando Anotaciones</h1>
<h2>Instrucciones de tareas</h2>
<p>El objetivo de la tarea consiste en crear un proyecto Servlet y CDI (Java EE9) usando anotaciones con los siguientes requerimientos:</p>

- El nombre del proyecto debe ser webapp-produces-factura-tarea15
- Crear una clase componente Cliente (anotado con algún contexto como @RequestScoped) con los atributos nombre: String, email: String y apellidos: String, los valores los pueden definir dentro de la misma clase por defecto usando @PostConstruct.
- Crear una clase componente Factura (anotado con @Named y @RequestScoped) la cual debe tener un atributo del tipo Cliente con sus getter y setters, por lo tanto se tiene que asignar/inyectar el Cliente a la Factura usando método setter. Además el componente Factura debe tener algunos atributos más a parte del cliente: el folio o numeroFactura del tipo Integer (número de factura), además un nombre (o descripción) del tipo String y lineasFactura del tipo List<LineaFactura> (que es el detalle o líneas de la factura). No olvidar sus respectivos getters y setters. Los valores del folio y descripcion lo pueden agregar usando un método @PostConstruct.
- Crear una clase LineaFactura debe tener los siguientes atributos: el precio: Integer, cantidad: Integer y nombre producto: String. No olvidar sus respectivos getters y setters.
- Crear una clase ProducerResources (anotada con @ApplicationScoped) y con un método @Produces en donde se tiene que crear (instanciar con operador new) las líneas de la Factura, es decir un List de LineaFactura (2 o 3) con datos de ejemplos de productos.
- Usar @Produces para registrar un List<LineaFactura> en el contenedor CDI, luego inyectar las líneas en el componente Factura.
- En la vista factura.jsp mostrar la factura con su descripción, número de folio, sus líneas o detalles y el cliente asociado a la factura.
- Opcionalmente estilos css con bootstrap en la vista.

<h2> Resumen Respuesta</h2>

- Clase `Cliente`
  - <b>Rol</b>: Representa al cliente que está asociado con una factura.
  - <b>Contexto CDI</b>: `@RequestScoped`, lo que significa que una nueva instancia de `Cliente` será creada para cada solicitud HTTP.
  - <b>Propósito</b>:
    - Contiene información básica del cliente, como nombre, email y apellidos.
    - Inicializa estos atributos con valores predeterminados usando el método `@PostConstruct`.
    - Los valores predeterminados se definen para simplificar la configuración inicial en el contexto de una solicitud.

- Clase `Factura`
  - <b>Rol</b>: Representa una factura completa, que incluye el cliente y las líneas de factura.
  - <b>Contexto CDI</b>: `@Named` y `@RequestScoped`.
  - <b>Propósito</b>:
    - Contiene un atributo `Cliente` para asociar la factura con un cliente específico.
    - Contiene una lista de `LineaFactura` que representa las diferentes líneas de productos en la factura.
    - Inicializa el número de factura y la descripción con valores predeterminados usando el método `@PostConstruct`.
    - Los atributos numeroFactura y descripcion se configuran por defecto para simplificar la presentación de la factura.

- Clase `LineaFactura`
  - <b>Rol</b>: Representa una línea individual en una factura.
  - <b>Propósito</b>:
    - Define los detalles de un producto en la factura, incluyendo el precio, la cantidad y el nombre del producto.
    - Tiene un método getImporte() que calcula el total para esa línea (cantidad * precio).

- Clase `ProducerResources`
  - <b>Rol</b>: Proveedor de recursos CDI.
  - <b>Contexto CDI</b>: `@ApplicationScoped`, lo que significa que una única instancia de ProducerResources es creada para toda la aplicación.
  - <b>Propósito</b>:
    - Produce una lista de `LineaFactura` que se utiliza en el componente Factura.
    - El método `@Produces` crea instancias de `LineaFactura` con datos de ejemplo y las proporciona a los componentes CDI que lo requieran.

- Servlet `FacturaController`
  - <b>Rol</b>: Controlador que maneja las solicitudes HTTP relacionadas con la factura.
  - <b>Propósito</b>:
    - Inyecta la instancia de `Factura` usando CDI.
    - Establece la `Factura` como un atributo de la solicitud.
    - Redirige a la vista `factura.jsp` para mostrar la información de la factura.
    - El servlet actúa como intermediario entre el modelo (datos) y la vista (presentación).

- Vista `factura.jsp`
  - <b>Rol</b>: Página JSP que muestra la información de la factura.
  - <b>Propósito</b>:
    - Usa expresiones JSP y JSTL (<c:forEach>) para iterar sobre la lista de LineaFactura y mostrar los detalles de cada línea en una tabla HTML.
    - Muestra la descripción de la factura, el número de factura y la información del cliente.
    - Actúa como la capa de presentación que se encarga de renderizar los datos que han sido preparados por el servlet.

<h2>Resumen del Flujo</h2>

- <b>Solicitud HTTP</b>: El cliente realiza una solicitud HTTP al servlet `/factura`.
- <b>Servlet</b> `FacturaController`:
  - Inyecta y crea una instancia de `Factura` (con cliente y líneas de factura).
  - Establece el objeto `Factura` como atributo de la solicitud.
  - Redirige la solicitud a la vista `factura.jsp`.
- <b>Vista</b> `factura.jsp`:
  - Muestra los detalles de la factura, incluyendo la descripción, el número de factura, el cliente y las líneas de la factura.
  - Utiliza JSTL para iterar sobre las líneas de factura y calcular el total para cada línea.
