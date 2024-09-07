<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Detalle de la Factura</title>
</head>
<body>
    <h1>Factura</h1>

    <h2>Descripción: ${factura.descripcion}</h2>
    <p>Número de Factura: ${factura.numeroFactura}</p>

    <h3>Cliente</h3>
    <ul>
        <li>Nombre: ${factura.cliente.nombre}</li>
        <li>Apellidos: ${factura.cliente.apellidos}</li>
        <li>Email: ${factura.cliente.email}</li>
    </ul>

    <h3>Detalles de la Factura</h3>
    <table border="1">
        <thead>
            <tr>
                <th>Producto</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="linea" items="${factura.lineasFactura}">
                <tr>
                    <td>${linea.nombreProducto}</td>
                    <td>${linea.precio}</td>
                    <td>${linea.cantidad}</td>
                    <td>${linea.getImporte()}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>