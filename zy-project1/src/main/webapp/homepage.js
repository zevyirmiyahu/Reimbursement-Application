var baseURL = 'http://localhost:8080/zy-project1/';

// purpose is to prevent reposting of reimbursement request to table
var PersonalRequestIDs = []; // holds all ids for posted personal requests
var employeeRequestIDs = []; // holds all ids for employee requests


// // checks to see if employee is manager to determine homepage layout
// window.onload = function() {
//     let xhttp;
//     xhttp = new XMLHttpRequest();
//     xhttp.open("GET", baseURL + "HomepageServlet", true);
//     xhttp.onreadystatechange = function () {
//         if (xhttp.readyState === 4 && xhttp.status === 200) {
//             isManager = xhttp.responseText;
//             console.log(isManager);
//         }
//     }
//     xhttp.send();
// };

function isManager() {
    let xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.open("GET", baseURL + "HomepageServlet", true);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            let isManager = xhttp.responseText;
            console.log(isManager);
            if(isManager === "true") {
                document.getElementById("manager-level").style.display = "initial";
                document.getElementById("register-employee").style.display = "initial";
            } else {
                document.getElementById("manager-level").style.display = "none";
                document.getElementById("register-employee").style.display = "none";
            }
        }
    }
    xhttp.send();
}

window.onload = function () {
    isManager();
    let xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.open("GET", baseURL + "ShowInfoServlet", true);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {

            let data = makeDataArray(xhttp.responseText);
            document.getElementById("table-firstname").innerHTML = data[0];
            document.getElementById("table-lastname").innerHTML = data[1];
            document.getElementById("table-address").innerHTML = data[2];
            document.getElementById("table-email").innerHTML = data[3];
            document.getElementById("table-phone").innerHTML = data[4];
            document.getElementById("table-manager").innerHTML = data[5];
        }
    };
    xhttp.send();
};

function makeDataArray(str) {
    str = str.slice(1, str.length - 1);
    let data = str.split(",");
    return data;
}

// for viewing of personal requests
function createRequestTable(entry) {

    PersonalRequestIDs.push(entry.reimburstmentID);

    let newRow = document.createElement('tr');

    let amountCol = document.createElement('td');
    amountCol.innerText = entry.amount;
    let dateCol = document.createElement('td');
    dateCol.innerText = entry.date
    let statusCol = document.createElement('td');
    statusCol.innerText = entry.status
    let descriptionCol = document.createElement('td');
    descriptionCol.innerText = entry.description

    newRow.appendChild(amountCol);
    newRow.appendChild(dateCol);
    newRow.appendChild(statusCol);
    newRow.appendChild(descriptionCol);
    document.getElementById('personal-request-table').appendChild(newRow);
}

// for viewing of all employee requests
function createAllRequestTable(entry) {

    employeeRequestIDs.push(entry.reimburstmentID);

    let newRow = document.createElement('tr');

    let idCol = document.createElement('td');
    idCol.innerText = entry.reimburstmentID;
    let amountCol = document.createElement('td');
    amountCol.innerText = entry.amount;
    let dateCol = document.createElement('td');
    dateCol.innerText = entry.date
    let statusCol = document.createElement('td');
    statusCol.innerText = entry.status
    let descriptionCol = document.createElement('td');
    descriptionCol.innerText = entry.description

    newRow.appendChild(idCol);
    newRow.appendChild(amountCol);
    newRow.appendChild(dateCol);
    newRow.appendChild(statusCol);
    newRow.appendChild(descriptionCol);
    document.getElementById('employee-request-table').appendChild(newRow);
}

function resetEmployeeRequestTable() {
    let table = document.getElementById("employee-request-table");
    while (table.secondChild) {
        table.removeChild(table.secondChild);
    }
}

function resetPersonalRequestTable() {
    let table = document.getElementById("personal-request-table");
    while (table.secondChild) {
        table.removeChild(table.secondChild);
    }
}

function getRequestInfo() {
    let xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.open("GET", baseURL + "RequestInfoServlet", true);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            let data = JSON.parse(xhttp.response);
            resetPersonalRequestTable();
            for (let e in data) {
                if (!PersonalRequestIDs.includes(data[e].reimburstmentID)) {
                    createRequestTable(data[e]);
                }
            }
        }
    };
    xhttp.send();
}

function getAllRequestInfo() {
    let xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.open("GET", baseURL + "AllRequestInfoServlet", true);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            let data = JSON.parse(xhttp.response);
            resetEmployeeRequestTable();
            for (let e in data) {
                if (!employeeRequestIDs.includes(data[e].reimburstmentID)) {
                    createAllRequestTable(data[e]);
                }
            }
        }
    };
    xhttp.send();
}