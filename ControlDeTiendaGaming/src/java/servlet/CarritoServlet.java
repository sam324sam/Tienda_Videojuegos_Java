package servlet;

import conexiones.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarritoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Conexion conexion = null;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect("InicioServlet");
            } else {
                try {
                    conexion = new Conexion();
                    out.println(conexion.crearCabecera(session));
                    out.println(conexion.mostrarCarrito(session));
                } catch (Exception ex) {
                    Logger.getLogger(ConsolasServlet.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (conexion != null) {
                        conexion.cerrarConexion();
                    }
                }
            }
            out.println("</body>");
            out.println("</html>");
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
        Conexion conexion = null;
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("InicioServlet");
        } else {
            String id_usuario = (int) session.getAttribute("id") + "";
            String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";
            switch (accion) {
                case "eliminar" -> {
                    String id_cesta = request.getParameter("id_cesta") != null ? request.getParameter("id_cesta") : "";
                    try {
                        conexion = new Conexion();
                        if (conexion.eliminarCarrito(id_cesta, id_usuario)) {
                            System.out.println("Eliminado de tu cesta");
                        } else {
                            System.out.println("A ocurrido un error ");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(CarritoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (conexion != null) {
                            conexion.cerrarConexion();
                        }
                    }
                    response.sendRedirect("CarritoServlet");
                }
                case "añadir" -> {
                    String id_producto = request.getParameter("id_producto") != null ? request.getParameter("id_producto") : "";
                    String db = request.getParameter("db") != null ? request.getParameter("db") : "";
                    String pagina = (String) session.getAttribute("paginaAnterior") + "";
                    System.out.println(id_producto + " " + id_usuario + " " + db + " " + pagina);
                    try {
                        conexion = new Conexion();
                        conexion.añadirCarrito(db, id_producto, 1, id_usuario);
                    } catch (Exception ex) {
                        Logger.getLogger(CarritoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (conexion != null) {
                            conexion.cerrarConexion();
                        }
                    }
                    response.sendRedirect(pagina);
                }
                case "comprar" -> {
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
                        out.println("</head>");
                        out.println("<body>");
                        try {
                            conexion = new Conexion();
                            out.println(conexion.crearCabecera(session));
                            out.println(conexion.comprarCarrito(id_usuario));
                        } catch (Exception ex) {
                            Logger.getLogger(ConsolasServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            if (conexion != null) {
                                conexion.cerrarConexion();
                            }
                        }
                        out.println("</body>");
                        out.println("</html>");
                    }
                    try {
                        conexion = new Conexion();
                        conexion.comprarCarrito(id_usuario);
                    } catch (Exception ex) {
                        Logger.getLogger(CarritoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (conexion != null) {
                            conexion.cerrarConexion();
                        }
                    }
                }
                default ->
                    response.sendRedirect("InicioServlet");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
