<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Hello World!!!</h1>

<ul>
    <c:forEach var="person" items="${persons}">
        <li><c:out value="${person.firstName}"/> - <c:out value="${person.lastName}"/></li>
    </c:forEach>
</ul>