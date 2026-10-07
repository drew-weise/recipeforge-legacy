<%@ include file="taglib.jsp" %>
<c:set var="title" value="Users" />
<%@ include file="head.jsp" %>

<script type="text/javascript" class="init">
    $(document).ready(function () {
        $('#userTable').DataTable();
    });
</script>

<body>
<h1>Users</h1>

<div class="container-fluid">
    <table id="userTable" class="display" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>Display Name</th>
            <th>Recipes</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.displayName}</td>

                <td>
                    <c:forEach var="recipe" items="${user.recipes}">
                        <ul>
                            <li>${recipe.recipeTitle}</li>
                            <li>Servings: ${recipe.recipeServings}</li>
                            <li>${recipe.recipeDescription}</li>
                            <li>${recipe.recipeInstructions}</li>

                            <li>
                                Ingredients:
                                <ul>
                                    <c:forEach
                                            var="recipeIngredient"
                                            items="${recipe.recipeIngredients}">

                                        <li>
                                                ${recipeIngredient.amount}
                                                ${recipeIngredient.ingredient.ingredientName}
                                        </li>

                                    </c:forEach>
                                </ul>
                            </li>
                        </ul>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>