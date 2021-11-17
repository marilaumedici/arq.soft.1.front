<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	
	<div class="row">
	  <div class="col-md-12">
	    <h1>Productos:</h1>
	  </div>
	</div>
	<br>
	<table style="width:100%;">
		<tr>
		   <td>
		    <ul>
		    <c:forEach items="${categorias}" var="c">
					<li><a href="/productos/filter?getItem=${c.id}">${c.nombre}</a></li>
			</c:forEach>
			</ul>
		   </td>
		   <td>
		        <div class="row">
		         <div class="col-md-12">
			         <form:form method="post" action="/productos/buscarProductoDetalleForm">
			            <div class="form-group row">
						    <label for="staticNombre" class="col-sm-2 col-form-label">Buscar:</label>
						    <div class="col-sm-5">
						      <form:input path="descripcion" class="form-control" required="required" minlength="1" maxlength="30" />
						    </div>
					  	</div>
					  	<br>
				  	    <button type="submit" class="btn btn-primary mb-2">Buscar</button>
				      </form:form>
		          </div>
		        </div>
		        <br>
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
							      		<th scope="col">Acciones</th>
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
								            <td><a href="redComprarProducto?getItem=${producto.id}"><span class="glyphicon glyphicon-shopping-cart">Comprar</span></a></td>
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
		   </td>
		</tr>
	</table>
	
	
	  

	
	