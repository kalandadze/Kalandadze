function findMessages() {
    clearList();
    if(document.getElementById("loginUsername").value==""||document.getElementById("loginPassword").value==""){
        alert("Username and Password is Required");
        return;
    }
    var message="sdasd";
    addMessage(message);
    document.getElementById("loginUsername").value="";
    document.getElementById("loginPassword").value=""
}

function addMessage(message){
    var ul = document.getElementById("messages");
    var li = document.createElement("li");
    li.appendChild(document.createTextNode(message));
    ul.appendChild(li);
}

function clearList(){
    var ul = document.getElementById("messages");
    while(ul.firstChild){
        ul.removeChild(ul.firstChild);
    }
}

function registerUser(){
    if(document.getElementById("username").value==""||document.getElementById("password").value==""){
        alert("Username and Password is Required");
        return;
    }
    document.getElementById("username").value="";
    document.getElementById("password").value="";
}

function sendMessage(){
    if(document.getElementById("user").value==""||document.getElementById("message").value==""){
        alert("Target user and Message is Required");
        return;
    }
    document.getElementById("user").value="";
    document.getElementById("message").value="";
}


















































// Request-ის გაგზავნა
// async function register() {
//     var url = webserverName + servletUrl + '?param1=param1Value' + '&param2=param2Value';
//     var method = "POST" ან "GET"
//     var response = await fetch(url, { method: "POST" });
//
//     // Response body-ს მიღება
//     var body = await response.text();
//
//     // HTML ელემენტის დამატება/შეცვლა
//     var div = document.getElementById("some-div-id");
//     div.innerHTML = 'some html code here';
// }








