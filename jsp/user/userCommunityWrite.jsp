<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!-- import JDBC package -->
<%@ page language="java" import="java.text.*, java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reading the content</title>
<style>
		input:focus {outline:none;}
		.footer {
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            height: 115px;

            text-align: right;
            color:white;
            font-size: 20px;
            font-weight: bold;
            background-color: #FFCE1F;
        }
        .navi {
            background-color: #FFCE1F;
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        .purposemenu {
            background-color: white;
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        li { float: left; }
        #navi li a {
            display: block;
            color: #000000;
            padding: 10px;
            text-decoration: none;
            text-align: center;
            font-weight: bold;
        }
        #navi li a.current { 
            background-color: #D0DB36;
            color: black;
        }
        #navi li a:hover:not(.current) {
            background-color: #FFD600;
            color: white;
        }
        .title {
			width: 700px;
			height: 65px;
			
			margin-top: 3%;
			margin-left: 4%;
			padding-top: 1%;
			
			background-color: #87C8E3;
			border-radius: 30px 30px 0px 0px;
			border:none;
			
			text-align: center;
			font-size: 40px;
			font-weight: bold;
			color: white;
		}
		.contentBack {
			width: 92%;
			height: 900px;
			
			margin-left: 4%;
			
			background-color: #FFF3EF;
		}
		.content {
			overflow: scroll;
			display: inline-block;
			
			width: 88%;
			height: 65%;
			background-color: white;
			
			margin-top: 2%;
			margin-left: 4%;
			margin-right: 4%;
			padding: 2%;
			border: none;
			
			color: black;
			font-size: 30px;
			font-weight: bold;
		}
		.ID {
			background-color: white;
			margin-top: 4%;
			margin-left: 4%;
			padding: 1%;
			
			border: none;
			font-weight: bold;
			font-size: 30px;
		}
		.writer {
			float: right;
			
			margin-top: 0.5%;
			margin-right: 5%;
			
			background: transparent;
			
			font-weight: bold;
			font-size: 30px;
		}
		.temp {
			color: #FFF3EF;
			font-size: 0.1px;
			border: none;
			background: transparent;
		}
		.btn {
			height: 50px;
			width: 130px;
			
			background-color: #87C8E3;
			border-radius: 30px 30px 30px 30px;
			border: none;
			
			
			color: white;
			font-weight: bold;
			font-size: 20px;
			text-aling: center;
		}
		select {
            float: left;
            width:150px;
            height: 40px;
            
          	margin-top: 0.5%;
            margin-left: 4%;
            text-align: left;
            padding-left: 10px;
            font-weight: bold;
        }
        .Info {
            margin-top: 3%;
            margin-right: 1%;
            float: right;
        }
    </style>
</head>
<body>
<header>
        <img class = "Logo" src="../../image/Logo.png" alt="Logo">
        <form class = "Info">
            <div style="display: inline-flex";>
                <img class = "user" src="../../image/user.png" alt="user">
                <h3>seedb님</h3>
            </div>
            <div>
                <a href="../../html/selectUserType.html">로그아웃</a>  
            </div>
        </form>
    </header>
    <ul class="navi" id="navi">
        <li><a href="userMainAll.jsp">씨앗 구매</a></li>
        <li><a href="userOrderList.jsp">주문 목록</a></li>
        <li><a href="userNoticeList.jsp">공지 사항</a></li>
        <li><a class="current" href="userCommunityList.jsp">커뮤니티</a></li>
    </ul>
    
    <%
    String serverIP = "localhost";
    String strSID = "orcl";
    String portNum = "1521";
    String user = "teamproject";
    String pass = "comp322";
    String url = "jdbc:oracle:thin:@" + serverIP + ":" + portNum + ":" + strSID;
    request.setCharacterEncoding("UTF-8");

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs;
    
    Class.forName("oracle.jdbc.driver.OracleDriver");
    conn = DriverManager.getConnection(url, user, pass);
    conn.setAutoCommit(false);
    stmt = conn.createStatement();
    
    String Title = request.getParameter("Title");
    String Content = request.getParameter("Content");
    String UserID = request.getParameter("UserID");
    String Time = request.getParameter("Time");
	
    // 아이디!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   	%>
   	<form action="userCommunityWriting.jsp">
   	<div>
    	<input type="text" class="title" name="Title" placeholder="제목을 입력하세요.">
    	<div type="text" class="contentBack">
    	   	<input type="text" name="UserID" class="ID" placeholder="아이디를 입력하세요."><br>
    		<input type="text" class="content" name="Content" placeholder="글 내용을 작성해주세요.">
    		<select name="Purpose">
            		<option selected>정렬 방식</option>
            		<option value="나눔">나눔</option>
            		<option value="홍보">홍보</option>
            		<option value="소통">소통</option>
        	</select><br>
    		<div class="writer">
    			<button type="submit" class="btn">등록하기</button>
    		</div>
    	</div>
    	</div>
    </form>
    <%

    stmt.close();
    conn.close();
    %>
</body>
</html>
