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
public class JuegosServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consolas</title> <link href=\"style.css\" rel=\"stylesheet\">");
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.setAttribute("paginaAnterior", "JuegosServlet");
            }
            try {
                conexion = new Conexion();
                out.println(conexion.crearCabecera(session));
                out.println(conexion.mostrarJuegos(session, "0"));
            } catch (Exception ex) {
                Logger.getLogger(JuegosServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
            }
            out.println("""
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
        processRequest(request, response);
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
