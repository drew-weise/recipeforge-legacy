import { useState } from "react";

function App() {
    const [message, setMessage] = useState("");

    async function syncUser() {
        try {
            const response = await fetch(
                "http://localhost:8080/recipeforge_war/api/user/sync",
                {
                    method: "POST",
                    credentials: "include"
                }
            );

            if (response.status === 401) {
                window.location.href = "http://localhost:8080/recipeforge_war/logIn";

                return;
            }

            const responseText = await response.text();

            console.log("HTTP status:", response.status);
            console.log("Response body:", responseText);

            if (!response.ok) {
                setMessage(
                    `Backend returned ${response.status}: ${responseText}`
                );
                return;
            }

            setMessage(`Success: ${responseText}`);
        } catch (error) {
            console.error("User sync failed:", error);

            setMessage(
                error instanceof Error
                    ? error.message
                    : String(error)
            );
        }
    }

    return (
        <main>
            <h1>RecipeForge</h1>

            <button onClick={syncUser}>
                Sync Local User
            </button>

            <p>{message}</p>
        </main>
    );
}

export default App;