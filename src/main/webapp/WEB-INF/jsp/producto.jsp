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
		  	<label for="staticNombreVendedor" class="col-sm-2 col-form-label">Vendedor:</label>
		    <div class="col-sm-5">
			    <form:select class="form-control" id="vendedores" path="idVendedor">
					<form:options items="${vendedores}" itemLabel="razonSocial" itemValue="id"/>
				</form:select>
			</div>
		  </div>
		  <br>
		  <div class="form-group row">
		  	<label for="staticNombreCategoria" class="col-sm-2 col-form-label">Categoria:</label>
		    <div class="col-sm-5">
			    <form:select class="form-control" id="categorias" path="idCategoria">
					<form:options items="${categorias}" itemLabel="nombre" itemValue="id"/>
				</form:select>
			</div>
		  </div>
		  <br>
		  <div class="form-group row">
		    <label for="staticNombreProducto" class="col-sm-2 col-form-label">Nombre del producto:</label>
		    <div class="col-sm-5">
		      <form:input path="nombre" class="form-control" required="required" minlength="5" maxlength="30"  />
		    </div>
		  </div>
		  <br>
	
		  <div class="form-group row">
		    <label for="inputCantidad" class="col-sm-2 col-form-label">Cantidad:</label>
		    <div class="col-sm-5">
		      <form:input path="cantidad" class="form-control" required="required" pattern="[0-9][0-9]{0,5}" title="La cantidad maxima permitida es 999999" />
		    </div>
		  </div>  
		  <br>
		  <div class="form-group row">
		    <label for="inputPrecio" class="col-sm-2 col-form-label">Precio:</label>
		    <div class="col-sm-5">
		      <form:input path="precio" class="form-control" pattern="[1-9]{0,1}[0-9]{1,10}\.[0-9]{2}" required="required" minlength="4" maxlength="13" title="Usa 2 decimales y punto. Ejemplo 34.50"/>
		    </div>
		  </div>
		  <br>
		  <div class="form-group row">
		    <label for="inputDescripcion" class="col-sm-2 col-form-label">Detalle:</label>
		    <div class="col-sm-5">
		      <form:input path="descripcion" class="form-control" minlength="5" maxlength="100" />
		    </div>
		  </div>		  
		  <button type="submit" class="btn btn-primary mb-2">Agregar</button>
		</form:form>
	  </div>
	</div>
	<br>
	<div class="row">
	  <div class="col-md-12">
	    <h4>O carga tus productos desde un <a  href="/export">excel</a> </h4>
	  </div>

	  <div>
	    <form action="import" method="post" enctype="multipart/form-data">
		    <input type="file" name="file" />
		    <input type="submit" value="Cargar" class="btn btn-primary mb-2" />
		</form>
	
	  </div>
	</div>

	
	<br>
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
		      		<th scope="col"></th>
		      		<th scope="col"></th>
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
			            <td><a href="redModifyProducto?getItem=${producto.id}"><span class="glyphicon glyphicon-pencil">Editar</span></a></td>
			            <td><a href="deleteProducto?getItem=${producto.id}"><span class="glyphicon glyphicon-remove">Eliminar</span></a></td>
			        </tr>
			    </c:forEach>
		  </tbody>
		</table>
	  </div>
	</div>