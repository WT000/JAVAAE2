// Stage of the transaction: 0: cardno, 1: card name, 2: card date, 3: card cvv, 4: amount, 5: review
let currentStage = 0;
console.log("Starting at stage " + currentStage)
let limits = [[16, 16], [1, 35], [5, 7], [3, 3], [1, 8]];

// Store the entered details when needed
let currentNumber = null;
let currentName = null;
let currentDate = null;
let currentCvv = null;
let currentAmount = null;
let containsDecimal = false;
let containsSpace = false;

let currentText = "Please enter your card number.";

// Get validMonths and append them to the list
let validMonths = [];

for (i=1; i<13; i++) {
    if (i<10) {
        validMonths.push(`0${i}`);
    } else {
        validMonths.push(`${i}`);
    }
};

// Function ran 5 seconds after a page is loaded
function clearResult() {
    document.getElementById("resultText").style.color = "black";
    document.getElementById("resultText").innerHTML = currentText;
};

// Function that displays text within the result div
function displayText(string, color="black") {
    document.getElementById("resultText").style.color = color;
    document.getElementById("resultText").innerHTML = string;
};

// Function that displays the text assigned to a number which the users clicked on (numbers)
function addText(element) {
    let currentLength = document.getElementById("inputResult").innerText.length
    let shouldAdd = false;
    let firstZeroAmount = false;
    
    // Only allow input if we're not at the finished stage and are less than the max limit
    if (currentStage <= 4) {
        if (currentLength < limits[currentStage][1]) {
            // Automatically add a /
            if (currentStage == 2 && currentLength == 1) {
                document.getElementById("inputResult").innerText += element.innerText + "/";
            // Automatically add 0. if starting with 0 or .
            } else if (currentStage == 4 && currentLength == 0 && !containsDecimal && (element.innerText == "0" || element.innerText == ".")) {
                document.getElementById("inputResult").innerText += "0.";
                containsDecimal = true;
            // General rules
            } else {
                if (element.innerText == "." && currentStage == 4 && !containsDecimal) {
                    // If adding a decimal place on amount
                    document.getElementById("inputResult").innerText += element.innerText;
                    containsDecimal = true;
                
                } else if (element.innerText !== ".") {
                    // All other cases that aren't a .
                    document.getElementById("inputResult").innerText += element.innerText;
                }
            }
        }
    }
};
// Function that wipes the program back to default
function attemptBack() {
    // Reset stage and text
    currentStage = 0;
    document.getElementById("inputResult").innerText = "";
    
    // Reset stored values
    currentNumber = null;
    currentName = null;
    currentDate = null;
    currentCvv = null;
    currentAmount = null;
    containsDecimal = false;
    containsSpace = false;
    
    // Hide the keyboard and show the numpad
    document.getElementById("formContainerKeypad").style.display = "flex";
    document.getElementById("formContainerKeyboard").style.display = "none";
    
    // Display that the transaction is cancelled, then prepare the new message
    displayText("Transaction cancelled.")
    currentText = "Please enter your card number.";
    setTimeout(() => {  clearResult(); }, 4000);
    
    // Log to console
    console.log("Going back to stage " + currentStage)
};


// Function that removes the most recent text (<)
function removeText() {
    let newText = document.getElementById("inputResult").innerText;
    if (currentStage == 2 && newText.charAt(newText.length-1) == "/") {
        newText = newText.slice(0, -2);
    
    // Slice extra if entering a date and hits a /
    } else if (currentStage <= 4) {
        if (newText.charAt(newText.length-1) == ".") {
            containsDecimal = false;
        } else if (newText.charAt(newText.length-1) == " ") {
            containsSpace = false;
        }
        newText = newText.slice(0, -1);
    }
    
    document.getElementById("inputResult").innerText = newText;
};

