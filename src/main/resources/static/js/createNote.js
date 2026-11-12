import {changePage} from "./utils/changePage.js";

const form = document.getElementById("create-note-form");
const textarea = document.getElementById("content");
const submitButton = document.getElementById("submit-button");

textarea.addEventListener("input", () => {
    textarea.style.height = "auto";
    textarea.style.height = `${textarea.scrollHeight}px`;
});

async function createNote(event) {
    event.preventDefault();

    const noteTitle = document.getElementById("title").value;
    const noteContent = document.getElementById("content").value;

    const userEmailResponse = await fetch("/users/get-current-authenticated-user-email");
    const email = await userEmailResponse.text();

    const idResponse = await fetch(`/users/get-user-id-by-email/${email}`);
    const id = await idResponse.text();

    const csrfResponse = await fetch(`/csrf`);
    const csrf = await csrfResponse.json();

    const run = (!(noteTitle === null) && !(noteTitle.trim() === "") && (noteTitle.trim().length <= 100)) && (!(noteContent === null) && !(noteContent.trim() === "") && (noteContent.trim().length <= 10000));

    if (run) {

        const response = await fetch("/notes/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [csrf.headerName]: csrf.token
            },
            body: JSON.stringify({
                title: noteTitle,
                content: noteContent,
                user_id: id
            })
        });

        if (response.ok) {
            changePage("");
        }

    } else {

        const errorMessage = document.createElement("p");
        errorMessage.textContent = "Invalid note!";
        errorMessage.style.color = "blue";

        form.insertBefore(errorMessage, submitButton);
    }

}

form.addEventListener("submit", (event) => createNote(event));