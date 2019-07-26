var baseURL = 'http://localhost:8080/zy-project1/';

(function viewInfo() {
    var xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            document.getElementById("infoTable").innerHTML = xhttp.responseText;
            let resp = JSON.parse(xhttp.reponse);
            
            console.log("HERE!!")
            popTable(resp);
            document.getElementById("email").innerHTML = resp[0];
            document.getElementById("first-name").innerHTML = resp[1];
        }
    };
    xhttp.open("GET", baseURL + "ShowInfoServlet", true);
    xhttp.send();
})