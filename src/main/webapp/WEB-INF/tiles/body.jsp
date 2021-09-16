<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	

    Productos:
    <table style="margin-left:8%;border: 1px solid black;">
	    <c:forEach items="${productos}" var="producto">
	        <tr>
	            <td>Producto: <c:out value="${producto.nombre}"/></td>
	            <td>En stock: <c:out value="${producto.cantidad}"/></td>  
	        </tr>
	    </c:forEach>
	</table>