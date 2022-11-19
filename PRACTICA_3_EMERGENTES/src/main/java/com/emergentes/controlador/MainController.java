package com.emergentes.controlador;

import com.emergentes.modelo.Producto;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op;
            op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";
            ArrayList<Producto> lista = new ArrayList<Producto>();
            ConexionDB conec = new ConexionDB();
            Connection con = conec.conectar();
            PreparedStatement ps;
            ResultSet rs;
            if (op.equals("list")) {

                String sql = "SELECT * FROM productos";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Producto prod = new Producto();
                    prod.setId(rs.getInt("id"));
                    prod.setProducto(rs.getString("producto"));
                    prod.setPrecio(rs.getDouble("precio"));
                    prod.setCantidad(rs.getInt("cantidad"));
                    lista.add(prod);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("index.jsp").forward(request, response);

            }
            if (op.equals("nuevo")) {
                Producto produ = new Producto();
                request.setAttribute("prod", produ);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
            if (op.equals("eliminar")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "DELETE FROM productos WHERE id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                response.sendRedirect("MainController");
            }
            if(op.equals("editar")){
               
                    int id = Integer.parseInt(request.getParameter("id"));
                    String sql = "SELECT * FROM productos WHERE id = ?";
                    ps= con.prepareStatement(sql);
                    ps.setInt(1, id);
                    rs=ps.executeQuery();
                    Producto prod = new Producto();
                    while(rs.next()){
                        prod.setId(rs.getInt("id"));
                        prod.setProducto(rs.getString("producto"));
                        prod.setPrecio(rs.getDouble("precio"));
                        prod.setCantidad(rs.getInt("cantidad"));
                    }
                    request.setAttribute("prod", prod);
                    request.getRequestDispatcher("editar.jsp").forward(request, response);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String producto = request.getParameter("producto");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            Producto x = new Producto();
            x.setProducto(producto);
            x.setId(id);
            x.setPrecio(precio);
            x.setCantidad(cantidad);
            ConexionDB conection = new ConexionDB();
            Connection conn = conection.conectar();
            PreparedStatement ps;
            if (id == 0) {

                String sql = "INSERT INTO productos(id,producto,precio,cantidad) VALUES(null,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, x.getProducto());
                ps.setDouble(2, x.getPrecio());
                ps.setInt(3, x.getCantidad());
                ps.executeUpdate();
                response.sendRedirect("MainController");
            }else{
                try {
                    String sql = "UPDATE productos set producto = ?,precio = ?,cantidad = ? WHERE id = ?";
                    ps=conn.prepareStatement(sql);
                    ps.setString(1, x.getProducto());
                    ps.setDouble(2, x.getPrecio());
                    ps.setInt(3, x.getCantidad());
                    ps.setInt(4, x.getId());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    
                }
                  response.sendRedirect("MainController");
            }
        } catch (SQLException ex) {
           
        }
    }

}
