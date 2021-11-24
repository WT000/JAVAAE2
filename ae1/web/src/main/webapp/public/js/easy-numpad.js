// Edited version of the numpad by WT000, supports other types of numpad styles
// such as decimal, mm/yy, no decimal, etc

let _outputID = "";
let _minValue = null;
let _maxValue = null;
let _isInRange = true;
let _doingDate = false;

function show_easy_numpad(thisElement, type)
{
    _doingDate = false;
    let easy_numpad = document.createElement("div");
    easy_numpad.id = "easy-numpad-frame";
    easy_numpad.className = "easy-numpad-frame";
    
    if (type == "decimal") {
        
        easy_numpad.innerHTML = `
        <div class="easy-numpad-container">
            <div class="easy-numpad-output-container">
                <p class="easy-numpad-output" id="easy-numpad-output"></p>
            </div>
            <div class="easy-numpad-number-container">
                <table>
                    <tr>
                        <td><a href="1" onclick="easynum(this)">1</a></td>
                        <td><a href="2" onclick="easynum(this)">2</a></td>
                        <td><a href="3" onclick="easynum(this)">3</a></td>
                        <td><a href="Cancel" class="cancel" id="cancel" onclick="easy_numpad_cancel()">X</a></td>
                    </tr>
                    <tr>
                        <td><a href="4" onclick="easynum(this)">4</a></td>
                        <td><a href="5" onclick="easynum(this)">5</a></td>
                        <td><a href="6" onclick="easynum(this)">6</a></td>
                        <td><a href="Del" class="del" id="del" onclick="easy_numpad_del()"><</a></td>
                    </tr>
                    <tr>
                        <td><a href="7" onclick="easynum(this)">7</a></td>
                        <td><a href="8" onclick="easynum(this)">8</a></td>
                        <td><a href="9" onclick="easynum(this)">9</a></td>
                        <td><a href="Done" class="done" id="done" onclick="easy_numpad_done()">O</a></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="0"onclick="easynum(this)">0</a></td>
                        <td><a href="."onclick="easynum(this)">.</a></td>
                    </tr>
                </table>
            </div>
        </div>`;
    } else if (type == "number" || type == "date") {
        if (type == "date") {_doingDate = true;};
        
        easy_numpad.innerHTML = `
        <div class="easy-numpad-container">
            <div class="easy-numpad-output-container">
                <p class="easy-numpad-output" id="easy-numpad-output"></p>
            </div>
            <div class="easy-numpad-number-container">
                <table>
                    <tr>
                        <td><a href="1" onclick="easynum(this)">1</a></td>
                        <td><a href="2" onclick="easynum(this)">2</a></td>
                        <td><a href="3" onclick="easynum(this)">3</a></td>
                        <td><a href="Cancel" class="cancel" id="cancel" onclick="easy_numpad_cancel()">X</a></td>
                    </tr>
                    <tr>
                        <td><a href="4" onclick="easynum(this)">4</a></td>
                        <td><a href="5" onclick="easynum(this)">5</a></td>
                        <td><a href="6" onclick="easynum(this)">6</a></td>
                        <td><a href="Del" class="del" id="del" onclick="easy_numpad_del()"><</a></td>
                    </tr>
                    <tr>
                        <td><a href="7" onclick="easynum(this)">7</a></td>
                        <td><a href="8" onclick="easynum(this)">8</a></td>
                        <td><a href="9" onclick="easynum(this)">9</a></td>
                        <td><a href="Done" class="done" id="done" onclick="easy_numpad_done()">O</a></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="0"onclick="easynum(this)">0</a></td>
                        <td></td>
                    </tr>
                </table>
            </div>
        </div>`;
    } else if (type == "letter") {
        easy_numpad.innerHTML = `
        <div id="letterPad" class="easy-numpad-container">
            <div class="easy-numpad-output-container">
                <p class="easy-numpad-output" id="easy-numpad-output"></p>
            </div>
            <div class="easy-numpad-number-container">
                <table>
                    <tr>
                        <td><a href="Q" onclick="easynum(this)">Q</a></td>
                        <td><a href="W" onclick="easynum(this)">W</a></td>
                        <td><a href="E" onclick="easynum(this)">E</a></td>
                        <td><a href="R" onclick="easynum(this)">R</a></td>
                        <td><a href="T" onclick="easynum(this)">T</a></td>
                        <td><a href="Y" onclick="easynum(this)">Y</a></td>
                        <td><a href="U" onclick="easynum(this)">U</a></td>
                        <td><a href="I" onclick="easynum(this)">I</a></td>
                        <td><a href="O" onclick="easynum(this)">O</a></td>
                        <td><a href="P" onclick="easynum(this)">P</a></td>
                        <td></td>
                        <td></td>
                        <td><a href="Cancel" class="cancel" id="cancel" onclick="easy_numpad_cancel()">X</a></td>
                    </tr>
                    <tr>
                        <td><a href="A" onclick="easynum(this)">A</a></td>
                        <td><a href="S" onclick="easynum(this)">S</a></td>
                        <td><a href="D" onclick="easynum(this)">D</a></td>
                        <td><a href="F" onclick="easynum(this)">F</a></td>
                        <td><a href="G" onclick="easynum(this)">G</a></td>
                        <td><a href="H" onclick="easynum(this)">H</a></td>
                        <td><a href="J" onclick="easynum(this)">J</a></td>
                        <td><a href="K" onclick="easynum(this)">K</a></td>
                        <td><a href="L" onclick="easynum(this)">L</a></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><a href="Del" class="del" id="del" onclick="easy_numpad_del()"><</a></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="Z" onclick="easynum(this)">Z</a></td>
                        <td><a href="X" onclick="easynum(this)">X</a></td>
                        <td><a href="C" onclick="easynum(this)">C</a></td>
                        <td><a href="V" onclick="easynum(this)">V</a></td>
                        <td><a href="B" onclick="easynum(this)">B</a></td>
                        <td><a href="N" onclick="easynum(this)">N</a></td>
                        <td><a href="M" onclick="easynum(this)">M</a></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><a href="Done" class="done" id="done" onclick="easy_numpad_done()">O</a></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td colspan='5'><a href="&nbsp;"onclick="easynum(this)">&nbsp;</a></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </div>
        </div>`;
    };

    document.getElementsByTagName('body')[0].appendChild(easy_numpad);
    _outputID = thisElement.id;
    _minValue = document.getElementById(thisElement.id).getAttribute("min");
    _maxValue = document.getElementById(thisElement.id).getAttribute("max");
    
    let useDefault = document.getElementById(thisElement.id).getAttribute("data-easynumpad-use_default");
    if(useDefault != "false")
    {
        document.getElementById("easy-numpad-output").innerText = thisElement.value;
    }
}

