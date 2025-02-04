/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author sam
 */
public class LoginServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login</title> <link rel=\"stylesheet\" href=\"style.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("""
                        <div class="formulario">
                                <h1>Iniciar Sesi\u00f3n</h1>
                                <form action="LoginServlet" method="POST">
                                    <label for="usuario">Usuario</label>
                                    <input type="text" id="usuario" name="usuario" placeholder="Ingresa tu usuario" required>
                                    <label for="clave">Contrase\u00f1a</label>
                                    <input type="clave" id="clave" name="clave" placeholder="Ingresa tu contrase\u00f1a" required>
                                    <button type="submit">Iniciar Sesi\u00f3n</button>
                                </form>
                                <p>\u00bfNo tienes cuenta? <a href="RegistroServlet">Reg\u00edstrate</a></p>
                            </div>
                        """);
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
        Conexion conexion = null;
        try {
            conexion = new Conexion();
            String usuario = request.getParameter("usuario") != null ? request.getParameter("usuario") : "";
            String clave = request.getParameter("clave") != null ? request.getParameter("clave") : "";
            if (conexion.login(usuario, clave)) {
                // Esto lo vimos en php asi que lo metia aqui
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                session.setAttribute("id", conexion.buscar_id(usuario));
                session.setAttribute("esAdmin", conexion.esAdmin(usuario));
                session.setAttribute("paginaAnterior", "InicioServlet"); // esto solo me sirve para añadir al carrito y que regrese a la pagina anterior
                response.sendRedirect("InicioServlet");
            } else {
                response.sendRedirect("LoginServlet");
            }
        } catch (Exception ex) {
            System.out.println("Error " + ex);
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conexion != null) {
                conexion.cerrarConexion();
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
