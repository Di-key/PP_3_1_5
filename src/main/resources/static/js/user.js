let currentUser = "";
let tableUser = [];
let request = new Request("/api/users/current", {
    method: "GET",
    headers: {
        'Content-Type': 'application/json',
    },
});
getUser()

function getUser() {
    fetch(request).then(res =>
        res.json()).then(data => {
        tableUser = [];
        if (data.length > 0) {
            data.forEach(user => {
                tableUser.push(user)
            })
        } else {
            tableUser = [];
        }

        showOneUser(tableUser);
    })
}

fetch("/api/users/current").then(res => res.json())
    .then(data => {
        currentUser = data;
        console.log(data)
        showOneUser(currentUser);
    })

function showOneUser(user) {
    let temp = "";
    temp += "<tr>"
    temp += "<td>" + user.id + "</td>"
    temp += "<td>" + user.firstName + "</td>"
    temp += "<td>" + user.lastName + "</td>"
    temp += "<td>" + user.age + "</td>"
    temp += "<td>" + user.email + "</td>"
    temp += "<td>" + user.roles.map(role => role.name.substring(5)) + "</td>"
    temp += "</tr>"
    document.getElementById("oneUserBody").innerHTML = temp;
}









