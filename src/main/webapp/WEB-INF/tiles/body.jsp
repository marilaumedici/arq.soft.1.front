<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	

    Productos:
    <span>
	    <table style="margin-left:8%;border: 1px solid black;">
		    <c:forEach items="${productos}" var="producto">
		        <tr>
		            <td>Producto: <c:out value="${producto.nombre}"/></td>
		            <td>En stock: <c:out value="${producto.cantidad}"/></td>  
		        </tr>
		    </c:forEach>
		</table>
	</span>
	
	<span>
		<form:form method="post" action="addProductoForm">
		<table>
			<tr>
				<td><div><form:label path="nombre"><b>Nombre del producto:</b></form:label></div></td>
			    <td><form:input path="nombre" /></td>
			</tr>
			<tr>
				<td><div><form:label path="categoria"><b>Categoria:</b></form:label></div></td>
			    <td><form:input path="categoria" /></td>
			</tr>
			<tr>
				<td><div><form:label path="cantidad"><b>Cantidad:</b></form:label></div></td>
			    <td><form:input path="cantidad" /></td>
			</tr>
			<tr>
				<td>
					<input  type="submit" value="Agregar"/>
				</td>
			</tr>
		</table>
		</form:form>
	</span>