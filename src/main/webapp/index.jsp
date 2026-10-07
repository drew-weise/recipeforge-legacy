<%@ include file="taglib.jsp" %>
<c:set var="title" value="RecipeForge Recipes" />
<%@ include file="head.jsp" %>

<script type="text/javascript" class="init">
    $(document).ready(function () {
        $('#recipeTable').DataTable();
    });
</script>

<body>
<h1>RecipeForge Recipes</h1>

<div class="container-fluid">
    <table id="recipeTable" class="display" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>User</th>
            <th>Recipe</th>
            <th>Servings</th>
            <th>Description</th>
            <th>Ingredients</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${users}">
            <c:forEach var="recipe" items="${user.recipes}">
                <tr>
                    <td>${user.displayName}</td>
                    <td>${recipe.recipeTitle}</td>
                    <td>${recipe.recipeServings}</td>
                    <td>${recipe.recipeDescription}</td>

                    <td>
                        <c:forEach
                                var="recipeIngredient"
                                items="${recipe.recipeIngredients}">

                            ${recipeIngredient.amount}
                            ${recipeIngredient.ingredient.ingredientName}<br>

                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>