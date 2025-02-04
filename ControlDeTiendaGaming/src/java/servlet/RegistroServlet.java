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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sam
 */
public class RegistroServlet extends HttpServlet {

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
        formulario(request, response, "");
    }

    public void formulario(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistroServlet</title> <link rel=\"stylesheet\" href=\"style.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + error + "</h1>");
            out.println("""
                        <div class="formulario">
                                <h1>Registro</h1>
                                <form action="RegistroServlet" method="POST">
                                    <div>
                                        <label for="usuario">Usuario</label>
                                        <input type="text" id="usuario" name="usuario" required>
                                    </div>
                                    <div>
                                        <label for="clave">Contraseña</label>
                                        <input type="password" id="clave" name="clave" required>
                                    </div>
                                    <br>
                                    <button type="submit">Registrar</button>
                                </form>
                            </div>""");
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
        String usuario = request.getParameter("usuario") != null ? request.getParameter("usuario") : "";
        String clave = request.getParameter("clave") != null ? request.getParameter("clave") : "";
        if (usuario.equals("") || clave.equals("")) {
            formulario(request, response, "Los campos usuario o clave no pueden estar vacios");
        } else {
            Conexion conexion = null;
            try {
                conexion = new Conexion();
                if (conexion.registro(usuario, clave)) {
                    response.sendRedirect("LoginServlet");
                } else {
                    formulario(request, response, "A ocurrido un error con la base de datos o con el nombre de usuario ya en uso");
                }
            } catch (Exception ex) {
                System.out.println("Error " + ex);
                Logger.getLogger(RegistroServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
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
