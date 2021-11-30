<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!-- import JDBC package -->
<%@ page language="java" import="java.text.*, java.sql.*" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>userMainEdible</title>
<style>
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
        #purposemenu li a {
            color: #C4C4C4;
            display: block;
            padding: 10px;
            text-decoration: none;
            text-align: center;
            font-weight: bold;
        }
        #purposemenu li a.current {
           color: black;
           background-color: white;
        }
        #purposemenu li a:hover:not(.current) {
            color: black;
            background-color: white;
        }
        #purposemenudefault li a {
            color: black;
            background-color: white;
        }
        .weekBestSeed {
            background-color: #F8FFDC;
            height: 200px;
            width: 18%;

            border:0 solid black;
            border-radius: 30px;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

            margin-left: 1%;
            margin-right: 1%;
            float: left;
                        
            text-align: center;
            font-weight: bold;
            font-size: 30px;
        }
        .weekbestSeedBack {
            display: block;
            background-color: #D0DB36;
            height: 280px;

            padding: 1%;

            font-weight: bold;
            font-size: 20px;
        }
        .seeds {
            background-color: #D0DB36;
            height: 400px;
            width: 23%;

            border:0 solid black;
            border-radius: 30px;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
            
            margin: 1%;
            float: left;
                        
            text-align: center;
            font-weight: bold;
            font-size: 40px;
        }
        select {
            margin-top: 20px;
            float: right;
            width:150px;
            height: 40px;
            font-weight: bold;
            text-align: left;
            padding-left: 10px;
        }
    </style>
</head>
<body>
<h1>네비게이션바</h1>
    <ul class="navi" id="navi">
        <li><a class="current" id="navi" href="userMainAll.html">씨앗 구매</a></li>
        <li><a href="userOrderList.jsp">주문 목록</a></li>
        <li><a href="#">공지 사항</a></li>
        <li><a href="#">커뮤니티</a></li>
    </ul>
    <ul class="purposemenu" id="purposemenu">
        <li><a href="userMainAll.jsp">전체</a></li>
        <li><a class="current" href="userMainEdible.jsp">식용</a></li>
        <li><a href="userMainOrnamental.jsp">관상용</a></li>
        <li><a href="userMainPet.jsp">사료용</a></li>
    </ul>
    <div class="weekbestSeedBack">
        이번 주 판매 베스트 TOP5<br><br>
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
        
        String ID = "abc12";
        String OrderNum = "";
        String Quantity = "";
        
        try {
        	String sql = "SELECT DISTINCT QSum, SeedName\r\n"
    				+ "FROM ( SELECT DISTINCT SUM(Quantity) OVER (PARTITION BY SeedName) AS QSum, SeedName\r\n"
    				        + "FROM \"ORDER\" INNER JOIN \"SEED\" ON OD_VarietyID = VarietyID\r\n"
    				        + "WHERE TO_CHAR(SYSDATE - 7, 'YYYY-MM-DD') < Purchase_Date\r\n"
    				        + "AND SeedPurpose = '식용'\r\n"
    				        + "ORDER BY QSum DESC)\r\n"
    				+ "WHERE ROWNUM <= 5";
    		rs = stmt.executeQuery(sql);
            while (rs.next()) {
               String SeedName = rs.getString(2);
			   %>
        		<form action="userSelectSeed.jsp" method="post">
        			<input type="submit" class="weekBestSeed" name="SeedName" value="<%=SeedName %>" onclick="location.href='userSelectSeed.jsp'">
        		</form>
        		<%
            }
    		
    	}catch(SQLException ex2) {
    		System.out.println("sql error = " + ex2.getMessage());
    		System.exit(1);
    	}
        %>
    </div>
    <form action="" name="frm">
    	<div>
        	<select name="sortlist" onchange="location=document.frm.sortlist.value">
            	<option value="userMainEdibleYear.jsp">정렬 방식</option>
            	<option value="userMainEdibleTeen.jsp">청소년 인기순</option>
            	<option value="userMainEdibleYouth.jsp">청년 인기순</option>
            	<option value="userMainEdibleMid.jsp">중년 인기순</option>
            	<option value="userMainEdibleOld.jsp">장년 인기순</option>
            	<option value="userMainEdibleDay.jsp">하루 인기순</option>
            	<option value="userMainEdibleMonth.jsp">한달 인기순</option>
            	<option selected value="userMainEdibleYear.jsp">일년 인기순</option>
        	</select><br>
    	</div>
    </form>
    <div style="margin-top: 5%;">
    <%
    try {
    	String sql = "SELECT DISTINCT SUM(Quantity) OVER (PARTITION BY SeedName) AS QSum, SeedName\r\n"
    			+ "FROM \"ORDER\" INNER JOIN \"SEED\" ON OD_VarietyID = VarietyID\r\n"
    			+ "WHERE TO_CHAR(SYSDATE - 365, 'YYYY-MM-DD') < Purchase_Date\r\n"
    			+ "AND SeedPurpose = '식용'\r\n"
    			+ "ORDER BY QSum DESC";		     
    	rs = stmt.executeQuery(sql);
        while (rs.next()) {
           String SeedName = rs.getString(2);
           %>
   			<form action="userSelectSeed.jsp" method="post">
           		<input type="submit" class="seeds" name="SeedName" value="<%=SeedName %>" onclick="location.href='userSelectSeed.jsp'">
           	</form>
           <%
        }
    }catch(SQLException ex2) {
    	System.out.println("sql error = " + ex2.getMessage());
    	System.exit(1);
    }
    %>
    </div>

</body>
</html>