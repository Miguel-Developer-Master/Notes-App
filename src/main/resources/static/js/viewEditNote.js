import {changePage} from "./utils/changePage.js";

const title = document.getElementById("note-title");
const content = document.getElementById("note-content");

function resize() {
    content.style.height = "auto";
    content.style.height = `${content.scrollHeight}px`;
}
content.addEventListener("input", resize);

const saveButton = document.getElementById("save-button");
const cancelButton = document.getElementById("cancel-button");
const action = document.getElementById("action-buttons");
const main = document.querySelector("main");

async function saveNote() {

    const params = new URLSearchParams(window.location.search);
    const id = Number(params.get("id"));

    const response = await fetch("/csrf");
    const csrf = await response.json();

    const today = new Date().toISOString().split('T')[0];

    const run = (!(title.value === null) && !(title.value.trim().length <= 0) && (title.value.length <= 100)) && (!(content.value === null) && !(content.value.trim().length <= 0) && content.value.length <= 10000);

    if (run) {
        const response = await fetch(`/notes/update-note-by-id`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                [csrf.headerName]: csrf.token
            },
            body: JSON.stringify({
                id: id,
                title: title.value,
                content: content.value,
                updated_at: today
            })
        });

        if (response.ok) {
            changePage("");
        }
    } else {

        const errorMessage = document.createElement("p");
        errorMessage.textContent = "Invalid note!";
        errorMessage.style.color = "red";

        main.insertBefore(errorMessage, action);
    }
}

async function viewNote() {

    const params = new URLSearchParams(window.location.search);
    const id = Number(params.get("id"));

    const response = await fetch(`/notes/get-note-by-id/${id}`);
    const note = await response.json();

    title.value = note.title;
    content.value = note.content;
    resize();
}

await viewNote();
saveButton.addEventListener("click", saveNote);
cancelButton.addEventListener("click", () => changePage(""));