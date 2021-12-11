function getContact(id) {
    if (document.getElementById("contact" + id).innerHTML == "") {
        document.getElementById("contact" + id).innerHTML = "Hello!";
        fetch('http://localhost:8080/getContact/' + id)  // fetch data from our service
            .then(data => data.json()) // JSONify the data returned
            .then(function (data) { // with the JSON data
// modify textToDisplay below here!
                var textToDisplay = ""; // create and append to a blank var
                textToDisplay += "ID: " + data.contact.id + "<br>";
                textToDisplay += "Name: " + data.contact.name + "<br>";
                textToDisplay += "Phone Nunber: " + data.contact.phoneNumber + "<br>";
                textToDisplay += "Address: " + data.contact.address + "<br>";
                textToDisplay += "Email: " + data.contact.email + "<br>";
                textToDisplay += "Role: " + data.contact.role + "<br>";

// finally, change our relevant div to display the var
                document.getElementById("contact" + id).innerHTML = textToDisplay;
            });
    } else {
        document.getElementById("contact" + id).innerHTML = "";
    }

}

function verify() {
    var password1 = document.forms['form']['password'].value;
    var password2 = document.forms['form']['verifyPassword'].value;
    if (password1 == null || password2 == "" || password1 != password2) {
        document.getElementById("error").innerHTML= "Please check your passwords";
        return false;
    }
}