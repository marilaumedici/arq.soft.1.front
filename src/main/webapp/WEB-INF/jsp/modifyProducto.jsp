<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	



    <div class="row">
	  <div class="col-md-12">
	    <h1>Modificar producto:</h1>
	  </div>
	</div>
    <div class="row">
	  <div class="col-md-12">
		<form:form method="post" action="modifyProductoForm">
		<div style="display: none;">
			<form:input path="id"/>
		</div>
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
		   <br>
		  <div class="form-group row">
		    <label for="inputDescripcion" class="col-sm-2 col-form-label">Detalle:</label>
		    <div class="col-sm-5">
		      <form:input path="descripcion" class="form-control" />
		    </div>
		  </div>		  
		  <button type="submit" class="btn btn-primary mb-2">Modificar</button>
	
		</form:form>
	  </div>
	</div>