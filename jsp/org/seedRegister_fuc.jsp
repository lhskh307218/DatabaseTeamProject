<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <!-- import JDBC package -->
<%@ page language="java" import="java.text.*, java.sql.*" %>
<%@include file="../global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>seedRegister_fuc.jsp</title>
	<%!
	boolean Org_Result = true;
	boolean Register_Result;
	%>
</head>
<body>
	<%
	String serverIP = "192.168.56.1";
	String strSID = "orcl";
	String portNum = "1521";
	String user = "seed";
	String pass = "seed";
	String url = "jdbc:oracle:thin:@"+serverIP+":"+portNum+":"+strSID;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Class.forName("oracle.jdbc.driver.OracleDriver");
	conn = DriverManager.getConnection(url, user, pass);	
	%>
	<%
	String VarietyID, Quantity, Org, sql, query;
	int res;
	
	//request.setCharacterEncoding("utf-8");
	
	Org =  request.getParameter("Org");
	VarietyID = request.getParameter("VarietyID");
	Quantity = request.getParameter("Quantity");
	
	/*이부분에서 globalUserID가 인식되지 못해서 "농촌진흥청"으로 입력하고 진행해도 아래의 sql Org부분에서 걸리는 것 같아요ㅠ*/
	if(Org.equals(global_User_ID))
	{
		try {
			sql = "SELECT H.VARIETYID " + "FROM HAS H " 
					+ "WHERE H.ORGNAME = '" + Org + "'";
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				String tmpVID = rs.getString(1);
				
				if(VarietyID.equals(tmpVID)) {
					Register_Result = false;
				}
				else {
					Register_Result = true;
					try {
						query = "INSERT INTO HAS VALUES ("
								+ "'" + VarietyID + "', "
								+ "'" + Org + "', " 
								+  Quantity + ", 0)";
						res = stmt.executeUpdate(query);
						if (res == 1) {
							Register_Result = true;
							conn.commit();
							return;
						} else {
							Register_Result = false;
						}
					} catch (SQLException ex2) {
						System.err.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
				}
			}
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	else {
		Org_Result = false;
	}
	%>
	<% if(Org_Result == false) {%>
		<script>
			alert('기관명이 동일하지 않습니다!'); 
			window.history.back();
		</script>
	<% }else { %>
		<% if(Register_Result == true) {%>
		<script>
			alert('씨앗 등록에 성공했습니다!');
			location.href="../../html/org/orgSeedList.html";
		</script>
		<% }else { %>
		<script>
			alert('이미 존재하는 품종입니다!'); 
			window.history.back();
		</script>
		<% } %>
	<% } %>
</body>
</html>