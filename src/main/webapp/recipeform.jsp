<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="Create Recipe" />
<jsp:include page="head.jsp" />

<html>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="btn btn-outline-light"
           href="${pageContext.request.contextPath}/user">
            Back to Account
        </a>
    </div>
</nav>

<main class="container py-5">

    <h1 class="mb-4">Create a New Recipe</h1>

    <form method="post"
          action="${pageContext.request.contextPath}/recipe">

        <div class="mb-3">
            <label for="title" class="form-label">
                Recipe title
            </label>

            <input type="text"
                   id="title"
                   name="title"
                   class="form-control"
                   required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">
                Description
            </label>

            <textarea id="description"
                      name="description"
                      class="form-control"
                      rows="3"
                      required></textarea>
        </div>

        <div class="mb-3">
            <label for="instructions" class="form-label">
                Instructions
            </label>

            <textarea id="instructions"
                      name="instructions"
                      class="form-control"
                      rows="8"
                      required></textarea>
        </div>

        <div class="mb-3">
            <label for="servings" class="form-label">
                Servings
            </label>

            <input type="number"
                   id="servings"
                   name="servings"
                   class="form-control"
                   min="1"
                   required>
        </div>

        <button type="submit" class="btn btn-dark">
            Save Recipe
        </button>

        <a href="${pageContext.request.contextPath}/user"
           class="btn btn-secondary">
            Cancel
        </a>

    </form>

</main>

</body>
</html>