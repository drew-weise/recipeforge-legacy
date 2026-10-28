<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="My RecipeForge Account" />

<jsp:include page="head.jsp" />

<body class="bg-light">

<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/">
            RecipeForge
        </a>

        <a class="btn btn-outline-light"
           href="${pageContext.request.contextPath}/logOut">
            Log out
        </a>
    </div>
</nav>

<main class="container py-5">

    <div class="mb-4">
        <h1>My Account</h1>

        <p class="text-muted mb-1">Welcome, ${user.displayName}</p>

        <p class="text-muted">${user.email}</p>
    </div>

    <hr>

    <h2 class="h4 mt-4 mb-3">My Recipes</h2>

    <c:choose>

        <c:when test="${empty user.recipes}">
            <div class="alert alert-info">
                You have not created any recipes yet.
            </div>
        </c:when>

        <c:otherwise>
            <div class="row g-4">

                <c:forEach var="recipe" items="${user.recipes}">
                    <div class="col-md-6 col-lg-4">

                        <div class="card h-100 shadow-sm">
                            <div class="card-body">

                                <h3 class="h5 card-title">${recipe.recipeTitle}</h3>

                                <p class="card-text text-muted">${recipe.recipeDescription}</p>

                                <p class="mb-0"><strong>Servings:</strong>${recipe.recipeServings}</p>

                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </c:otherwise>

    </c:choose>

</main>

</body>
</html>