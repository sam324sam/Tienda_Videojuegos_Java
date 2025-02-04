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
public class AdminServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Conexion conexion = null;
            ConexionAdmin conexionAdmin = null;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                response.sendRedirect("InicioServlet");
            } else {
                Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
                if (!esAdmin) {
                    response.sendRedirect("InicioServlet");
                }
            }
            try {
                conexion = new Conexion();
                conexionAdmin = new ConexionAdmin();
                out.println(conexion.crearCabecera(session));
                // Acciones de admin
                out.println("<div class='formulario'>");
                out.println("<h1>Panel de Administración</h1>");
                out.println("<p>Seleccione la acción que desea realizar:</p>");

                out.println("<form action='AdminServlet' method='POST'>");
                out.println("<input type='hidden' name='accion' value='añadir'>");
                out.println("<button type='submit'>Añadir Producto</button>");
                out.println("</form>");

                out.println("<form action='AdminServlet' method='POST'>");
                out.println("<input type='hidden' name='accion' value='modificar'>");
                out.println("<button type='submit'>Modificar Producto</button>");
                out.println("</form>");

                out.println("<form action='AdminServlet' method='POST'>");
                out.println("<input type='hidden' name='accion' value='eliminar'>");
                out.println("<button type='submit'>Eliminar Producto</button>");
                out.println("</form>");

                out.println("</div>");

            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
                if (conexionAdmin != null) {
                    conexionAdmin.cerrarConexion();
                }
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("InicioServlet");
        } else {
            String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
            String script = """
                        <script>
                                function mostrar_info(id, boton) {
                                    var informacion = document.getElementById(id);
                                    if (informacion.style.opacity == "" || informacion.style.opacity == "0") {
                                        informacion.style.opacity = "1";
                                        informacion.style.height = "110px";
                                        informacion.style.min_height = "160px";
                                        boton.innerHTML = "Ver menos informacion";
                                        console.log("enseñar");
                                    } else {
                                        informacion.style.opacity = "0";
                                        informacion.style.height = "0px";
                                        informacion.style.min_height = "0px";
                                        boton.innerHTML = "Ver mas informacion";
                                        console.log("ocultar");
                                    }
                                }
                            </script>
                        """;
            switch (accion) {
                case "eliminar" -> {
                    mostrarEliminar(request, response, script);
                }
                case "añadir" -> {
                    mostrarAñadir(request, response);
                }
                case "modificar" -> {
                    mostrarModificar(request, response, script);
                }
                default ->
                    response.sendRedirect("InicioServlet");
            }
        }
    }

    public void mostrarAñadir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Añadir Producto</title>");
            out.println("<link href=\"style.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='formulario'>"
                    + "<form action=\"AnadirServlet\" method=\"post\">"
                    + "<input type=\"hidden\" name=\"accion\" value=\"seleccionar\">"
                    + "<h2>Seleccione el tipo de producto a añadir:</h2>"
                    + "<label for='tipo'>Tipo de Producto:</label>"
                    + "<select id='tipo' name=\"tipo\" required>"
                    + "<option value=\"\" disabled selected>Seleccione una opción</option>"
                    + "<option value=\"consola\">Consola</option>"
                    + "<option value=\"juego\">Juego</option>"
                    + "</select><br>"
                    + "<button class=\"boton\" type=\"submit\">Siguiente</button>"
                    + "</form>"
                    + "</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public void mostrarEliminar(HttpServletRequest request, HttpServletResponse response, String script)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Conexion conexion = null;
            ConexionAdmin conexionAdmin = null;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                response.sendRedirect("InicioServlet");
            } else {
                Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
                if (!esAdmin) {
                    response.sendRedirect("InicioServlet");
                }
            }
            try {
                conexion = new Conexion();
                conexionAdmin = new ConexionAdmin();
                out.println(conexion.crearCabecera(session));
                out.println(conexionAdmin.mostrarEliminar());
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
                if (conexionAdmin != null) {
                    conexionAdmin.cerrarConexion();
                }
            }
            out.println(script);
            out.println("</body>");
            out.println("</html>");
        }
    }

    public void mostrarModificar(HttpServletRequest request, HttpServletResponse response, String script)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Conexion conexion = null;
            ConexionAdmin conexionAdmin = null;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                response.sendRedirect("InicioServlet");
            } else {
                Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
                if (!esAdmin) {
                    response.sendRedirect("InicioServlet");
                }
            }
            try {
                conexion = new Conexion();
                conexionAdmin = new ConexionAdmin();
                out.println(conexion.crearCabecera(session));
                out.println(conexionAdmin.mostrarModificar());
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
                if (conexionAdmin != null) {
                    conexionAdmin.cerrarConexion();
                }
            }
            out.println(script);
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
