package org.CCristian.apiservlet.webapp.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.CCristian.apiservlet.webapp.models.LineaFactura;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ProducerResources {

    @Produces
    public List<LineaFactura> crearLineasFactura() {
        LineaFactura linea1 = new LineaFactura(100, 2, "Producto A");
        LineaFactura linea2 = new LineaFactura(200, 1, "Producto B");
        LineaFactura linea3 = new LineaFactura(150, 3, "Producto C");

        return Arrays.asList(linea1, linea2, linea3);
    }
}
