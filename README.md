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

<h1 align="center">Solución del Profesor</h1>

- Clase beans `Cliente`:
```java
package org.aguzman.apiservlet.webapp.factura.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class Cliente {

    private String nombre;

    private String apellido;

    @PostConstruct
    public void inicializar(){
        nombre = "Andres";
        apellido = "Guzman";
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
```

- Clase beans `Factura`:
```java
package org.aguzman.apiservlet.webapp.factura.models;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class Factura {

    @Inject
    private List<LineaFactura> lineas;

    private Cliente cliente;

    private String descripcion;
    
    private Long folio;

    @PostConstruct
    public void inicializar() {
        cliente.setNombre(cliente.getNombre().concat(" ").concat("José"));
        descripcion = "Factura Oficina".concat(" del cliente: ").concat(cliente.getNombre());
        folio = Math.round( Math.random() * 1000000000)+10;
    }

    @PreDestroy
    public void destruir() {
        System.out.println("Factura destruida: ".concat(descripcion));
    }

    public List<LineaFactura> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaFactura> lineas) {
        this.lineas = lineas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Inject
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }
}
```

- Clase `LineaFactura`:
```java
package org.aguzman.apiservlet.webapp.factura.models;

public class LineaFactura {

    private Producto producto;
    private Integer cantidad;

    public LineaFactura(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Integer calcularImporte() {
        return cantidad * producto.getPrecio();
    }
}
```

- Clase `Producto`:
```java
package org.aguzman.apiservlet.webapp.factura.models;

public class Producto {

    private String nombre;
    private Integer precio;

    public Producto(String nombre, Integer precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}
```

- Clase producer CDI `ProducerResources`:
```java
package org.aguzman.apiservlet.webapp.factura.configs;

import org.aguzman.apiservlet.webapp.factura.models.Producto;
import org.aguzman.apiservlet.webapp.factura.models.LineaFactura;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ProducerResources {

    @Produces
    private List<LineaFactura> beanLineas() {
        Producto producto1 = new Producto("Monitor LG LCD 24", 250);
        Producto producto2 = new Producto("Notebook Asus", 500);
        Producto producto3 = new Producto("Impresora HP Multifuncional", 80);
        Producto producto4 = new Producto("Escritorio Oficina", 300);

        LineaFactura linea1 = new LineaFactura(producto1, 2);
        LineaFactura linea2 = new LineaFactura(producto2, 1);
        LineaFactura linea3 = new LineaFactura(producto3, 3);
        LineaFactura linea4 = new LineaFactura(producto4, 1);

        return Arrays.asList(linea1, linea2, linea3, linea4);
    }
}
```

- Clase servlet `FacturaController`:
```java
package org.aguzman.apiservlet.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.aguzman.apiservlet.webapp.factura.models.Factura;

import java.io.IOException;

@WebServlet({"/factura", "/"})
public class ProductoServlet extends HttpServlet {

    @Inject
    private Factura factura;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("factura", factura);
        req.setAttribute("title", "Ejemplo Factura con inyección de dependencia");

        getServletContext().getRequestDispatcher("/factura.jsp").forward(req, resp);
    }
}
```

- Vista `factura.jsp`:
```jsp
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="layout/header.jsp" />

<h3>${title}</h3>

<ul class="list-group">
    <li class="list-group-item active">Factura: ${factura.folio}</li>
    <li class="list-group-item">${factura.descripcion}</li>
    <li class="list-group-item">${factura.cliente.nombre} ${factura.cliente.apellido}</li>
</ul>

<table class="table table-hover table-striped">
    <thead>
        <tr>
            <th>Producto</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Total</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${factura.lineas}" var="linea">
        <tr>
            <td>${linea.producto.nombre}</td>
            <td>${linea.producto.precio}</td>
            <td>${linea.cantidad}</td>
            <td>${linea.calcularImporte()}</td>
        </tr>
    </c:forEach>
</tbody>
</table>
<jsp:include page="layout/footer.jsp" />
```

- Archivo `beans.xml`:
```xml
<beans xmlns="https://jakarta.ee/xml/ns/jakartaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/beans_3_0.xsd"
       version="3.0" bean-discovery-mode="annotated">

</beans>
```

- `pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.aguzman.apiservlet.webapp.producesfactura.tarea15</groupId>
    <artifactId>webapp-produces-factura-tarea15</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>
    <properties>
        <maven.compiler.source>16</maven.compiler.source>
        <maven.compiler.target>16</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>
    <dependencies>
        <dependency>
            <groupId>jakarta.platform</groupId>
            <artifactId>jakarta.jakartaee-api</artifactId>
            <version>9.0.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.3</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
            <version>2.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.weld.servlet</groupId>
            <artifactId>weld-servlet-core</artifactId>
            <version>4.0.1.SP1</version>
        </dependency>
    </dependencies>
    <build>
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
            </plugin>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <url>http://localhost:8080/manager/text</url>
                    <username>admin</username>
                    <password>12345</password>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.2.3</version>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```
