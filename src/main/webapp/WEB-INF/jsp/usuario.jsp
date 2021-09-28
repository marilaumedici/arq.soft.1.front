<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	

    <div class="row">
	  	<div class="col-md-12">
	    	<h1>Usuarios:</h1>
	  	</div>
	</div>
    <div class="row">
    	<div class="col-md-12">
    		<form:form method="post" action="addUsuarioForm">
				<div class="form-group row">
				    <label for="staticNombre" class="col-sm-2 col-form-label">Nombre:</label>
				    <div class="col-sm-5">
				      <form:input path="nombre" class="form-control" required="required" minlength="5" maxlength="30" />
				    </div>
			  	</div>
			  	<br>
			  	<div class="form-group row">
				    <label for="staticApellido" class="col-sm-2 col-form-label">Apellido:</label>
				    <div class="col-sm-5">
				      <form:input path="apellido" class="form-control" required="required" minlength="5" maxlength="30" />
				    </div>
			  	</div>
			  	<br>
			  	<div class="form-group row">
				    <label for="inputEmail" class="col-sm-2 col-form-label">Email:</label>
				    <div class="col-sm-5">
				      <form:input path="email" class="form-control" minlength="3" maxlength="60"  />
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
    				<th scope="col">Nombre</th>
    				<th scope="col">Apellido</th>
    				<th scope="col">Email</th>
    			</tr>
    		</thead>
    		<tbody>
	    		<c:forEach items="${usuarios}" var="usuario">
			        <tr>
			            <td><c:out value="${usuario.nombre}"/></td>
			            <td><c:out value="${usuario.apellido}"/></td>
			            <td><c:out value="${usuario.email}"/></td>
			        </tr>
			    </c:forEach>
		  </tbody>
		</table>
	  </div>
	</div>