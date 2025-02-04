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
public class ConsolasServlet extends HttpServlet {

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
                session.setAttribute("paginaAnterior", "ConsolasServlet");
            }
            try {
                conexion = new Conexion();
                out.println(conexion.crearCabecera(session));
                out.println(conexion.mostrarConsolas(session));
            } catch (Exception ex) {
                Logger.getLogger(ConsolasServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
            }
            //Script para la informacion
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_consola = request.getParameter("id_consola") != null ? request.getParameter("id_consola") : "";
        String imgUrl = request.getParameter("imgUrl") != null ? request.getParameter("imgUrl") : "";
        if (id_consola.equals("") && imgUrl.equals("")) {
            processRequest(request, response);
        } else {
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
                    session.setAttribute("paginaAnterior", "ConsolasServlet?id_consola="+id_consola+"&imgUrl="+imgUrl);
                }
                try {
                    conexion = new Conexion();
                    out.println(conexion.crearCabecera(session));
                    out.println("<section class=\"contenedor\"><div class=\"consolas\">");
                    out.println("<div class=\"consola\">");
                    out.println("<img class=\"img_consola\" src=\"" + imgUrl + "\" alt=\" consola \">");
                    out.println("</div></div></section>");
                    out.println(conexion.mostrarJuegos(session, id_consola));
                } catch (Exception ex) {
                    Logger.getLogger(ConsolasServlet.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (conexion != null) {
                        conexion.cerrarConexion();
                    }
                }
                //Script para la informacion
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

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
