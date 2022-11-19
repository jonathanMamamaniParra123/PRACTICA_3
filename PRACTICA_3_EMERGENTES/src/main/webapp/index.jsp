<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="com.emergentes.modelo.Producto"%>
<%
    List<Producto> lista = (List<Producto>) request.getAttribute("lista");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
       
        <h1>Productos</h1>
       
        <p><a href="MainController?op=nuevo">Nuevo Producto</a></p>
        <br>
        <br>
         
        <table border = 3>
            <b>
            <tr>
                <th>id  </th>
                <th>producto  </th>
                <th>precio  </th>
                <th>cantidad  </th>
                <th>editar  </th>
                <th>eliminar  </th>
            </tr>
             </b>
            <c:forEach var="item" items="${lista}">
                <tr>
                    <td>${item.id} </td>
                    <td>${item.producto} </td>
                    <td>${item.precio} </td>
                    <td>${item.cantidad} </td>
                    <td><a href="MainController?op=editar&id=${item.id}"> editar</a></td>
                    <td><a href="MainController?op=eliminar&id=${item.id}" onclick="return confirm('Eliminar ?')"> eliminar</a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
