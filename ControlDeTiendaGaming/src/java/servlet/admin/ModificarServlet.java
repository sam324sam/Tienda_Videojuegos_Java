/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet.admin;

import conexiones.Conexion;
import conexiones.ConexionAdmin;
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
public class ModificarServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
                String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
                String id = request.getParameter("id_producto") != null ? request.getParameter("id_producto") : "";
                String id_consola = request.getParameter("id_consola") != null ? request.getParameter("id_consola") : "";
                String db = request.getParameter("db") != null ? request.getParameter("db") : "";
                String compania = request.getParameter("compania") != null ? request.getParameter("compania") : "";
                String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
                String unidades = request.getParameter("unidades") != null ? request.getParameter("unidades") : "";
                String imgUrl = request.getParameter("db") != null ? request.getParameter("imgUrl") : "";
                if (accion.equals("realizar")) {
                    String dato1 = request.getParameter("dato1") != null ? request.getParameter("dato1") : "";
                    String dato2 = request.getParameter("dato2") != null ? request.getParameter("dato2") : "";
                    realiazarMod(id, db, nombre, compania, precio, unidades, imgUrl, dato1, dato2, id_consola, request, response);
                } else {
                    String cadena = "";
                    if (db.equals("juegos")) {
                        String dato1 = request.getParameter("genero") != null ? request.getParameter("genero") : "";
                        String dato2 = request.getParameter("puntuacion") != null ? request.getParameter("puntuacion") : "";
                        cadena += "<label for='dato1'>Género:</label>";
                        cadena += "<input type=\"text\" id='dato1' name=\"dato1\" value=\"" + dato1 + "\"><br>";
                        cadena += "<label for='dato2'>Puntuación:</label>";
                        cadena += "<input type=\"number\" id='dato2' name=\"dato2\" value=\"" + dato2 + "\"><br>";
                        cadena += obtenerConsolas(id_consola);
                    } else if (db.equals("consolas")) {
                        String dato1 = request.getParameter("potenciaCPU") != null ? request.getParameter("potenciaCPU") : "";
                        String dato2 = request.getParameter("potenciaGPU") != null ? request.getParameter("potenciaGPU") : "";
                        cadena += "<label for='dato1'>Potencia CPU:</label>";
                        cadena += "<input type=\"text\" id='dato1' name=\"dato1\" value=\"" + dato1 + "\"><br>";
                        cadena += "<label for='dato2'>Potencia GPU:</label>";
                        cadena += "<input type=\"text\" id='dato2' name=\"dato2\" value=\"" + dato2 + "\"><br>";
                    }
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div class='formulario'>"
                                + "<img src='" + imgUrl + "'>"
                                + "<form action=\"ModificarServlet\" method=\"post\">"
                                + "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">"
                                + "<input type=\"hidden\" name=\"db\" value=\"" + db + "\">"
                                + "<input type=\"hidden\" name=\"accion\" value=\"realizar\">"
                                + "<label for='nombre'>Nombre:</label>"
                                + "<input type=\"text\" id='nombre' name=\"nombre\" value=\"" + nombre + "\"><br>"
                        );
                        out.println(cadena);
                        out.println("<label for='compania'>Compañía:</label>"
                                + "<input type=\"text\" id='compania' name=\"compania\" value=\"" + compania + "\"><br>"
                                + "<label for='precio'>Precio:</label>"
                                + "<input type=\"number\" id='precio' name=\"precio\" value=\"" + precio + "\"><br>"
                                + "<label for='unidades'>Unidades:</label>"
                                + "<input type=\"number\" id='unidades' name=\"unidades\" value=\"" + unidades + "\"><br>"
                                + "<label for='imgUrl'>URL de Imagen:</label>"
                                + "<input type=\"text\" id='imgUrl' name=\"imgUrl\" value=\"" + imgUrl + "\"><br>"
                                + "<button class=\"boton\" type=\"submit\">Modificar</button>"
                                + "</div></form>"
                        );
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            }
        }
    }

    protected String obtenerConsolas(String id_consola) {
        String cadena = "<br><label for='id_consola'>Pertenece a la consola:</label><select name=\"id_consola\" required>"
                + "<option value=\"0\"> Sin consola</option>";
        ConexionAdmin conexionAdmin = null;
        try {
            conexionAdmin = new ConexionAdmin();
            String consolasSplit = conexionAdmin.obtenerConsolas();
            String[] consolas = consolasSplit.split("/");
            for (String consola : consolas) {
                String[] datos = consola.split("-");
                if (datos[0].equals(id_consola)) {
                    cadena += "<option value=\"" + datos[0] + "\" selected>" + datos[1] + "</option>";
                } else {
                    cadena += "<option value=\"" + datos[0] + "\">" + datos[1] + "</option>";
                }
            }
            cadena += "</select><br>";
        } catch (Exception ex) {
            Logger.getLogger(ModificarServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conexionAdmin != null) {
                conexionAdmin.cerrarConexion();
            }
        }
        return cadena;
    }

    protected void realiazarMod(String id, String db, String nombre, String compania, String precio, String unidades, String imgUrl, String dato1, String dato2, String id_consola, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ConexionAdmin conexionAdmin = null;
        Conexion conexion = null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            try {
                conexionAdmin = new ConexionAdmin();
                conexion = new Conexion();
                HttpSession session = request.getSession(false);
                out.println(conexion.crearCabecera(session));
                out.println("<div class='resumen_compra'>");
                if (conexionAdmin.modificar(id, db, nombre, compania, precio, unidades, imgUrl, dato1, dato2, id_consola)) {

                    out.println("<h1>Se ha modificado correctamente los datos</h1>");
                } else {
                    out.println("<h1>Ocurrio un error y no se pudo realizar la modificacion</h1>");
                }
                out.println("<p>ID: " + id + "</p>");
                out.println("<p>Base de Datos: " + db + "</p>");
                out.println("<p>Nombre: " + nombre + "</p>");
                out.println("<p>Compañía: " + compania + "</p>");
                out.println("<p>Precio: " + precio + "</p>");
                out.println("<p>Unidades: " + unidades + "</p>");
                out.println("<p>Imagen URL: " + imgUrl + "</p>");
                out.println("<p>Dato1: " + dato1 + "</p>");
                out.println("<p>Dato2: " + dato2 + "</p>");
                if (!id_consola.equals("")) {
                    out.println("<p>Id consola: " + id_consola + "</p>");
                }
            } catch (Exception ex) {
                Logger.getLogger(ModificarServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexionAdmin != null) {
                    conexionAdmin.cerrarConexion();
                }
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
