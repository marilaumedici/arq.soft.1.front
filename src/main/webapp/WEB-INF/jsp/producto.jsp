<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	

    <div class="row">
	  <div class="col-md-12">
	    <h1>Productos:</h1>
	  </div>
	</div>
    <div class="row">
	  <div class="col-md-12">
		<form:form method="post" action="addProductoForm">
		  <div class="form-group row">
		    <label for="staticNombreProducto" class="col-sm-2 col-form-label">Nombre del producto:</label>
		    <div class="col-sm-5">
		      <form:input path="nombre" class="form-control" />
		    </div>
		  </div>
		  <br>
		  <div class="form-group row">
		    <label for="inputCategoria" class="col-sm-2 col-form-label">Categoria:</label>
		    <div class="col-sm-5">
		      <form:input path="categoria" class="form-control"  />
		    </div>
		  </div>
		  <br>
		  <div class="form-group row">
		    <label for="inputCantidad" class="col-sm-2 col-form-label">Cantidad:</label>
		    <div class="col-sm-5">
		      <form:input path="cantidad" class="form-control" />
		    </div>
		  </div>
		  <br>
		  <div class="form-group row">
		    <label for="inputPrecio" class="col-sm-2 col-form-label">Precio:</label>
		    <div class="col-sm-5">
		      <form:input path="precio" class="form-control" />
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="inputDescripcion" class="col-sm-2 col-form-label">Detalle:</label>
		    <div class="col-sm-5">
		      <form:input path="descripcion" class="form-control" />
		    </div>
		  </div>
		  
		  <button type="submit" class="btn btn-primary mb-2">Agregar</button>
	
		</form:form>
	  </div>
	</div>
	<br>
	<div class="row">
	  <div class="col-md-12">
    	<table class="table">
    		<thead>
    			<tr>
    			    <th scope="col" style="visibility: hidden"></th>
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
			            <td style="visibility: hidden"><c:out value="${producto.id}"/></td>
			            <td><c:out value="${producto.nombre}"/></td>
			            <td><c:out value="${producto.categoria}"/></td>
			            <td><c:out value="${producto.cantidad}"/></td>
			            <td><c:out value="${producto.precio}"/></td>
			            <td><c:out value="${producto.descripcion}"/></td>      
			        </tr>
			    </c:forEach>
		  </tbody>
		</table>
	  </div>
	</div>