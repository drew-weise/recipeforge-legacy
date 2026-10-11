<%@ include file="taglib.jsp" %>
<c:set var="title" value="RecipeForge Recipes" />
<%@ include file="head.jsp" %>
<html>
<body>

<c:choose>
    <c:when test="${empty userName}">
        <a href = "logIn">Log in</a>
    </c:when>
    <c:otherwise>
        <h3>Welcome ${sessionScope.userName}</h3>
    </c:otherwise>
</c:choose>

</body>
</html>