function easy_numpad_close()
{
        let elementToRemove = document.querySelectorAll("div.easy-numpad-frame")[0];
        elementToRemove.parentNode.removeChild(elementToRemove);
        _isInRange = true;
}

function easynum(thisElement)
{
    event.preventDefault();

    let currentValue = document.getElementById("easy-numpad-output").innerText;
    
    switch(thisElement.innerText)
    {
        case "±":
            if(currentValue.startsWith("-"))
            {
                document.getElementById("easy-numpad-output").innerText = currentValue.substring(1,currentValue.length);
            }
            else
            {
                document.getElementById("easy-numpad-output").innerText = "-" + currentValue;
            }
        break;
        case ".":
            if(_isInRange)
            {
                if(currentValue.length === 0)
                {
                    document.getElementById("easy-numpad-output").innerText = "0";
                }
                else if(currentValue.length === 1 && currentValue === "-")
                {
                    document.getElementById("easy-numpad-output").innerText = currentValue + "0";
                }
                else
                {
                    if(currentValue.indexOf(".") < 0)
                    {
                        document.getElementById("easy-numpad-output").innerText += ".";
                    }
                }
            }
        break;
        default:
            if(_isInRange && document.getElementById("easy-numpad-output").innerText.length < parseInt(_maxValue))
            {
                if (document.getElementById("easy-numpad-output").innerText.length == 1 && _doingDate) {
                    document.getElementById("easy-numpad-output").innerText += (thisElement.innerText + "/");
                } else {
                    document.getElementById("easy-numpad-output").innerText += thisElement.innerText;
                }
            }
        break;
    }

    let newValue = Number(document.getElementById("easy-numpad-output").innerText);
    easy_numpad_check_range(newValue);
}

function easy_numpad_del()
{
    event.preventDefault();
    let easy_numpad_output_val = document.getElementById("easy-numpad-output").innerText;

    if (easy_numpad_output_val.slice(-2) == "0.") {
        var easy_numpad_output_val_deleted = easy_numpad_output_val.slice(0, -2);
    } else if (easy_numpad_output_val.slice(-1) == "/") {
        var easy_numpad_output_val_deleted = easy_numpad_output_val.slice(0, -2);
    } else {
        var easy_numpad_output_val_deleted = easy_numpad_output_val.slice(0, -1);
    }
    document.getElementById("easy-numpad-output").innerText = easy_numpad_output_val_deleted;
    easy_numpad_check_range(Number(easy_numpad_output_val_deleted));
}

function easy_numpad_clear()
{
    event.preventDefault();
    document.getElementById("easy-numpad-output").innerText="";
}

// if statements removed

function easy_numpad_cancel()
{
    event.preventDefault();
    easy_numpad_close();
}

function easy_numpad_done() 
{
    event.preventDefault();
    
    let easy_numpad_output_val = document.getElementById("easy-numpad-output").innerText;

    if(easy_numpad_output_val.indexOf(".") === (easy_numpad_output_val.length - 1))
    {
        easy_numpad_output_val = easy_numpad_output_val.substring(0,easy_numpad_output_val.length - 1);
    }

    document.getElementById(_outputID).value = easy_numpad_output_val;
    easy_numpad_close();
}

function easy_numpad_check_range(value)
{
    let outputElement = document.getElementById("easy-numpad-output");
    if(_maxValue !== null && _minValue !== null)
    {
        console.log("Range limit");
        if(outputElement.innerText.length < _maxValue && outputElement.innerText.length > _minValue)
        {
            outputElement.style.color = "black";
            _isInRange = true;
        }
        else
        {
            outputElement.style.color = "green";
            _isInRange = false;
        }
    }
    else if(_maxValue !== null)
    {
        console.log("Only upper limit");

        if(outputElement.innerText.length < _maxValue)
        {
            if (_doingDate && outputElement.innerText.length == 5) {
                outputElement.style.color = "green";
            } else {
                outputElement.style.color = "black";
            }
            _isInRange = true;
        }
        else
        {
            outputElement.style.color = "green";
            _isInRange = false;
        }
    }
    else if (_minValue !== null)
    {
        console.log("Only lower limit");

        if(outputElement.innerText.length > _minValue)
        {
            outputElement.style.color = "black";
            _isInRange = true;
        }
        else
        {
            outputElement.style.color = "red";
            _isInRange = false;
        }
    }
    else
    {
        console.log("No range limit");
        outputElement.style.color = "black";
        _isInRange = true;
    }
}