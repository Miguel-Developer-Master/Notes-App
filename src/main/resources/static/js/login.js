const loginForm = document.querySelector("#login-form");
const email = document.querySelector("#email");
const password = document.querySelector("#password");
const rememberMe = document.querySelector("#remember-me");

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

    const authenticated = await response.text();

    console.log(authenticated);
}

loginForm.addEventListener("submit", login);