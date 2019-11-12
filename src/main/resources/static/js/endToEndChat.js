var usernamePage = document.querySelector('#username-page');
var endToEndChatPage=document.querySelector('#endToEndChatPage');


function getActiveUsers(usernameValue) {

    if(usernameValue) {
        username = usernameValue;
        usernamePage.classList.add('hidden');
        endToEndChatPage.classList.remove('hidden');

        let activeUsers=["neo","zen","ab"];
        let table=document.getElementById("activeUsersData");
        for(let i=0;i<activeUsers.length;i++){
            let row=table.insertRow(i);
            let cell=row.insertCell(0);
            cell.innerHTML=activeUsers[i];
        }
        // var socket = new SockJS('/public');
        // stompClient = Stomp.over(socket);
        //
        // stompClient.connect({}, onConnected, onError);
    }
}

$.get("/user", function(data) {
    if(data){
        $("#user").html(data);
        getActiveUsers(data);
    }
});


