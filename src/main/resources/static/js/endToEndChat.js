var usernamePage = document.querySelector('#username-page');
var endToEndChatPage=document.querySelector('#endToEndChatPage');


function getActiveUsers(usernameValue) {

    if(usernameValue) {
        username = usernameValue;
        usernamePage.classList.add('hidden');
        endToEndChatPage.classList.remove('hidden');

        $.get("/usersFromSessionRegistry", function (data) {
            var activeUsers=data;
            var table=document.getElementById("activeUsersData");
            for(var i=0;i<activeUsers.length;i++){
                var row=table.insertRow(i);
                var cell=row.insertCell(0);
                cell.innerHTML=activeUsers[i];
            }
        })

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


