async function findMessages() {
    clearList();
    user=document.getElementById("loginUsername").value;
    password=document.getElementById("loginPassword").value;
    if(user==""||password==""){
        alert("Username and Password is Required");
        return;
    }
    var url = "http://localhost:8989/messenger/message?username="+user+"&password="+password;
    var response = await fetch(url, { method: "GET" });
    if(response.status===403){
        alert("user doesn't exist");
    }
    var message=await response.text();
    const messages=message.split("\n");
    addMessage(messages);
    document.getElementById("loginUsername").value="";
    document.getElementById("loginPassword").value=""
}

function addMessage(messages){
    for(var i=0;i<messages.length-1;i++){
        var message=messages[i];
        var ul = document.getElementById("messages");
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(message));
        ul.appendChild(li);
    }
}

function clearList(){
    var ul = document.getElementById("messages");
    while(ul.firstChild){
        ul.removeChild(ul.firstChild);
    }
}

async function registerUser(){
    var user=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    if(user==""||password==""){
        alert("Username and Password is Required");
        return;
    }
    var url = "http://localhost:8989/messenger/user?username="+user+"&password="+password;
    var response = await fetch(url, { method: "POST" });
    if(response.status===403){
        alert("user already exists")
    }else{
        alert("user succesfully registered")
    }

    document.getElementById("username").value="";
    document.getElementById("password").value="";
}

async function sendMessage(){
    var user=document.getElementById("user").value;
    var message=document.getElementById("message").value;
    if(message.includes("\\n")){
        alert("message can't contain new line or \\n")
        return;
    }
    if(user==""||message==""){
        alert("Target user and Message is Required");
        return;
    }
    var url = "http://localhost:8989/messenger/message?username="+user+"&message="+message;
    var response = await fetch(url, { method: "POST" });
    if(response.ok){
        alert("message succesfully sent")
    }else if(response.status===403){
        alert("user doesn't exist");
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








