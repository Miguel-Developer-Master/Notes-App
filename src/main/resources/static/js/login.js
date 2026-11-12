import {changePage} from "./utils/changePage.js";

const loginForm = document.querySelector("#login-form");
const email = document.querySelector("#email");
const password = document.querySelector("#password");
const rememberMe = document.querySelector("#remember-me");
const errorDiv = document.querySelector("#some-content-div");

async function login(event) {
    event.preventDefault();

    const csrfResponse = await fetch("/csrf");
    const csrf = await csrfResponse.json();

    const response = await fetch(`/users/login?remember-me=${rememberMe.checked}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrf.headerName]: csrf.token
        },
        body: JSON.stringify({
            email: email.value,
            password: password.value
        })
    });

    const authenticated = await response.json();

    if (!authenticated) {

        const errorParagraph = document.createElement("p");
        errorParagraph.textContent = "Email or password are incorrect";
        errorParagraph.style.color = "red";
        errorDiv.append(errorParagraph);
    } else {
        changePage("");
    }
}

loginForm.addEventListener("submit", login);