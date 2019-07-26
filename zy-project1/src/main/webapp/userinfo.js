var baseURL = 'http://localhost:8080/zy-project/';
var info = [];

document.getElementById("updateInfo").onclick = () => {
    console.log('loaded');
    let xhr = new XMLHttpRequest();
    xhr.open("GET", baseURL + "ShowInfoServlet?");
    console.log('loaded2');
    xhr.onreadystatechange = () => {
        console.log('loaded3');
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.status);
            console.log(xhr.responseText);
            let resp = JSON.parse(xhr.response);
            resetTable();
            info = [];
            for (let data of resp) {
                console.log(data);
                info.push(data);
            }
            popTable();
        }
    };
    xhr.send();
};

function popTable() {
    for (let data of info) {
        let newRow = document.createElement('tr');
        console.log(data.id);
        let idCol = document.createElement('td');
        idCol.innerText = data.id;
        let nameCol = document.createElement('td');
        nameCol.innerText = data.firstname;
        let levelCol = document.createElement('td');
        levelCol.innerText = data.lastname;
        let classCol = document.createElement('td');
        classCol.innerText = data.address;

        newRow.appendChild(idCol);
        newRow.appendChild(nameCol);
        newRow.appendChild(levelCol);
        newRow.appendChild(classCol);
        document.getElementById('infotable').appendChild(newRow);
    }
}

function resetTable() {
    let table = document.getElementById('infotable');
    while (table.firstChild) {
        table.removeChild(table.firstChild);
    }
}