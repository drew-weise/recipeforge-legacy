import { useState, useEffect } from 'react';

type Recipe = {
    recipeId: number;
    recipeTitle: string;
    recipeServings: number;
};

function App() {
    const [recipeList, setRecipes] = useState<Recipe[]>([]);

    useEffect(() => {
        fetch('/api/recipes')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setRecipes(data);
            });
    }, []);

  return (
      <main>
        <h1>Provisions</h1>
        <p>Your personal food-planning space.</p>

          {recipeList.map(recipe => (
              <div key={recipe.recipeId}>
                  <h2>{recipe.recipeTitle}</h2>
                  <p>Servings: {recipe.recipeServings}</p>
              </div>
          ))}

      </main>
  );
}

export default App;
