var usernamePage = document.querySelector('#username-page');
var endToEndChatPage=document.querySelector('#endToEndChatPage');
//var chatPage = document.querySelector('#chat-page');
//var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var stompClient = null;
var username = null;
var selectedUser=null;


function connect(usernameValue) {

    if(usernameValue) {

        username = usernameValue;
         usernamePage.classList.add('hidden');
         endToEndChatPage.classList.remove('hidden');
        /*username = usernameValue;
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');*/

        var socket = new SockJS('/public');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
    // Subscribe to the Public Topic
    getActiveUsers();
    stompClient.subscribe('/topic/'+username, onMessageReceived);

    /*// Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )*/

    //connectingElement.classList.add('hidden');
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient && selectedUser) {
        var chatMessage = {
            sender: username,
            receiver:selectedUser,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/sendMessage/chat.EndToEndChat", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function getActiveUsers() {

    if(username) {
       // username = usernameValue;
       // usernamePage.classList.add('hidden');
       // endToEndChatPage.classList.remove('hidden');

        $.get("/usersFromSessionRegistry", function (data) {
            var activeUsers=data;
            var table=document.getElementById("activeUsersData");
            for(var i=0;i<activeUsers.length;i++){
                var row=table.insertRow(i);
                var cell=row.insertCell(0);
                var link=document.createElement("a")
                link.className ="activeUsersCell";
                var linkText=document.createTextNode(activeUsers[i]);
                row.setAttribute("class","activeUsersRow")
                //link.setAttribute("href", "https://www.w3schools.com");
                cell.setAttribute("id",activeUsers[i]);
                cell.setAttribute("class","activeUsersCell");
                link.appendChild(linkText);
                cell.appendChild(link);
                link.onclick=function () { setSelectedUser(link.innerText) };
            }
        })

        // var socket = new SockJS('/public');
        // stompClient = Stomp.over(socket);
        //
        // stompClient.connect({}, onConnected, onError);
    }
}


function setSelectedUser(selectedUsername) {
    selectedUser = selectedUsername;
    //document.getElementById('response').innerHTML = '';
}

messageForm.addEventListener('submit', sendMessage, true);

$.get("/user", function(data) {
    if(data){
        $("#user").html(data);
        connect(data);
        //getActiveUsers(data);
    }
});


