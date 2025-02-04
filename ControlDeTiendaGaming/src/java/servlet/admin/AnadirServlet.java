/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.admin;

import conexiones.Conexion;
import conexiones.ConexionAdmin;
import servlet.ConsolasServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sam
 */
public class AnadirServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("InicioServlet");
        } else {
            Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
            if (!esAdmin) {
                response.sendRedirect("InicioServlet");
            } else {
                response.sendRedirect("AdminServlet");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("InicioServlet");
        } else {
            Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
            if (!esAdmin) {
                response.sendRedirect("InicioServlet");
            } else {
                String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
                String tipo = request.getParameter("tipo") != null ? request.getParameter("tipo") : "";
                if (accion.equals("seleccionar")) {
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        if (tipo.equals("consola")) {
                            out.println(generarFormularioConsola());
                        } else if (tipo.equals("juego")) {
                            out.println(generarFormularioJuego());
                        }
                    }
                } else if (accion.equals("añadir")) {
                    añadir(request, response, tipo);
                }
            }
        }
    }

    public String generarFormularioConsola() {
        String cadena = "";
        cadena += "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Añadir Consola</title>"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "</head>"
                + "<body>"
                + "<div class='formulario'>"
                + "<h3>Añadir Consola</h3>"
                + "<form action=\"AnadirServlet\" method=\"post\">"
                + "<input type=\"hidden\" name=\"accion\" value=\"añadir\">"
                + "<input type=\"hidden\" name=\"tipo\" value=\"consola\">"
                + "<label for='nombre'>Nombre:</label>"
                + "<input type=\"text\" id='nombre' name=\"nombre\" placeholder=\"Ingrese el nombre de la consola\" required><br>"
                + "<label for='potenciaCPU'>Potencia CPU:</label>"
                + "<input type=\"text\" id='potenciaCPU' name=\"potenciaCPU\" placeholder=\"Ingrese la potencia CPU de la consola\" required><br>"
                + "<label for='potenciaGPU'>Potencia GPU:</label>"
                + "<input type=\"text\" id='potenciaGPU' name=\"potenciaGPU\" placeholder=\"Ingrese la potencia GPU del juego\" required><br>"
                + "<label for='compania'>Compañía:</label>"
                + "<input type=\"text\" id='compania' name=\"compania\" placeholder=\"Ingrese la compañía\" required><br>"
                + "<label for='precio'>Precio:</label>"
                + "<input type=\"number\" id='precio' name=\"precio\" placeholder=\"Ingrese el precio\" step=\"0.01\" required><br>"
                + "<label for='unidades'>Unidades:</label>"
                + "<input type=\"number\" id='unidades' name=\"unidades\" placeholder=\"Ingrese la cantidad de unidades\" required><br>"
                + "<label for='imgUrl'>URL de Imagen:</label>"
                + "<input type=\"text\" id='imgUrl' name=\"imgUrl\" placeholder=\"Ingrese la URL de la imagen\" required><br>"
                + "<button class=\"boton\" type=\"submit\">Añadir Consola</button>"
                + "</form>"
                + "</div>"
                + "</body>"
                + "</html>";
        return cadena;
    }

    public String generarFormularioJuego() {
        String cadena = "";
        ConexionAdmin conexionAdmin = null;
        try {
            conexionAdmin = new ConexionAdmin();
            cadena += "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<title>Añadir Juego</title>"
                    + "<link href=\"style.css\" rel=\"stylesheet\">"
                    + "</head>"
                    + "<body>"
                    + "<div class='formulario'>"
                    + "<h3>Añadir Juego</h3>"
                    + "<form action=\"AnadirServlet\" method=\"post\">"
                    + "<input type=\"hidden\" name=\"accion\" value=\"añadir\">"
                    + "<input type=\"hidden\" name=\"tipo\" value=\"juego\">"
                    + "<label for='nombre'>Nombre:</label>"
                    + "<input type=\"text\" id='nombre' name=\"nombre\" placeholder=\"Ingrese el nombre del juego\" required><br>"
                    + "<label for='genero'>Genero:</label>"
                    + "<input type=\"text\" id='genero' name=\"genero\" placeholder=\"Ingrese el genero del juego\" required><br>"
                    + "<label for='puntuacion'>Puntuacion:</label>"
                    + "<input type=\"number\" id='puntuacion' name=\"puntuacion\" placeholder=\"Ingrese la puntuacion del juego\" required><br>"
                    + "<label for='compania'>Compañía:</label>"
                    + "<input type=\"text\" id='compania' name=\"compania\" placeholder=\"Ingrese la compañía\" required><br>"
                    + "<label for='precio'>Precio:</label>"
                    + "<input type=\"number\" id='precio' name=\"precio\" placeholder=\"Ingrese el precio\" step=\"0.01\" required><br>"
                    + "<label for='unidades'>Unidades:</label>"
                    + "<input type=\"number\" id='unidades' name=\"unidades\" placeholder=\"Ingrese la cantidad de unidades\" required><br>"
                    + "<label for='imgUrl'>URL de Imagen:</label>"
                    + "<input type=\"text\" id='imgUrl' name=\"imgUrl\" placeholder=\"Ingrese la URL de la imagen\" required><br>"
                    + "<br><label for='id_consola'>Seleccione la consola:</label>"
                    + "<select name=\"id_consola\" required>"
                    + "<option value=\"0\"> Sin consola</option>";
            // para saber a que ide de consola quiere pertenecer la consola
            String consolasSplit = conexionAdmin.obtenerConsolas();
            String[] consolas = consolasSplit.split("/");
            for (String consola : consolas) {
                String[] datos = consola.split("-");
                cadena += "<option value=\"" + datos[0] + "\">" + datos[1] + "</option>";
                //System.out.println("<option value=\"" + datos[0] + "\">" + datos[1] + "</option>");
            }
            cadena += "</select><br>"
                    + "<button class=\"boton\" type=\"submit\">Añadir Juego</button>"
                    + "</form>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
        } catch (Exception ex) {
            Logger.getLogger(AnadirServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conexionAdmin != null) {
                conexionAdmin.cerrarConexion();
            }
        }
        return cadena;
    }

    public void añadir(HttpServletRequest request, HttpServletResponse response, String tipo)
            throws ServletException, IOException {
        ConexionAdmin conexionAdmin = null;
        Conexion conexion = null;
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String compania = request.getParameter("compania") != null ? request.getParameter("compania") : "";
        String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
        String unidades = request.getParameter("unidades") != null ? request.getParameter("unidades") : "";
        String imgUrl = request.getParameter("imgUrl") != null ? request.getParameter("imgUrl") : "";
        try {
            conexionAdmin = new ConexionAdmin();
            conexion = new Conexion();
            HttpSession session = request.getSession(false);
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print("<!DOCTYPE html>");
                out.print("<html>");
                out.print("<head>");
                out.print("<title>Consola Añadida</title>");
                out.print("<link rel=\"stylesheet\" href=\"style.css\">");
                out.print("</head>");
                out.print("<body>");
                out.println(conexion.crearCabecera(session));
                out.print("<div class='resumen_compra'>");
                if (tipo.equals("consola")) {
                    String potenciaCPU = request.getParameter("potenciaCPU") != null ? request.getParameter("potenciaCPU") : "";
                    String potenciaGPU = request.getParameter("potenciaGPU") != null ? request.getParameter("potenciaGPU") : "";
                    if (conexionAdmin.añadirConsola(nombre, compania, precio, unidades, imgUrl, potenciaCPU, potenciaGPU)) {
                        out.print("<h1>Consola añadida exitosamente</h1>");
                    } else {
                        out.print("<h1>No se pudo añadir la consola por un error</h1>");
                    }
                    out.print("<p><strong>Nombre:</strong> " + nombre + "</p>");
                    out.print("<p><strong>Compañía:</strong> " + compania + "</p>");
                    out.print("<p><strong>Precio:</strong> " + precio + "€</p>");
                    out.print("<p><strong>Unidades:</strong> " + unidades + "</p>");
                    out.print("<p><strong>URL de imagen:</strong> <a href='" + imgUrl + "'>" + imgUrl + "</a></p>");
                    out.print("<p><strong>Potencia CPU:</strong> " + potenciaCPU + "</p>");
                    out.print("<p><strong>Potencia GPU:</strong> " + potenciaGPU + "</p>");
                } else if (tipo.equals("juego")) {
                    String genero = request.getParameter("genero") != null ? request.getParameter("genero") : "";
                    String puntuacion = request.getParameter("puntuacion") != null ? request.getParameter("puntuacion") : "";
                    String id_consola = request.getParameter("id_consola") != null ? request.getParameter("id_consola") : "";
                    if (conexionAdmin.añadirJuego(nombre, compania, precio, unidades, imgUrl, genero, puntuacion, id_consola)) {
                        out.print("<h1>Juego añadido exitosamente</h1>");
                    } else {
                        out.print("<h1>No se pudo añadir el juego por un error</h1>");
                    }
                    out.print("<p><strong>Nombre:</strong> " + nombre + "</p>");
                    out.print("<p><strong>Compañía:</strong> " + compania + "</p>");
                    out.print("<p><strong>Precio:</strong> " + precio + "€</p>");
                    out.print("<p><strong>Unidades:</strong> " + unidades + "</p>");
                    out.print("<p><strong>URL de imagen:</strong> <a href='" + imgUrl + "'>" + imgUrl + "</a></p>");
                    out.print("<p><strong>Género:</strong> " + genero + "</p>");
                    out.print("<p><strong>Puntuación:</strong> " + puntuacion + "</p>");
                    out.print("<p><strong>ID Consola:</strong> " + id_consola + "</p>");
                }
                out.print("</div>");
                out.print("</body>");
                out.print("</html>");
            }
        } catch (Exception ex) {
            Logger.getLogger(ConsolasServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conexionAdmin != null) {
                conexionAdmin.cerrarConexion();
            }
            if (conexion != null) {
                conexion.cerrarConexion();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
