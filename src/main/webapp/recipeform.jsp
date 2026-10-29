<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="Create Recipe" />

<jsp:include page="head.jsp" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Recipe</title>
</head>

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

        <div class="mb-4">
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

        <h2 class="h4 mb-3">Ingredients</h2>

        <div id="ingredients-container"></div>

        <button type="button"
                id="add-ingredient"
                class="btn btn-outline-primary mb-4">
            + Add Ingredient
        </button>

        <div>
            <button type="submit"
                    class="btn btn-dark">
                Save Recipe
            </button>

            <a href="${pageContext.request.contextPath}/user"
               class="btn btn-secondary">
                Cancel
            </a>
        </div>

    </form>

</main>

<script>
    const ingredientsContainer =
        document.getElementById("ingredients-container");

    const addIngredientButton =
        document.getElementById("add-ingredient");

    function addIngredientRow() {

        const ingredientRow =
            document.createElement("div");

        ingredientRow.className =
            "row mb-3 ingredient-row";

        ingredientRow.innerHTML = `
            <div class="col-md-7">
                <input type="text"
                       name="ingredientName"
                       class="form-control"
                       placeholder="Example: Chicken breast">
            </div>

            <div class="col-md-4">
                <input type="text"
                       name="ingredientAmount"
                       class="form-control"
                       placeholder="Example: 2 breasts">
            </div>

            <div class="col-md-1 d-flex align-items-end">
                <button type="button"
                        class="btn btn-outline-danger remove-ingredient">
                    Remove
                </button>
            </div>
        `;

        ingredientsContainer.appendChild(ingredientRow);
    }

    addIngredientButton.addEventListener(
        "click",
        addIngredientRow
    );

    ingredientsContainer.addEventListener(
        "click",
        function (event) {

            if (event.target.classList.contains("remove-ingredient")) {

                const ingredientRow =
                    event.target.closest(".ingredient-row");

                ingredientRow.remove();
            }
        }
    );

    addIngredientRow();
</script>

</body>
</html>