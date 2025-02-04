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
public class EliminarServlet extends HttpServlet {

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
                String db = request.getParameter("db") != null ? request.getParameter("db") : "";
                String imgUrl = request.getParameter("db") != null ? request.getParameter("imgUrl") : "";
                if (accion.equals("realizar")) {
                    realiazarEli(id, db, nombre, request, response);
                } else {
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
                                + "<form action=\"EliminarServlet\" method=\"post\">"
                                + "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">"
                                + "<input type=\"hidden\" name=\"db\" value=\"" + db + "\">"
                                + "<input type=\"hidden\" name=\"accion\" value=\"realizar\">"
                                + "<label for='nombre'>Nombre</label>"
                                + "<input type=\"hidden\" id='nombre' name=\"nombre\" value=\"" + nombre + "\"><br>"
                                + "<p>" + nombre + "</p><br>"
                                + "<h3>Seguro quieres eliminar este producto?</h3><br>"
                                + "<button class=\"boton\" type=\"submit\">Eliminar</button>"
                                + "</div></form>"
                        );
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            }
        }
    }

    protected void realiazarEli(String id, String db, String nombre, HttpServletRequest request, HttpServletResponse response)
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
                if (conexionAdmin.eliminar(id, db)) {
                    out.println("<h1>Se ha eliminado correctamente los datos</h1>");
                } else {
                    out.println("<h1>Ocurrio un error y no se pudo realizar la eliminacion</h1>");
                }
                out.println("<p>ID: " + id + "</p>");
                out.println("<p>Base de Datos: " + db + "</p>");
                out.println("<p>Nombre: " + nombre + "</p>");
                out.println("<form action=\"AdminServlet\" method=\"post\"><input type=\"hidden\" name=\"accion\" value=\"eliminar\">"
                        + "<button class=\"boton\" type=\"submit\">Volver a eliminar</button></form>");
            } catch (Exception ex) {
                Logger.getLogger(EliminarServlet.class.getName()).log(Level.SEVERE, null, ex);
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
