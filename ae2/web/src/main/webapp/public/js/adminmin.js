// Login form
document.getElementById("loginForm").addEventListener("submit", e => {
    e.preventDefault();
    let bankUsername = document.forms["loginForm"]["propertiesUsername"].value;
    let bankPassword = document.forms["loginForm"]["propertiesPassword"].value;

    let foundError = false;

    if (bankUsername.trim() == "") {
        foundError = true;
        document.forms["loginForm"]["propertiesUsername"].style.backgroundColor = "red";
    } else {
        document.forms["loginForm"]["propertiesUsername"].style.backgroundColor = "white";
    }

    if (bankPassword.trim() == "") {
        foundError = true;
        document.forms["loginForm"]["propertiesPassword"].style.backgroundColor = "red";
    } else {
        document.forms["loginForm"]["propertiesPassword"].style.backgroundColor = "white";
    }
    
    if (foundError) {
        document.getElementById("resultText").innerHTML = "ERROR - Please enter a username and password.";
        document.getElementById("resultText").style.color = "red";
    } else {
        document.getElementById("loginForm").submit();
    }
});
