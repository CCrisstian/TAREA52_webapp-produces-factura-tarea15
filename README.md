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

