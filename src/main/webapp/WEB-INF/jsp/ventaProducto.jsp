<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	



    <div class="row">
	  <div class="col-md-12">
	    <h1>Comprar producto:</h1>
	  </div>
	</div>
    <div class="row">
	  <div class="col-md-12">
		<form:form method="post" action="addVentaProductoForm">
			<div style="display: none;">
				<form:input path="idProducto"/>
			</div>
			<div class="form-group row">
				<label for="inputCantidad" class="col-sm-2 col-form-label">Cantidad permitida:</label>
			    <div class="col-sm-5">
			    	<form:input path="cantidadMaxima" readonly="true"/>
			    </div>
			</div>
			<div class="form-group row">
				<label for="inputCantidad" class="col-sm-2 col-form-label">Usuarios compradores:</label>
			    <div class="col-sm-5">
				    <form:select class="form-control" id="usuarios" path="idComprador">
						<form:options items="${usuarios}" itemLabel="email" itemValue="id"/>
					</form:select>
				</div>
			</div>
			<div class="form-group row">
			    <label for="inputCantidad" class="col-sm-2 col-form-label">Cantidad a comprar:</label>
			    <div class="col-sm-5">
			      <form:input path="cantidadComprada" class="form-control" required="required" pattern="[0-9][0-9]{0,5}"/>
			    </div>
			</div>  
			<button type="submit" class="btn btn-primary mb-2">Comprar</button>
		</form:form>
	  </div>
	</div>