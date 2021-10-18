<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	
	
	<c:choose>
	    <c:when test="${not empty productos}">
	        <div class="row">
			  <div class="col-md-12">
		    	<table class="table">
		    		<thead>
		    			<tr>
		    				<th scope="col">Producto</th>
		    				<th scope="col">Categoria</th>
				      		<th scope="col">Cantidad</th>
				      		<th scope="col">Precio</th>
				      		<th scope="col">Detalle</th>
		    			</tr>
		    		</thead>
		    		<tbody>
			    		<c:forEach items="${productos}" var="producto">
					        <tr>
					            <td><c:out value="${producto.nombre}"/></td>
					            <td><c:out value="${producto.categoria.nombre}"/></td>
					            <td><c:out value="${producto.cantidad}"/></td>
					            <td><c:out value="${producto.precio}"/></td>
					            <td><c:out value="${producto.descripcion}"/></td>  
					        </tr>
					    </c:forEach>
				  </tbody>
				</table>
			  </div>
			</div>
	    </c:when>    
	    <c:otherwise>
	        <div> Aun no hay productos que listar.</div>
	    </c:otherwise>
    </c:choose>

	
	