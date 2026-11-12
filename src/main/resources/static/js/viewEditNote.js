import {changePage} from "./utils/changePage.js";

const title = document.getElementById("note-title");
const content = document.getElementById("note-content");
content.addEventListener("input", () => {
    content.style.height = "auto";
    content.style.height = `${content.scrollHeight}px`;
});

const saveButton = document.getElementById("save-button");
const cancelButton = document.getElementById("cancel-button");

async function saveNote() {

    const params = new URLSearchParams(window.location.search);
    const id = Number(params.get("id"));

    const response = await fetch("/csrf");
    const csrf = await response.json();

    const today = new Date().toISOString().split('T')[0];

    await fetch(`/notes/update-note-by-id`, {
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
}

async function viewNote() {

    const params = new URLSearchParams(window.location.search);
    const id = Number(params.get("id"));

    const response = await fetch(`/notes/get-note-by-id/${id}`);
    const note = await response.json();

    title.value = note.title;
    content.value = note.content;
}

await viewNote();
saveButton.addEventListener("click", saveNote);
cancelButton.addEventListener("click", () => changePage(""));