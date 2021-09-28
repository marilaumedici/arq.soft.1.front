<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	

    <div class="row">
	  	<div class="col-md-12">
	    	<h1>Vendedores:</h1>
	  	</div>
	</div>
    <div class="row">
    	<div class="col-md-12">
    		<form:form method="post" action="addVendedorForm">
				<div class="form-group row">
				    <label for="staticRazonSocial" class="col-sm-2 col-form-label">Raz&oacute;n social:</label>
				    <div class="col-sm-5">
				      <form:input path="razonSocial" class="form-control" />
				    </div>
			  	</div>
			  	<br>
			  	<div class="form-group row">
				    <label for="inputEmail" class="col-sm-2 col-form-label">Email:</label>
				    <div class="col-sm-5">
				      <form:input path="email" class="form-control"  />
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
    				<th scope="col">Raz&oacute;n social</th>
    				<th scope="col">Email</th>
    			</tr>
    		</thead>
    		<tbody>
	    		<c:forEach items="${vendedores}" var="vendedor">
			        <tr>
			            <td><c:out value="${vendedor.razonSocial}"/></td>
			            <td><c:out value="${vendedor.email}"/></td>
			        </tr>
			    </c:forEach>
		  </tbody>
		</table>
	  </div>
	</div>