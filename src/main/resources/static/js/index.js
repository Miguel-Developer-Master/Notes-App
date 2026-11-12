import {changePage} from "./utils/changePage.js";

const createNoteButton = document.getElementById("create-note-button");

createNoteButton.addEventListener("click", () => changePage("notes/create"));

const logoutButton = document.getElementById("logout-button");
const aboutMeButton = document.getElementById("about-me");
const footer = document.querySelector("footer");

aboutMeButton.addEventListener("click", () => {
    footer.scrollIntoView({
        behavior: "smooth",
        block: "start",
    })
});


async function logout() {

    const csrfResponse = await fetch("/csrf");
    const csrf = await csrfResponse.json();

    await fetch("/logout", {
        method: "POST",
        headers: {
            [csrf.headerName]: csrf.token,
        }
    })

    changePage("login");
}

logoutButton.addEventListener("click", logout);

// adding making notes visible

const notesSection = document.getElementById("all-notes");
async function showNotes() {

    const notesResponse = await fetch(`/notes/get-all-by-current-user`);
    const notes = await notesResponse.json();

    for (const note of notes) {

        const noteDiv = document.createElement("div");
        noteDiv.classList.add("notes-div");

        const contentDiv = document.createElement("div");
        contentDiv.classList.add("content-div");

        const actionDiv = document.createElement("div");
        actionDiv.classList.add("action-div");
        actionDiv.id = note.id;

        const h3 = document.createElement("h3");

        const link = document.createElement("a");
        link.href = `/notes/view?id=${note.id}`;
        link.textContent = note.title;

        const firstFifteenLetters = document.createElement("p");
        const content = note.content ?? "";
        let textToPut = content.substring(0, 15);
        if (content.length > textToPut.length) {
            textToPut += "...";
        }
        firstFifteenLetters.textContent = textToPut;

        const updatedAt = document.createElement("p");
        updatedAt.textContent = `Updated at: ${note.updated_at.replaceAll('-', '/')}`;

        const deleteButton = document.createElement("button");
        deleteButton.classList.add("delete-note-button");
        deleteButton.innerHTML = "<svg\n" +
            "                                class=\"delete-note-svg\"\n" +
            "                                width=\"32px\"\n" +
            "                                height=\"32px\"\n" +
            "                                viewBox=\"0 0 24 24\"\n" +
            "                                fill=\"none\"\n" +
            "                                xmlns=\"http://www.w3.org/2000/svg\"\n" +
            "                            >\n" +
            "                                <path\n" +
            "                                        d=\"M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M18 6V16.2C18 17.8802 18 18.7202 17.673 19.362C17.3854 19.9265 16.9265 20.3854 16.362 20.673C15.7202 21 14.8802 21 13.2 21H10.8C9.11984 21 8.27976 21 7.63803 20.673C7.07354 20.3854 6.6146 19.9265 6.32698 19.362C6 18.7202 6 17.8802 6 16.2V6M14 10V17M10 10V17\"\n" +
            "                                        stroke=\"currentColor\"\n" +
            "                                        stroke-width=\"2\"\n" +
            "                                        stroke-linecap=\"round\"\n" +
            "                                        stroke-linejoin=\"round\"\n" +
            "                                />\n" +
            "                            </svg>";

        contentDiv.append(h3);
        h3.append(link);
        contentDiv.append(firstFifteenLetters);
        contentDiv.append(updatedAt);
        actionDiv.append(deleteButton);

        noteDiv.append(contentDiv);
        noteDiv.append(actionDiv);
        notesSection.append(noteDiv);
    }
}
await showNotes();

const deleteButtons = document.querySelectorAll(".delete-note-button");

async function deleteNotes(event) {
    const id = event.target.closest(".action-div").id;

    const csrfResponse = await fetch("/csrf");
    const csrf = await csrfResponse.json();

    const run = confirm("Are you sure you want to delete this note?");
    if (run) {
        await fetch(`/notes/delete-note-by-id/${id}`, {
            method: "DELETE",
            headers: {
                [csrf.headerName]: csrf.token
            }
        });

        changePage("");
    }

}

for (const button of deleteButtons) {
    button.addEventListener("click", deleteNotes);
}


/*
P.S:
As I did not create a "create notes" page, I don't have how to test, and there are probably some bugs.
 */