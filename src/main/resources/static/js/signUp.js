import {changePage} from "./utils/changePage.js";

const username = document.getElementById("username");
const email = document.getElementById("email");
const password = document.getElementById("password");
const form = document.getElementById("signUpForm");

async function signUp(event) {
    event.preventDefault();

    const csrfResponse = await fetch("/csrf");

    const csrf = await csrfResponse.json();

    const response = await fetch("/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrf.headerName]: csrf.token
        },
        body: JSON.stringify({
            email: email.value,
            display_name: username.value,
            password: password.value
        })
    });

    changePage("login");
}

form.addEventListener("submit",   signUp);