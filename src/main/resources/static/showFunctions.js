function autocomplete(inp, arr) {
    var currentFocus;
    inp.addEventListener("input", function (e) {
        var a, b, i, val = this.value;
        closeAllLists();
        if (!val) {
            return false;
        }
        currentFocus = -1;
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        this.parentNode.appendChild(a);
        for (i = 0; i < arr.length; i++) {
            if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                b = document.createElement("DIV");
                b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                b.addEventListener("click", function (e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });
    inp.addEventListener("keydown", function (e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            currentFocus++;
            addActive(x);
        } else if (e.keyCode == 38) {
            currentFocus--;
            addActive(x);
        } else if (e.keyCode == 13) {
            e.preventDefault();
            if (currentFocus > -1) {
                if (x) x[currentFocus].click();
            }
        }
    });

    function addActive(x) {
        if (!x) return false;
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        x[currentFocus].classList.add("autocomplete-active");
    }

    function removeActive(x) {
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }

    function closeAllLists(elmnt) {
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }

    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}

window.onload = function () {
    let citiesNames = localStorage.getItem('citiesNames');
    if (citiesNames != null) {
        autocomplete(document.getElementById("geo_from"), JSON.parse(localStorage.getItem("citiesNames")));
        autocomplete(document.getElementById("geo_to"), JSON.parse(localStorage.getItem("citiesNames")));
        return;
    }

    let xhr = new XMLHttpRequest();
    let hostname = location.hostname;
    xhr.open('POST', 'https://' + hostname + ':8088/load_cities', true);
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.setRequestHeader('Access-Control-Allow-Methods', 'POST');
    xhr.setRequestHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            localStorage.setItem("citiesNames", JSON.stringify(data));
            autocomplete(document.getElementById("geo_from"), JSON.parse(localStorage.getItem("citiesNames")));
            autocomplete(document.getElementById("geo_to"), JSON.parse(localStorage.getItem("citiesNames")));
        }
    }
    xhr.send();
}


function sendData() {
    let xhr = new XMLHttpRequest();
    let hostname = location.hostname;
    xhr.open('POST', 'https://' + hostname + ':8088/find_tickets', true);
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.setRequestHeader('Access-Control-Allow-Methods', 'POST');
    xhr.setRequestHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            showTickets(data);
        }
    }

    let origin = document.getElementById("geo_from").value;
    let destination = document.getElementById("geo_to").value;
    let originDate = document.getElementById("date_from").value;
    let destinationDate = document.getElementById("date_to").value;

    let messageStr = "{" +
        " \"origin\" : \"" + origin + "\" ," +
        " \"destination\" : \"" + destination + "\" ," +
        " \"originDate\" : \"" + originDate + "\" ," +
        " \"destinationDate\" : \"" + destinationDate + "\"" +
        "}";
    console.log(messageStr);
    xhr.send(messageStr);
}

function showTickets(data) {
    let resultContainer = document.getElementById("search_result");

    while (resultContainer.firstChild) {
        resultContainer.removeChild(resultContainer.lastChild);
    }

    for (let i = 0; i < data.length; i++) {
        let ticketData = data[i];
        let ticketTo = ticketData[0];
        let ticketBack = ticketData[1];
        let ticketContent = `
                <div class="result_content">`;
        ticketContent += `
                        <div>
                        <p>
                            ${ticketTo['departureCountry']}.${ticketTo['departure']} -> ${ticketTo['arrivalCountry']}.${ticketTo['arrival']}
                        </p>
                        </div>
                        <div>
                            <p>
                                ${ticketTo['departureDate']}.${ticketTo['departureHour']} -> ${ticketTo['arrivalDate']}.${ticketTo['arrivalHour']}
                            </p>
                        </div>
                        `
        if (ticketBack !== null){
            ticketContent += `
                        <div>
                        <p>
                            ${ticketBack['arrivalCountry']}.${ticketBack['departure']} -> ${ticketBack['departureCountry']}.${ticketBack['arrival']}
                        </p>
                        </div>
                        <div>
                            <p>
                                ${ticketBack['departureDate']}.${ticketBack['departureHour']} -> ${ticketBack['arrivalDate']}.${ticketBack['arrivalHour']}
                            </p>
                        </div>
                        `

        ticketBack += `<div>
                             ${ticketBack['price']}
                           </div>`;
        }
        ticketContent += `</div>`;
        resultContainer.insertAdjacentHTML('beforeend', ticketContent);
    }
}