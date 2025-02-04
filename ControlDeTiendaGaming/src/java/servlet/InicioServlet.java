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
public class InicioServlet extends HttpServlet {

    /*
     Falta
        Opciones de admin
            Listo mod
            Listo add 
            Listo delete 
            Buscar para añadir la opcion de subir la imagen mediante el servlet para añadir o modificar
        
        Listo Arreglar el elimnar para que te deje eliminar consola apesar de esta asociado a un juego
        Listo Colocar bonito el inicio
        Listo Arreglar la distribuccion de servlets en paquetes distintos
        Listo Registro
        Listo Añadir a sesicion una forma de ver cual pagina fue la anterior para el carrito
        Listo Productos
        Listo Comprar (Se hay tiempo añado cesta de la compra vincularlo con la sesion o usar un apartado de la base de datos con el id del producto o yo que se)
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
            try {
                conexion = new Conexion();
                out.println(conexion.crearCabecera(session));
            } catch (Exception ex) {
                Logger.getLogger(ConsolasServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (conexion != null) {
                    conexion.cerrarConexion();
                }
            }

            out.println("""
                        <main class="secciones">
                                    <section class="seccion">
                                        <img src="img/iconos/productos.jpg" alt="Productos">
                                        <h2>Productos</h2>
                                        <p>Consulta nuestra amplia gama de productos disponibles.</p>
                                        <a href="ProductosServlet" class="boton">Ver Productos</a>
                                    </section>
                                    <section class="seccion">
                                        <img src="img/iconos/juegos.jpeg" alt="Inicio">
                                        <h2>Juegos</h2>
                                        <p>Descubre los mejores juegos y explora lo mejor que ofrece el mercado.</p>
                                        <a href="JuegosServlet" class="boton">Ver juegos</a>
                                    </section>
                                    <section class="seccion">
                                        <img src="img/iconos/consolas.webp" alt="Consolas">
                                        <h2>Consolas</h2>
                                        <p>Encuentra las mejores consolas para disfrutar tus juegos favoritos.</p>
                                        <a href="ConsolasServlet" class="boton">Ver Consolas</a>
                                    </section>
                                </main>""");
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
