<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../common/menu.jsp" %>

<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css?v=<%=System.currentTimeMillis()%>" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css?v=<%=System.currentTimeMillis()%>" />
<title>Friends Management</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
   function addFriend() {
       var userId = "<%= user_id%>"
       var friendId = $('#friendId').val();

       $.ajax({
           url: '<%=request.getContextPath()%>/FriendServlet',
           type: 'POST',
           data: {
               action: 'addFriend',
               userId: userId,
               friendId: friendId
           },
           success: function(response) {
               if (response) {
                   alert('Friend added successfully!');
                   viewFriends();
               } else {
                   alert('This friend relationship already exists.');
               }
           }
       });
   }

   function viewFriends() {
       var userId = "<%= user_id%>"

       $.ajax({
           url: '<%=request.getContextPath()%>/FriendServlet',
           type: 'GET',
           data: {
               action: 'viewFriends',
               userId: userId
           },
           success: function(response) {
               var friendsList = $('#friendsList');
               friendsList.empty();
               $.each(response, function(index, friend) {
                   friendsList.append('<li>' + friend.friend_id + '</li>');
               });
           }
       });
   }
   
   document.addEventListener('DOMContentLoaded', function () {
   	viewFriends();
   });
   
</script>
</head>
<body>
<main id="container">
<section>
	<div class="inner">
		<h2>친구 추가</h2>
    <form onsubmit="addFriend(); return false;">
        내 ID: <h3><%= user_id%></h3><br>
        친구 ID: <input type="text" id="friendId"><br>
        <input type="submit" value="Add Friend">
    </form>

    <h1>내 친구 목록</h1>

    <ul id="friendsList"></ul>
	</div>
</section>
</main>
<%@ include file="../common/footer.jsp"%>
</body>
</html>