// Function that attempts to submit the current text (O)
function attemptSubmit() {
    let userDetail = document.getElementById("inputResult").innerText;
    let foundError = false;
    
    if (currentStage == 0) {
        // card check
        if (userDetail.trim() == "" || userDetail.length !== 16 || isNaN(userDetail)) {
            foundError = true;
            displayText("ERROR - Please enter an appropriate card.", "red")
            setTimeout(() => {  clearResult(); }, 4000);
        } else {
            currentNumber = userDetail;
            currentText = "Please enter your name on card.";
            // Show the keyboard and hide the numpad
            document.getElementById("formContainerKeypad").style.display = "none";
            document.getElementById("formContainerKeyboard").style.display = "flex";
        }
        
    } else if (currentStage == 1) {
        // name check
        if (userDetail.trim() == "") {
            foundError = true;
            displayText("ERROR - Please enter an appropriate name.", "red")
            setTimeout(() => {  clearResult(); }, 4000);
        } else {
            currentName = userDetail;
            currentText = "Please enter your cards expiry date (MM/YY or MM/YYYY).";
            // Hide the keyboard and show the numpad
            document.getElementById("formContainerKeypad").style.display = "flex";
            document.getElementById("formContainerKeyboard").style.display = "none";
        }
        
    } else if (currentStage == 2) {
        // date check
        let cardMonth = userDetail[0] + userDetail[1];
        
        if (userDetail.trim() == "" || !(userDetail.length == 5 || userDetail.length == 7) || userDetail[2] !== "/" || !(validMonths.includes(cardMonth))) {
            foundError = true;
            displayText("ERROR - Please enter an appropriate expiration date.", "red")
            setTimeout(() => {  clearResult(); }, 4000);
        } else {
            currentDate = userDetail;
            currentText = "Please enter your cards cvv.";
        }
        
    } else if (currentStage == 3) {
        // cvv check
        if (userDetail.trim() == "" || userDetail.length !== 3 || isNaN(userDetail)) {
            foundError = true;
            displayText("ERROR - Please enter an appropriate cvv.", "red")
            setTimeout(() => {  clearResult(); }, 4000);
        } else {
            currentCvv = userDetail;
            currentText = "Please enter the amount to send in GBP.";
        }
        
    } else if (currentStage == 4) {
        // amount
        if (userDetail.trim() == "" || isNaN(userDetail) || parseFloat(userDetail) < 0.01 || parseFloat(userDetail) > 10000000) {
            foundError = true;
            displayText("ERROR - Please enter an appropriate amount.", "red")
            setTimeout(() => {  clearResult(); }, 4000);
        } else {
            currentAmount = userDetail;
            currentText = "Submit your transaction.";
        }
        
    } else if (currentStage == 5) {
        // put into the form and submit
        document.forms["transactionForm"]["cardNumber"].value = currentNumber;
        document.forms["transactionForm"]["cardName"].value = currentName;
        document.forms["transactionForm"]["cardDate"].value = currentDate;
        document.forms["transactionForm"]["cardCvv"].value = currentCvv;
        document.forms["transactionForm"]["amount"].value = currentAmount;
        
        displayText("Sending transaction...");
        document.getElementById("transactionForm").submit();
    }
    
    if (!foundError && currentStage !== 5) {
        currentStage++;
        console.log("Going to stage " + currentStage)
        clearResult();
        
        if (currentStage !== 5) {
            document.getElementById("inputResult").innerText = "";
        } else {
            let summary = `Card Number: ${currentNumber}, Card Name: ${currentName},
Card Date: ${currentDate}, Card Cvv: ${currentCvv}, Amount: £${currentAmount}`;
            document.getElementById("inputResult").innerText = summary;
        }
    }
};

// Innertext doesn't seem to work with spaces, so we'll ensure only one space can be performed here instead
document.getElementById("spaceBar").addEventListener("click", e=>{
    if (!containsSpace && document.getElementById("inputResult").innerText.length > 0) {
        document.getElementById("inputResult").innerText += " ";
        containsSpace = true;
    };
});

// Clear result after page reset
setTimeout(() => {  clearResult(); }, 4000);
