/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    • Modificar la info de cualquier producto.
    • Insertar nuevos productos.
    • Eliminar productos.
 */
public class ConexionAdmin {

    private final Connection miConexion;

    public ConexionAdmin() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        this.miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "daw2", "1234");
    }

    // modificar
    public String mostrarModificar() {
        String cadena = "";
        cadena += "<center><h1>Consolas</h1></center>";
        cadena += mostrarModConsolas();
        cadena += "<center><h1>Juegos</h1></center>";
        cadena += mostrarModJuegos();
        return cadena;
    }

    public String mostrarModConsolas() {
        String consulta = "SELECT * FROM consolas;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            ResultSet rs = stm.executeQuery();
            String cadena = "<section class=\"contenedor\"><div class=\"consolas\">";
            cadena += "";
            while (rs.next()) {
                // datos de la BD
                String nombre = rs.getString("nombre");
                int id = rs.getInt("id");
                String potenciaCPU = rs.getString("potenciaCPU");
                String potenciaGPU = rs.getString("potenciaGPU");
                String compania = rs.getString("compania");
                double precio = rs.getDouble("precio");
                int unidades = rs.getInt("unidades");
                String imgUrl = rs.getString("imgUrl");
                // Creacion del html
                cadena += "<div class=\"consola\">";
                cadena += "<img class=\"img_consola\" src=\"" + imgUrl + "\" alt=\"" + nombre + "\">";
                cadena += "<div class=\"contenido_tarjeta\">";
                cadena += "<h3 class=\"titulo_tarjeta\">" + nombre + "</h3>";
                cadena += "<h4 class=\"precio_tarjeta\">" + precio + " €</h4>";
                // Formulario para modificar
                cadena += "<form action=\"ModificarServlet\" method=\"post\">";
                cadena += "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">";
                cadena += "<input type=\"hidden\" name=\"db\" value=\"consolas\">";
                cadena += "<input type=\"hidden\" name=\"accion\" value=\"modificar\">";
                // Datos para luego el formulario
                cadena += "<input type=\"hidden\" name=\"nombre\" value=\"" + nombre + "\">";
                cadena += "<input type=\"hidden\" name=\"potenciaCPU\" value=\"" + potenciaCPU + "\">";
                cadena += "<input type=\"hidden\" name=\"potenciaGPU\" value=\"" + potenciaGPU + "\">";
                cadena += "<input type=\"hidden\" name=\"compania\" value=\"" + compania + "\">";
                cadena += "<input type=\"hidden\" name=\"precio\" value=\"" + precio + "\">";
                cadena += "<input type=\"hidden\" name=\"unidades\" value=\"" + unidades + "\">";
                cadena += "<input type=\"hidden\" name=\"imgUrl\" value=\"" + imgUrl + "\">";
                cadena += "<button class=\"boton\" type=\"submit\">Modificar</button>";
                cadena += "</form>";
                // Informacion adicional del producto
                cadena += "<br><button class=\"boton\" onclick=\"mostrar_info('C" + id + "',this)\">Ver mas informacion </button> <div class=\"informacion\" id=\"C" + id + "\">";
                cadena += "<p>Potencia de CPU: " + potenciaCPU + "</p>";
                cadena += "<p>Potencia de GPU: " + potenciaGPU + "</p>";
                cadena += "<p>Compañia: " + compania + "</p>";
                cadena += "<p>Unidades: " + unidades + "</p>";
                cadena += "</div>";
                cadena += "</div>";
                cadena += "</div>";
            }
            cadena += "</div> </section>";
            return cadena;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " No se pudo ver la base de datos o ocurrio un error de conexion";
    }

    public String mostrarModJuegos() {
        String consulta;
        consulta = "SELECT * FROM juegos ORDER BY id_consola ASC;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            ResultSet rs = stm.executeQuery();
            String cadena = "<section class=\"contenedor\"><div class=\"juegos\">";
            cadena += "";
            // Procesar fila por fila
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int id = rs.getInt("id");
                String genero = rs.getString("genero");
                int puntuacion = rs.getInt("puntuacion");
                String compania = rs.getString("compania");
                double precio = rs.getDouble("precio");
                int unidades = rs.getInt("unidades");
                String imgUrl = rs.getString("imgUrl");
                int id_consola = rs.getInt("id_consola");
                cadena += "<div class=\"juego\">";
                cadena += "<img class=\"img_juego\" src=\"" + imgUrl + "\" alt=\"" + nombre + "\">";
                cadena += "<div class=\"contenido_tarjeta\">";
                cadena += "<h3 class=\"titulo_tarjeta\">" + nombre + "</h3>";
                cadena += "<h4 class=\"precio_tarjeta\">" + precio + " €</h4>";
                // Formualrio para editar
                cadena += "<form action=\"ModificarServlet\" method=\"post\">";
                cadena += "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">";
                cadena += "<input type=\"hidden\" name=\"db\" value=\"juegos\">";
                cadena += "<input type=\"hidden\" name=\"accion\" value=\"modificar\">";
                // Datos para luego el formulario
                cadena += "<input type=\"hidden\" name=\"nombre\" value=\"" + nombre + "\">";
                cadena += "<input type=\"hidden\" name=\"genero\" value=\"" + genero + "\">";
                cadena += "<input type=\"hidden\" name=\"puntuacion\" value=\"" + puntuacion + "\">";
                cadena += "<input type=\"hidden\" name=\"compania\" value=\"" + compania + "\">";
                cadena += "<input type=\"hidden\" name=\"precio\" value=\"" + precio + "\">";
                cadena += "<input type=\"hidden\" name=\"unidades\" value=\"" + unidades + "\">";
                cadena += "<input type=\"hidden\" name=\"imgUrl\" value=\"" + imgUrl + "\">";
                cadena += "<input type=\"hidden\" name=\"id_consola\" value=\"" + id_consola + "\">";
                cadena += "<button class=\"boton\" type=\"submit\">Modificar</button>";
                cadena += "</form>";
                // Informacion adicional del producto
                cadena += "<br><button class=\"boton\" onclick=\"mostrar_info('J" + id + "',this)\">Ver mas informacion </button> <div class=\"informacion\" id=\"J" + id + "\">";
                cadena += "<p>Genero: " + genero + "</p>";
                cadena += "<p>Puntuacion Metacritic: " + puntuacion + "</p>";
                cadena += "<p>Compañia: " + compania + "</p>";
                cadena += "<p>Unidades: " + unidades + "</p>";
                cadena += "</div>";
                cadena += "</div>";
                cadena += "</div>";
            }
            cadena += "</div> </section>";
            return cadena;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " No se pudo ver la base de datos o ocurrio un error de conexion";
    }

    public boolean modificar(String id, String db, String nombre, String compania, String precio, String unidades, String imgUrl, String dato1, String dato2, String id_consola) {
        String update;
        id_consola = id_consola.equals("0") ? null : id_consola;
        if (db.equals("consolas")) {
            update = "UPDATE " + db + " SET nombre = ?, potenciaCPU = ?, potenciaGPU = ? , compania = ?, precio = ?, unidades = ?, imgUrl = ? WHERE id = ?;";
        } else {
            update = "UPDATE " + db + " SET nombre = ?, genero = ?, puntuacion = ? , compania = ?, precio = ?, unidades = ?, imgUrl = ?, id_consola = ? WHERE id = ?;";
        }
        try (PreparedStatement stm = this.miConexion.prepareStatement(update);) {
            stm.setString(1, nombre);
            stm.setString(2, dato1);
            stm.setString(3, dato2);
            stm.setString(4, compania);
            stm.setString(5, precio);
            stm.setString(6, unidades);
            stm.setString(7, imgUrl);
            if (db.equals("consolas")) {
                stm.setString(8, id);
            }else{
                stm.setString(8, id_consola);
                stm.setString(9, id);
            }
            
            System.out.println("Datos recibidos: ID=" + id + ", DB=" + db + ", Nombre=" + nombre + ", Compañía=" + compania + ", Precio=" + precio + ", Unidades=" + unidades + ", ImgURL=" + imgUrl + ", Dato1=" + dato1 + ", Dato2=" + dato2);
            int filasAfectadas = stm.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // eliminar
    public String mostrarEliminar() {
        String cadena = "";
        cadena += "<center><h1>Consolas</h1></center>";
        cadena += mostrarEliConsolas();
        cadena += "<center><h1>Juegos</h1></center>";
        cadena += mostrarEliJuegos();
        return cadena;
    }

    public String mostrarEliConsolas() {
        String consulta;
        consulta = "SELECT * FROM consolas ORDER BY id ASC;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            ResultSet rs = stm.executeQuery();
            String cadena = "<section class=\"contenedor\"><div class=\"juegos\">";
            cadena += "";
            // Procesar fila por fila
            while (rs.next()) {
                // datos de la BD
                String nombre = rs.getString("nombre");
                int id = rs.getInt("id");
                String potenciaCPU = rs.getString("potenciaCPU");
                String potenciaGPU = rs.getString("potenciaGPU");
                String compania = rs.getString("compania");
                double precio = rs.getDouble("precio");
                int unidades = rs.getInt("unidades");
                String imgUrl = rs.getString("imgUrl");
                // Creacion del html
                cadena += "<div class=\"consola\">";
                cadena += "<img class=\"img_consola\" src=\"" + imgUrl + "\" alt=\"" + nombre + "\">";
                cadena += "<div class=\"contenido_tarjeta\">";
                cadena += "<h3 class=\"titulo_tarjeta\">" + nombre + "</h3>";
                cadena += "<h4 class=\"precio_tarjeta\">" + precio + " €</h4>";
                // Formulario para eliminar
                cadena += "<form action=\"EliminarServlet\" method=\"post\">";
                cadena += "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">";
                cadena += "<input type=\"hidden\" name=\"db\" value=\"consolas\">";
                cadena += "<input type=\"hidden\" name=\"accion\" value=\"modificar\">";
                // Datos para luego el formulario
                cadena += "<input type=\"hidden\" name=\"nombre\" value=\"" + nombre + "\">";
                cadena += "<input type=\"hidden\" name=\"potenciaCPU\" value=\"" + potenciaCPU + "\">";
                cadena += "<input type=\"hidden\" name=\"potenciaGPU\" value=\"" + potenciaGPU + "\">";
                cadena += "<input type=\"hidden\" name=\"compania\" value=\"" + compania + "\">";
                cadena += "<input type=\"hidden\" name=\"precio\" value=\"" + precio + "\">";
                cadena += "<input type=\"hidden\" name=\"unidades\" value=\"" + unidades + "\">";
                cadena += "<input type=\"hidden\" name=\"imgUrl\" value=\"" + imgUrl + "\">";
                cadena += "<button class=\"boton\" type=\"submit\">Eliminar</button>";
                cadena += "</form>";
                // Informacion adicional del producto
                cadena += "<br><button class=\"boton\" onclick=\"mostrar_info('C" + id + "',this)\">Ver mas informacion </button> <div class=\"informacion\" id=\"C" + id + "\">";
                cadena += "<p>Potencia de CPU: " + potenciaCPU + "</p>";
                cadena += "<p>Potencia de GPU: " + potenciaGPU + "</p>";
                cadena += "<p>Compañia: " + compania + "</p>";
                cadena += "<p>Unidades: " + unidades + "</p>";
                cadena += "</div>";
                cadena += "</div>";
                cadena += "</div>";
            }
            cadena += "</div> </section>";
            return cadena;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " No se pudo ver la base de datos o ocurrio un error de conexion";
    }

    public String mostrarEliJuegos() {
        String consulta;
        consulta = "SELECT * FROM juegos ORDER BY id_consola ASC;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            ResultSet rs = stm.executeQuery();
            String cadena = "<section class=\"contenedor\"><div class=\"juegos\">";
            cadena += "";
            // Procesar fila por fila
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int id = rs.getInt("id");
                String genero = rs.getString("genero");
                int puntuacion = rs.getInt("puntuacion");
                String compania = rs.getString("compania");
                double precio = rs.getDouble("precio");
                int unidades = rs.getInt("unidades");
                String imgUrl = rs.getString("imgUrl");
                cadena += "<div class=\"juego\">";
                cadena += "<img class=\"img_juego\" src=\"" + imgUrl + "\" alt=\"" + nombre + "\">";
                cadena += "<div class=\"contenido_tarjeta\">";
                cadena += "<h3 class=\"titulo_tarjeta\">" + nombre + "</h3>";
                cadena += "<h4 class=\"precio_tarjeta\">" + precio + " €</h4>";
                // Formualrio para editar
                cadena += "<form action=\"EliminarServlet\" method=\"post\">";
                cadena += "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">";
                cadena += "<input type=\"hidden\" name=\"db\" value=\"juegos\">";
                cadena += "<input type=\"hidden\" name=\"accion\" value=\"eliminar\">";
                // Datos para luego el formulario
                cadena += "<input type=\"hidden\" name=\"nombre\" value=\"" + nombre + "\">";
                cadena += "<input type=\"hidden\" name=\"imgUrl\" value=\"" + imgUrl + "\">";
                cadena += "<button class=\"boton\" type=\"submit\">Eliminar</button>";
                cadena += "</form>";
                // Informacion adicional del producto
                cadena += "<br><button class=\"boton\" onclick=\"mostrar_info('J" + id + "',this)\">Ver mas informacion </button> <div class=\"informacion\" id=\"J" + id + "\">";
                cadena += "<p>Genero: " + genero + "</p>";
                cadena += "<p>Puntuacion Metacritic: " + puntuacion + "</p>";
                cadena += "<p>Compañia: " + compania + "</p>";
                cadena += "<p>Unidades: " + unidades + "</p>";
                cadena += "</div>";
                cadena += "</div>";
                cadena += "</div>";
            }
            cadena += "</div> </section>";
            return cadena;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " No se pudo ver la base de datos o ocurrio un error de conexion";
    }

    public boolean eliminar(String id, String db) {
        String delete = "DELETE FROM " + db + " WHERE id = ?";
        try (PreparedStatement stm = this.miConexion.prepareStatement(delete);) {
            stm.setString(1, id);
            int filasAfectadas = stm.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println(" Error: " + ex);
        }
        return false;
    }

    // Añadir
    public boolean añadirConsola(String nombre, String compania, String precio, String unidades, String imgUrl, String potenciaCPU, String potenciaGPU) {
        String insert = "INSERT INTO consolas (nombre, potenciaCPU, potenciaGPU, compania, precio, unidades, imgUrl) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stm = this.miConexion.prepareStatement(insert);) {
            stm.setString(1, nombre);
            stm.setString(2, potenciaCPU);
            stm.setString(3, potenciaGPU);
            stm.setString(4, compania);
            stm.setString(5, precio);
            stm.setString(6, unidades);
            stm.setString(7, imgUrl);
            int filasInsertadas = stm.executeUpdate();
            if (filasInsertadas > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(" Error: " + ex);
        }
        return false;
    }

    public boolean añadirJuego(String nombre, String compania, String precio, String unidades, String imgUrl, String genero, String puntuacion, String id_consola) {
        id_consola = id_consola.equals("0") ? null : id_consola; // para juegos que no tengan consola (poner para que sea null en base de datros)
        String insert = "INSERT INTO juegos (nombre, genero, puntuacion, compania, precio, unidades, imgUrl, id_consola) VALUES (?, ?, ?, ?, ? ,?, ?, ? );";
        try (PreparedStatement stm = this.miConexion.prepareStatement(insert);) {
            stm.setString(1, nombre);
            stm.setString(2, genero);
            stm.setString(3, puntuacion);
            stm.setString(4, compania);
            stm.setString(5, precio);
            stm.setString(6, unidades);
            stm.setString(7, imgUrl);
            stm.setString(8, id_consola);
            int filasInsertadas = stm.executeUpdate();
            if (filasInsertadas > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(" Error: " + ex);
        }
        return false;
    }

    public String obtenerConsolas() {
        String cadena = "";
        String consulta = "SELECT * FROM consolas ORDER BY id ASC;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            ResultSet rs = stm.executeQuery();
            cadena += "";
            // Procesar fila por fila
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int id = rs.getInt("id");
                cadena += id + "-" + nombre + "/";
            }
            return cadena;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " No se pudo ver la base de datos o ocurrio un error de conexion";
    }

    public void cerrarConexion() {
        if (this.miConexion != null) {
            try {
                this.miConexion.close();
                System.out.println("Conexión cerrada exitosamente.");
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
            }
        }
    }
}
