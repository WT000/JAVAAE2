// Function ran 8 seconds after a page is loaded
function clearResult() {
    document.getElementById("resultText").style.color = "black";
    
    if (currentForm == "propertiesForm") {
        document.getElementById("resultText").innerHTML = "Properties Controller";
    } else if (currentForm == "refundForm") {
        document.getElementById("resultText").innerHTML = "Refund to Card";
    } else {
        document.getElementById("resultText").innerHTML = "Admin Control Panel";
    }
};

// Function that displays text within the result div
function displayText(string) {
    document.getElementById("resultText").style.color = "black";
    document.getElementById("resultText").innerHTML = string;
};

// Function that displays a given form
function displayForm(name) {
    // Display the form container if it's not showing
    if (!showingFormContainer) {
        formContainer.style.display = "block";
    };

    // Display the form
    if (currentForm !== name) {
        if (currentForm !== null) {
            document.getElementById(currentForm).style.display = "none";
        };
        currentForm = name;
        window.sessionStorage.setItem("currentForm", currentForm);
        document.getElementById(currentForm).style.display = "block";
    };
};

// Function that gets the last loaded form and displays it
function displayPreviousForm() {
    toDisplay = window.sessionStorage.getItem("currentForm");
    
    if (toDisplay == "propertiesForm") {
        displayForm("propertiesForm");
    } else if (toDisplay == "refundForm") {
        displayForm("refundForm");
    };
};

// Get the forms and make them invisible
const formContainer = document.getElementById("formContainer");
const forms = document.getElementsByClassName("innerForm");
let showingFormContainer = false;
let currentForm = null;

for (let i = 0; i < forms.length; i++) {
    forms[i].style.display = "none";
};

displayPreviousForm();

// Properties Button
document.getElementById("buttonProperties").addEventListener("click", e => {
    displayForm("propertiesForm");
    displayText("Properties Controller");
});

// Refund Button
document.getElementById("buttonRefund").addEventListener("click", e => {
    displayForm("refundForm");
    displayText("Refund to Card")
});

// Properties form
document.getElementById("propertiesForm").addEventListener("submit", e => {
    e.preventDefault();
    let bankUrl = document.forms["propertiesForm"]["propertiesURL"].value;
    let bankUsername = document.forms["propertiesForm"]["propertiesUsername"].value;
    let bankPassword = document.forms["propertiesForm"]["propertiesPassword"].value;
    let bankCardNo = document.forms["propertiesForm"]["propertiesCard"].value;

    let foundError = false;

    if (bankUrl.trim() == "") {
        foundError = true;
        document.forms["propertiesForm"]["propertiesURL"].style.backgroundColor = "red";
    } else {
        document.forms["propertiesForm"]["propertiesURL"].style.backgroundColor = "white";
    }

    if (bankUsername.trim() == "") {
        foundError = true;
        document.forms["propertiesForm"]["propertiesUsername"].style.backgroundColor = "red";
    } else {
        document.forms["propertiesForm"]["propertiesUsername"].style.backgroundColor = "white";
    }

    if (bankPassword.trim() == "") {
        foundError = true;
        document.forms["propertiesForm"]["propertiesPassword"].style.backgroundColor = "red";
    } else {
        document.forms["propertiesForm"]["propertiesPassword"].style.backgroundColor = "white";
    }

    if (bankCardNo.trim() == "" || bankCardNo.length !== 16 || isNaN(bankCardNo)) {
        foundError = true;
        document.forms["propertiesForm"]["propertiesCard"].style.backgroundColor = "red";
    } else {
        document.forms["propertiesForm"]["propertiesCard"].style.backgroundColor = "white";
    }
    
    if (foundError) {
        document.getElementById("resultText").innerHTML = "ERROR - Bank information is incorrect.";
        document.getElementById("resultText").style.color = "red";
    } else {
        displayText("Applying properties...");
        document.getElementById("propertiesForm").submit();
    }
});

// Check refund form
document.getElementById("refundForm").addEventListener("submit", e => {
    e.preventDefault();
    let cardNo = document.forms["refundForm"]["cardNumber"].value;
    let amount = document.forms["refundForm"]["amount"].value;

    let foundError = false;
    
    if (cardNo.trim() == "" || cardNo.length !== 16 || isNaN(cardNo)) {
        foundError = true;
        document.forms["refundForm"]["cardNumber"].style.backgroundColor = "red";
    } else {
        document.forms["refundForm"]["cardNumber"].style.backgroundColor = "white";
    }
    
    if (amount.trim() == "" || isNaN(amount) || parseFloat(amount) < 0.01) {
        foundError = true;
        document.forms["refundForm"]["amount"].style.backgroundColor = "red";
    } else {
        document.forms["refundForm"]["amount"].style.backgroundColor = "white";
    }

    if (foundError) {
        document.getElementById("resultText").innerHTML = "ERROR - Please follow the guidance in the placeholders.";
        document.getElementById("resultText").style.color = "red";
    } else {
        displayText("Sending refund...");
        document.getElementById("refundForm").submit();
    };
});

setTimeout(() => {  clearResult(); }, 8000);
