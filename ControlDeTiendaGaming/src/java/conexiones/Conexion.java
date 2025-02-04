/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexiones;

import servlet.ConsolasServlet;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sam
 */
public class Conexion {

    private final Connection miConexion;

    public Conexion() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        this.miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "daw2", "1234");
    }

    public boolean registro(String usuario, String clave) {
        String insert = "INSERT INTO usuarios (usuario, clave) VALUES (?, ?)";
        try (PreparedStatement stm = this.miConexion.prepareStatement(insert);) {
            stm.setString(1, usuario);
            stm.setString(2, clave);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al registrar usuario: " + ex.getMessage());
            return false;
        }
    }

    // Rehacer login y esAdmin para hacer solo un metodo que regrese un array de boolean con los dos datos
    public boolean login(String usuario, String clave) {
        String consulta = "SELECT * FROM usuarios WHERE usuario = ? and clave = ?;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            stm.setString(1, usuario);
            stm.setString(2, clave);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("Error " + ex);
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean esAdmin(String usuario) {
        String consulta = "SELECT * FROM usuarios WHERE usuario = ?";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            stm.setString(1, usuario);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    boolean admin = rs.getBoolean("admin");
                    System.out.println(admin);
                    return admin;
                } else {
                    System.out.println("El usuario no existe o no se encontró.");
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("Error " + ex);
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int buscar_id(String usuario) {
        int id = 0;
        String consulta = "SELECT * FROM usuarios WHERE usuario = ?";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            stm.setString(1, usuario);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    id = rs.getInt("id");
                    System.out.println(id);
                } else {
                    System.out.println("El usuario no existe o no se encontró.");
                }
            } catch (SQLException ex) {
                System.out.println("Error " + ex);
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    //Generar cabecera
    public String crearCabecera(HttpSession session) {
        String cabecera = """
        <header class="cabecera">
            <div class="logo">
                <h1 style="color: white;">Tienda</h1>
            </div>
            <nav class="menu">
                <ul>
                    <li><a href="InicioServlet">Inicio</a></li>
                    <li><a href="ProductosServlet">Productos</a></li>
                    <li><a href="ConsolasServlet">Consolas</a></li>
                    <li><a href="JuegosServlet">Juegos</a></li>
    """;
        // Parte de la sesion
        if (session == null || session.getAttribute("usuario") == null) {
            cabecera += "<li><a href=\"LoginServlet\">Login</a></li>";
        } else {
            String usuario = (String) session.getAttribute("usuario");
            Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
            int indiceCarrito = 0;

            try {
                Conexion conexion = new Conexion();
                indiceCarrito = conexion.indiceCarrito(session);
                conexion.cerrarConexion();
            } catch (Exception ex) {
                Logger.getLogger(ConsolasServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            cabecera += "<li class=\"carrito\"><a href=\"CarritoServlet\"><span class=\"indice\">"
                    + indiceCarrito
                    + "</span><img src=\"img/iconos/carrito.png\" alt=\"carrito\"></a></li>";

            cabecera += "<li>" + usuario + "</li>";
            cabecera += "<li><a href=\"CerrarSessionServlet\">Cerrar Sesión</a></li>";
            if (esAdmin) {
                cabecera += "<li><a href=\"AdminServlet\">Opciones de admin</a></li>";
            }
        }
        cabecera += """
                </ul>
            </nav>
        </header>
    """;
        return cabecera;
    }

    public String mostrarConsolas(HttpSession session) {
        String consulta = "SELECT * FROM consolas;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            ResultSet rs = stm.executeQuery();
            String cadena = "<section class=\"contenedor\"><div class=\"consolas\">";
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
                // Boton de carrito
                if (session == null) {
                    cadena += "<button class=\"boton\"><a href=\"LoginServlet\">Añadir al carrito</a></button> ";
                } else {
                    cadena += "<form action=\"CarritoServlet\" method=\"post\">";
                    cadena += "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">";
                    cadena += "<input type=\"hidden\" name=\"db\" value=\"consolas\">";
                    cadena += "<input type=\"hidden\" name=\"accion\" value=\"añadir\">";
                    cadena += "<button class=\"boton\" type=\"submit\">Añadir al carrito</button>";
                    cadena += "</form>";
                }
                // Ver catalogo de consola
                cadena += "<form action=\"ConsolasServlet\" method=\"get\">";
                cadena += "<input type=\"hidden\" name=\"id_consola\" value=\"" + id + "\">";
                cadena += "<input type=\"hidden\" name=\"imgUrl\" value=\"" + imgUrl + "\">";
                cadena += "<button class=\"boton\" type=\"submit\">Ver catalogo</button>";
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

    public String mostrarJuegos(HttpSession session, String id_consola) {
        String consulta;
        if (id_consola.equals("0")) {
            consulta = "SELECT * FROM juegos ORDER BY id_consola ASC;";
        } else {
            consulta = "SELECT * FROM juegos WHERE id_consola ='" + id_consola + "';";
            System.out.println(consulta);
        }
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
                // Boton de carrito
                if (session == null) {
                    cadena += "<button class=\"boton\"><a href=\"LoginServlet\">Añadir al carrito</a></button> ";
                } else {
                    cadena += "<form action=\"CarritoServlet\" method=\"post\">";
                    cadena += "<input type=\"hidden\" name=\"id_producto\" value=\"" + id + "\">";
                    cadena += "<input type=\"hidden\" name=\"db\" value=\"juegos\">";
                    cadena += "<input type=\"hidden\" name=\"accion\" value=\"añadir\">";
                    cadena += "<button class=\"boton\" type=\"submit\">Añadir al carrito</button>";
                    cadena += "</form>";
                }
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

    public boolean añadirCarrito(String db, String id_producto, int cantidadSolicitada, String id_usuario) {
        cantidadSolicitada = 1; // luego cambiare para elejir cuantos quieres
        String consultaProducto = "SELECT * FROM " + db + " WHERE id = ?;";
        String insertarCarrito = "";
        if (db.equals("consolas")) {
            insertarCarrito = "INSERT INTO carrito (id_usuario, nombre, cantidad, precio, imgUrl, id_consola) VALUES (?, ?, ?, ?, ?, ?);";
        } else if (db.equals("juegos")) {
            insertarCarrito = "INSERT INTO carrito (id_usuario, nombre, cantidad, precio, imgUrl, id_juego) VALUES (?, ?, ?, ?, ?, ?);";
        }
        try (PreparedStatement stmProducto = this.miConexion.prepareStatement(consultaProducto)) {
            stmProducto.setString(1, id_producto);
            try (ResultSet rs = stmProducto.executeQuery()) {
                if (rs.next()) {
                    // Obtener datos del producto
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int unidadesDisponible = rs.getInt("unidades");
                    double precio = rs.getDouble("precio");
                    String imgUrl = rs.getString("imgUrl");
                    // Verificar si la cantidad esta disponible
                    if (cantidadSolicitada > unidadesDisponible) {
                        System.out.println("No hay suficiente cantidad disponible.");
                        return false;
                    }
                    // insert para el carriuto
                    try (PreparedStatement stmInsertar = this.miConexion.prepareStatement(insertarCarrito)) {
                        stmInsertar.setString(1, id_usuario);
                        stmInsertar.setString(2, nombre);
                        stmInsertar.setInt(3, cantidadSolicitada);
                        stmInsertar.setDouble(4, precio * cantidadSolicitada);
                        stmInsertar.setString(5, imgUrl);
                        stmInsertar.setInt(6, id);
                        int filasInsertadas = stmInsertar.executeUpdate();
                        return filasInsertadas > 0;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int indiceCarrito(HttpSession session) {
        int indice = 0;
        int id_usuario = (int) session.getAttribute("id");
        String consulta = "SELECT COUNT(*) AS indice FROM `carrito` WHERE id_usuario = ?;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            stm.setInt(1, id_usuario);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    indice = rs.getInt("indice");
                } else {
                    System.out.println("El usuario no tiene nada en el carrito.");
                }
            } catch (SQLException ex) {
                System.out.println("Error " + ex);
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return indice;
    }

    public String mostrarCarrito(HttpSession session) {
        String usuario = (String) session.getAttribute("usuario");
        int id_usuario = (int) session.getAttribute("id");
        String cadena = "";
        double precio_total = 0;
        // Título de la sección
        cadena += "<div class='carrito_contenedor'>";
        cadena += "<h2 class='carrito_titulo'>Carrito de " + usuario + "</h2>";
        cadena += "<div class='carrito_productos'>";

        String consulta = "SELECT * FROM `carrito` WHERE id_usuario = ?;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            stm.setInt(1, id_usuario);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    do {
                        // Obtener los datos del producto
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        int cantidad = rs.getInt("cantidad");
                        double precio = rs.getDouble("precio");
                        String imgUrl = rs.getString("imgUrl");
                        precio_total += precio;
                        // Crear la tarjeta del producto
                        cadena += "<div class='carrito_item'>";
                        cadena += "<img src='" + imgUrl + "' alt='" + nombre + "' />";
                        cadena += "<h3>" + nombre + "</h3>";
                        cadena += "<p class='cantidad'>Cantidad: " + cantidad + "</p>";
                        cadena += "<p class='precio'>$" + precio + "</p>";
                        cadena += "<form action=\"CarritoServlet\" method=\"post\">";
                        cadena += "<input type=\"hidden\" name=\"id_cesta\" value=\"" + id + "\">";
                        cadena += "<input type=\"hidden\" name=\"accion\" value=\"eliminar\">";
                        cadena += "<button class=\"boton_eliminar\" type=\"submit\">Eliminar del carrito</button>";
                        cadena += "</form>";
                        cadena += "</div>";
                    } while (rs.next());
                    cadena += "</div>";
                    cadena += "<div class=\"resumen_compra\">\n";
                    cadena += "<h3>Resumen de tu compra</h3>\n";
                    cadena += "<p class=\"precio_final\">Precio total: <span class='precio_final'>" + precio_total + "</span></p>";
                    cadena += "<form action=\"CarritoServlet\" method=\"post\" class=\"formulario_compra\">";
                    cadena += "<input type=\"hidden\" name=\"accion\" value=\"comprar\">";
                    cadena += "<button type=\"submit\" class=\"boton_comprar\">Realizar Compra</button>\n";
                    cadena += "</form>\n";
                    cadena += "</div>";
                } else {
                    // Carrito vacío
                    cadena += "</div><p>No tienes productos en el carrito.</p>";
                }
            } catch (SQLException ex) {
                System.out.println("Error al obtener productos: " + ex.getMessage());
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar consulta: " + ex.getMessage());
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        cadena += "</div>";
        return cadena;
    }

    public boolean eliminarCarrito(String id, String id_usuario) {
        String delete = "DELETE FROM carrito WHERE id = ? and id_usuario = ?";
        try (PreparedStatement stm = this.miConexion.prepareStatement(delete);) {
            stm.setString(1, id);
            stm.setString(2, id_usuario);
            int filasAfectadas = stm.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println(" Error: " + ex);
        }
        return false;
    }

    public String comprarCarrito(String id_usuario) {
        String cadena = "<div class=\"resumen_compra\"> <h3>Resumen de tu compra</h3>";
        String consultaCarrito = "SELECT * FROM `carrito` WHERE id_usuario = ?;";
        try (PreparedStatement stm = this.miConexion.prepareStatement(consultaCarrito);) {
            stm.setString(1, id_usuario);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    do {
                        // los datos del producto
                        int id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        int cantidad = rs.getInt("cantidad");
                        int id_consola = rs.getInt("id_consola");
                        int id_juego = rs.getInt("id_juego");
                        if (gestionStock(cantidad, id_consola, id_juego)) {
                            cadena += "<p>Producto comprado " + nombre + "</p><br>";
                            String id_string = id + "";
                            eliminarCarrito(id_string, id_usuario);
                        } else {
                            cadena += "<p>Lo sentimos pero el producto " + nombre + " no esta en stock en este momento. lo quitaremos de su cesta ahora</p><br>";
                            String id_string = id + "";
                            eliminarCarrito(id_string, id_usuario);
                        }
                    } while (rs.next());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener productos: " + ex.getMessage());
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadena;
    }

    public boolean gestionStock(int cantidadSolicitada, int id_consola, int id_juego) {
        String consulta = "";
        String update = "";
        int id = 0;
        if (id_consola != 0) {
            consulta = "SELECT * FROM `consolas` WHERE id = ?;";
            update = "UPDATE `consolas` SET `unidades` = ? WHERE id = ?;";
            id = id_consola;
        } else if (id_juego != 0) {
            consulta = "SELECT * FROM `juegos` WHERE id = ?;";
            update = "UPDATE `juegos` SET `unidades` = ? WHERE id = ?;";
            id = id_juego;
        }
        try (PreparedStatement stm = this.miConexion.prepareStatement(consulta);) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    do {
                        // los datos del producto
                        int unidades = rs.getInt("unidades");
                        if (cantidadSolicitada > unidades) {
                            return false;
                        } else {
                            // Actualizar el stock
                            try (PreparedStatement updateStm = this.miConexion.prepareStatement(update)) {
                                updateStm.setInt(1, unidades - cantidadSolicitada);
                                updateStm.setInt(2, id);
                                int filasActualizadas = updateStm.executeUpdate();
                                return filasActualizadas > 0;
                            }
                        }
                    } while (rs.next());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener productos: " + ex.getMessage());
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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