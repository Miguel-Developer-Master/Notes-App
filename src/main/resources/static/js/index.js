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

        noteDiv.append(h3);
        h3.append(link);
        noteDiv.append(firstFifteenLetters);
        noteDiv.append(updatedAt);

        notesSection.append(noteDiv);
    }
}

await showNotes();

/*
P.S:
As I did not create a "create notes" page, I don't have how to test, and there are probably some bugs.
 */