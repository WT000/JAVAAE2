// Function ran 8 seconds after a page is loaded
function clearResult() {
    document.getElementById("resultText").style.color = "black";
    document.getElementById("resultText").innerHTML = "Please enter your details and an amount to send below.";
};

// Function that displays text within the result div
function displayText(string) {
    document.getElementById("resultText").style.color = "black";
    document.getElementById("resultText").innerHTML = string;
};

// Get validMonths and append them to the list
let validMonths = [];

for (i=1; i<13; i++) {
    if (i<10) {
        validMonths.push(`0${i}`);
    } else {
        validMonths.push(`${i}`);
    }
};

// Check addCard form
document.getElementById("transactionForm").addEventListener("submit", e => {
    e.preventDefault();
    let cardNo = document.forms["transactionForm"]["cardNumber"].value;
    let cardName = document.forms["transactionForm"]["cardName"].value;
    let cardDate = document.forms["transactionForm"]["cardDate"].value;
    let cardCvv = document.forms["transactionForm"]["cardCvv"].value;
    let amount = document.forms["transactionForm"]["amount"].value;

    let foundError = false;
    
    if (cardNo.trim() == "" || cardNo.length !== 16 || isNaN(cardNo)) {
        foundError = true;
        document.forms["transactionForm"]["cardNumber"].style.backgroundColor = "red";
    } else {
        document.forms["transactionForm"]["cardNumber"].style.backgroundColor = "white";
    }

    if (cardName.trim() == "") {
        foundError = true;
        document.forms["transactionForm"]["cardName"].style.backgroundColor = "red";
    } else {
        document.forms["transactionForm"]["cardName"].style.backgroundColor = "white";
    }

    let cardMonth = cardDate[0] + cardDate[1];
    
    // Note that if it expires in a month, then it'll still be valid on the final day of that month
    if (cardDate.trim() == "" || !(cardDate.length == 5 || cardDate.length == 7) || cardDate[2] !== "/" || !(validMonths.includes(cardMonth))) {
        foundError = true;
        document.forms["transactionForm"]["cardDate"].style.backgroundColor = "red";
    } else {
        document.forms["transactionForm"]["cardDate"].style.backgroundColor = "white";
    }

    if (cardCvv.trim() == "" || cardCvv.length !== 3 || isNaN(cardCvv)) {
        foundError = true;
        document.forms["transactionForm"]["cardCvv"].style.backgroundColor = "red";
    } else {
        document.forms["transactionForm"]["cardCvv"].style.backgroundColor = "white";
    }
    
    if (amount.trim() == "" || isNaN(amount) || parseFloat(amount) < 0.01) {
        foundError = true;
        document.forms["transactionForm"]["amount"].style.backgroundColor = "red";
    } else {
        document.forms["transactionForm"]["amount"].style.backgroundColor = "white";
    }

    if (foundError) {
        document.getElementById("resultText").innerHTML = "ERROR - Please follow the guidance in the placeholders.";
        document.getElementById("resultText").style.color = "red";
    } else {
        displayText("Sending transaction...");
        document.getElementById("transactionForm").submit();
    };
});

setTimeout(() => {  clearResult(); }, 8000);
