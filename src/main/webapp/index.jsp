<%@include file="taglib.jsp"%>
<c:set var="title" value="Search Results" />
<%@include file="head.jsp"%>

<script type="text/javascript" class="init">
    $(document).ready( function () {
        $('#userTable').DataTable();
    } );
</script>
<html>
<body>

<div class="container-fluid">
    <h2>Search Results: </h2>
    <table id="userTable" class="display" cellspacing="0" width="100%">
        <thead>
        <th>Username</th>
        <th>Recipes</th>
        </thead>

        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.displayName}</td>
<%--                <td>--%>
<%--                    <c:forEach var="recipe" items="${user}">--%>
<%--                        <li>${recipe.title + recipe.servings}</li>--%>
<%--                        <li>${recipe.description}</li>--%>
<%--                        <li>${recipe.instrictions}</li>--%>
<%--                    </c:forEach>--%>
<%--                </td>--%>
            </tr>


        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
