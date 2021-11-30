package phase3;

import java.sql.*; // import JDBC package
import java.util.Scanner;
import java.io.Closeable;
import java.io.IOException;

public class Team7_SEEDB_JDBC {

	public static final String URL = "jdbc:oracle:thin:@192.168.56.1:1521:orcl";
	public static final String USER_SEED = "seed";
	public static final String USER_PASSWD = "seed";

	static String now_USER_ID;
	static String now_USER_PW;
	static int now_USER_Type = 1; /* 현재 접속자 분류: 1) 고객 2) 기관 3) 관리자 . 기본값은 고객 */

	/*
	 * public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	 * public static final String USER_SEED = "teamproject"; public static final
	 * String USER_PASSWD = "comp322";
	 */

	static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		/***************************************************
		 * 연결
		 ***************************************************/
		Connection conn = null; // Connection object
		Statement stmt = null; // Statement object

		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object
			System.out.println("Success!");
		} catch (ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try {
			conn = DriverManager.getConnection(URL, USER_SEED, USER_PASSWD);
			System.out.println("Connected.");
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}

		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}

		/***************************************************
		 * SEEDB 프로그램
		 ***************************************************/

		while (true) {
			System.out.println("SEEDB에 오신 것을 환영합니다!\n\n");

			/* 로그인, 회원가입 메뉴 출력 */
			System.out.println("-----------------------------------");
			System.out.println("---------------메뉴-----------------");
			System.out.println("[1] 로그인       [2] 회원가입         [3] 프로그램 종료");
			System.out.println("-----------------------------------");
			System.out.println("-----------------------------------");

			/* 메뉴 입력받기 */
			int menu_num, Login_Result;
			System.out.printf("<메뉴를 선택해주세요>: ");
			menu_num = keyboard.nextInt();

			if (menu_num == 1) {
				Login_Result = Login(conn, stmt);

				/* 로그인에 성공 */
				if (Login_Result == 1) {
					int select_menu;
					switch (now_USER_Type) {
					/* 1) 접속: 고객 */
					case 1:
						while (true) {
							/* 고객 메인 화면 */
							System.out.println("-----------------------------------");
							System.out.println("---------------메뉴-----------------");
							System.out.println("[1] 씨앗 구매    [2] 주문 목록  [3] 공지사항");
							System.out.println("[4] 커뮤니티   [5] 마이페이지  [6] 로그아웃");
							System.out.println("-----------------------------------");
							System.out.println("-----------------------------------");
							System.out.println("메뉴를 선택하세요: ");

							select_menu = keyboard.nextInt();

							/* 로그아웃 */
							if (select_menu == 6)
								break;

							/* 고객 메뉴 실행 */
							switch (select_menu) {
							/* 고객: [1] 씨앗 구매 */
							case 1: {
								orderoption(conn, stmt);
								break;
							}

							/* 고객: [2] 주문 목록 */
							case 2: {
								myOrderList(conn, stmt);
								break;
							}

							/* 고객: [3] 공지사항 */
							case 3:
		                    	 System.out.println("-----------------------------------");
		                         System.out.println("---------------메뉴-----------------");
		                         System.out.println("-------------게시글 검색---------------");
		                         System.out.println("-----------------------------------");
		                         System.out.println("-----------------------------------");
		                         NBread(conn, (PreparedStatement) stmt);
								break;

							/* 고객: [4] 커뮤니티 */
							case 4:
		                        System.out.println("-----------------------------------");
		                         System.out.println("---------------메뉴-----------------");
		                         System.out.println("[1] 게시글 쓰기    [2] 게시글 검색  [3] 게시글 지우기");
		                         System.out.println("-----------------------------------");
		                         System.out.println("-----------------------------------");
		                         System.out.println("메뉴를 선택하세요: ");
		                         int select_menu1 = keyboard.nextInt();
		                         
		                         switch(select_menu1)
		                         {
		                           case 1:
		                              BBwrite(conn,(PreparedStatement) stmt);
		                              break;
		                           case 2:
		                              BBread(conn, (PreparedStatement) stmt);
		                              break;
		                           case 3:
		                              BBdelete(conn,(PreparedStatement) stmt);
		                              break;                             
		                              
		                         }
		                         break;

							/* 고객: [5] 마이페이지 */
							case 5:
								Mypage(conn, stmt);
								break;
							}

						} // 고객 메뉴 while문 종료
						System.out.println("[고객님의 접속을 종료합니다.]");
						break;

					/* 2) 접속: 기관 */
					case 2:
						while (true) {
							/* 판매 기관 메인 화면 */
							System.out.println("-----------------------------------");
							System.out.println("---------------메뉴-----------------");
							System.out.println("[1] 씨앗      [2] 주문 목록  [3] 공지사항");
							System.out.println("[4] 커뮤니티   [5] 마이페이지  [6] 로그아웃");
							System.out.println("-----------------------------------");
							System.out.println("-----------------------------------");
							System.out.println("메뉴를 선택하세요: ");

							select_menu = keyboard.nextInt();

							/* 로그아웃 */
							if (select_menu == 6)
								break;

							/* 판매 기관 메뉴 실행 */
							switch (select_menu) {
							/* 판매 기관: [1] 씨앗 */
							case 1:
								SEEDIN(conn, stmt);
								break;

							/* 판매 기관: [2] 주문 목록 */
							case 2:
								OrgOrderList(conn, stmt);
								break;

							/* 판매 기관: [3] 공지사항 */
							case 3:
		                    	 System.out.println("-----------------------------------");
		                         System.out.println("---------------메뉴-----------------");
		                         System.out.println("-------------게시글 검색---------------");
		                         System.out.println("-----------------------------------");
		                         System.out.println("-----------------------------------");
		                         NBread(conn, (PreparedStatement) stmt);
								break;

							/* 판매 기관: [4] 커뮤니티 */
							case 4:
		                        System.out.println("-----------------------------------");
		                         System.out.println("---------------메뉴-----------------");
		                         System.out.println("-------------게시글 검색---------------");
		                         System.out.println("-----------------------------------");
		                         System.out.println("-----------------------------------");
		                         System.out.println("메뉴를 선택하세요: ");                        
		                         BBread(conn, (PreparedStatement) stmt);
								break;

							/* 판매 기관: [5] 마이페이지 */
							case 5:
								Mypage(conn, stmt);
								break;
							}
						}
						System.out.println("[판매 기관님의 접속을 종료합니다.]");
						break;

					/* 3) 접속: 관리자 */
					case 3:
						while (true) {
							/* 관리자 메인 화면 */
							System.out.println("-----------------------------------");
							System.out.println("---------------메뉴-----------------");
							System.out.println("[1] 회원/기관 관리  [2] 주문목록  [3] 공지사항");
							System.out.println("[4] 커뮤니티   [5] 마이페이지  [6] 로그아웃");
							System.out.println("-----------------------------------");
							System.out.println("-----------------------------------");
							System.out.println("메뉴를 선택하세요: ");

							select_menu = keyboard.nextInt();

							/* 로그아웃 */
							if (select_menu == 6)
								break;

							/* 관리자 메뉴 실행 */
							switch (select_menu) {
							/* 관리자: [1] 회원/기관 관리 */
							case 1:
								Mypage(conn, stmt);
								break;

							/* 관리자: [2] 주문 목록 */
							case 2:
								manOrderList(conn, stmt);
								break;

							/* 고객: [3] 공지사항 */
							case 3: {
								System.out.println("-----------------------------------");
								System.out.println("---------------메뉴-----------------");
								System.out.println("[1] 게시글 읽기  ");

								NBread(conn, (PreparedStatement) stmt);

								break;
							}

							/* 고객: [4] 커뮤니티 */
							case 4: {
								System.out.println("-----------------------------------");
								System.out.println("---------------메뉴-----------------");
								System.out.println("[1] 게시글 쓰기    [2] 게시글 검색  [3] 게시글 지우기");
								System.out.println("-----------------------------------");
								System.out.println("-----------------------------------");
								System.out.println("메뉴를 선택하세요: ");
								int select_menu1 = keyboard.nextInt();

								switch (select_menu1) {
								case 1:
									BBwrite(conn, (PreparedStatement) stmt);
									break;
								case 2:
									BBread(conn, (PreparedStatement) stmt);
									break;
								case 3:
									BBdelete(conn, (PreparedStatement) stmt);
									break;
								}
							}
							/* 관리자: [5] 마이페이지 */
							case 5:
								Mypage(conn, stmt);
								break;
							}
						}
						System.out.println("[관리자님의 접속을 종료합니다.]");
						break;
					}
				}
			} else if (menu_num == 2)
				Sign_up(conn, stmt);
			else if (menu_num == 3)
				break;

		}

		System.out.println("SEEDB를 종료합니다. 이용해주셔서 감사합니다.\n");
		/***************************************************
		 * 종료
		 ***************************************************/
		// Release database resources.

		try {
			// Close the Statement object.
			if (stmt != null)
				stmt.close();
			// Close the Connection object.
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // main 종료

	/* 로그인 함수 */
	public static int Login(Connection conn, Statement stmt) {
		int result = 0;
		String ID, PW, sql;

		int Login_menu_num;
		System.out.println("[1] 로그인을 선택하셨습니다!\n\n");
		System.out.println("-----------------------------------");
		System.out.println("로그인하고자 하는 유형을 선택하십시오.");
		System.out.println("---------------메뉴-----------------");
		System.out.println("[1] 사용자/관리자       [2] 판매 기관");
		System.out.println("-----------------------------------");
		System.out.println("-----------------------------------");

		/* 메뉴 입력받기 */
		System.out.printf("<로그인 메뉴를 선택해주세요>: ");
		Login_menu_num = keyboard.nextInt();

		switch (Login_menu_num) {
		case 1:
			System.out.println("[1] 사용자/관리자를 선택하셨습니다!\n\n");

			int User_id_wrong = 0;
			int User_pw_wrong = 0;
			while (true) {
				/* 3번째 실패 시 메인 화면으로 전환 */
				if (User_id_wrong > 1 || User_pw_wrong > 1) {
					System.out.println("로그인에 실패하였습니다!\n\n\n");
					result = 0;
					break;
				}
				System.out.println("아이디와 비밀번호를 입력해주세요.\n\n");
				System.out.printf("<아이디>: ");
				ID = keyboard.next();
				System.out.printf("<비밀번호>: ");
				PW = keyboard.next();
				try {
					sql = "SELECT U.USERID, U.PW \r\n" + "FROM \"USER\" U \r\n" + "WHERE U.USERID = '" + ID + "'";
					ResultSet rs = stmt.executeQuery(sql);
					int count = 0;
					while (true) {
						count++;
						if (!(rs.next())) {
							if (count == 1) {
								System.out.println("잘못된 아이디이거나 등록되지 않은 아이디입니다.");
								User_id_wrong++;
							}
							count = 0;
							break;
						}

						String real_id = rs.getString(1);
						String real_pw = rs.getString(2);

						if (real_pw.equals(PW)) {
							/* 접속자가 관리자 일 때 */
							if (real_id.contains("manager"))
								now_USER_Type = 3;

							System.out.printf("%s 님 반갑습니다!\n", ID);
							now_USER_ID = real_id;
							now_USER_PW = real_pw;
							result = 1;
							return result;
						} else {
							System.out.println("잘못된 비밀번호입니다.\n");
							User_pw_wrong++;
						}
					}
					conn.commit();
				} catch (SQLException ex2) {
					System.err.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
			}
			break;
		/* [2] 판매 기관 */
		case 2:
			System.out.println("[2] 사용자/관리자를 선택하셨습니다!\n\n");

			int Org_id_wrong = 0;
			int Org_pw_wrong = 0;
			while (true) {
				/* 3번째 실패 시 메인 화면으로 전환 */
				if (Org_id_wrong > 1 || Org_pw_wrong > 1) {
					System.out.println("로그인에 실패하였습니다!");
					result = 0;
					break;
				}
				System.out.println("기관 이름과 비밀번호를 입력해주세요.\n\n");
				System.out.printf("<기관 이름>: ");
				ID = keyboard.next();
				System.out.printf("<비밀번호>: ");
				PW = keyboard.next();
				try {
					sql = "SELECT O.ORGNAME, O.ORG_PW \r\n" + "FROM ORGANIZATION O \r\n" + "WHERE O.ORGNAME = '" + ID
							+ "'";
					ResultSet rs = stmt.executeQuery(sql);
					int count = 0;
					while (true) {
						count++;
						if (!(rs.next())) {
							if (count == 1) {
								System.out.println("잘못된 기관 이름이거나 등록되지 않은 기관입니다.");
								Org_id_wrong++;
							}
							count = 0;
							break;
						}

						String real_id = rs.getString(1);
						String real_pw = rs.getString(2);

						if (real_pw.equals(PW)) {
							/* 접속자가 기관 일 때 */
							now_USER_Type = 2;
							System.out.printf("%s 님 반갑습니다!\n", ID);
							now_USER_ID = real_id;
							result = 1;
							return result;
						} else {
							System.out.println("잘못된 비밀번호입니다.\n");
							Org_pw_wrong++;
						}
					}
					conn.commit();
				} catch (SQLException ex2) {
					System.err.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
			}
			break;
		}
		return result;
	}// 로그인 함수 종료

	/* 회원가입 함수 */
	public static void Sign_up(Connection conn, Statement stmt) {
		String sql;
		int SignUp_menu_num, res;

		System.out.println("[2] 회원가입을 선택하셨습니다!\n\n");

		System.out.println("-----------------------------------");
		System.out.println("회원가입하고자 하는 유형을 선택하십시오.");
		System.out.println("---------------메뉴-----------------");
		System.out.println("[1] 사용자       [2] 판매 기관");
		System.out.println("-----------------------------------");
		System.out.println("-----------------------------------");

		/* 메뉴 입력받기 */
		System.out.printf("<회원가입 메뉴를 선택해주세요>: ");
		SignUp_menu_num = keyboard.nextInt();

		switch (SignUp_menu_num) {
		/* 회원가입: [1] 사용자/관리자 */
		case 1:
			System.out.println("회원가입: [1] 사용자를 선택하셨습니다!\n\n");

			String iUser_Id = null, iUser_Name, iUser_Pw, iUser_Addr, iUser_Age, iUser_PhoneN, iUser_Gender,
					iUser_Email;
			int iUser_wrong_num = 0;

			System.out.println("회원가입을 진행합니다. manager를 포함하지 않는 id를 작성해주새요.\n");
			while (true) {
				if (iUser_wrong_num > 1) {
					System.out.println("회원가입에 실패하였습니다. \n\n");
					return;
				}
				System.out.printf("<아이디>: ");
				iUser_Id = keyboard.next();

				if (iUser_Id.contains("manager")) {
					System.out.printf("id에 manager를 포함할 수 없습니다. 다시 입력해 주세요.\n\n");
					continue;
				}

				try {
					sql = "SELECT U.USERID \r\n" + "FROM \"USER\" U \r\n" + "WHERE U.USERID = '" + iUser_Id + "'";
					ResultSet rs = stmt.executeQuery(sql);
					if (!(rs.next())) {
						System.out.println("사용 가능한 아이디입니다.");
						break;
					} else {
						iUser_wrong_num++;
						System.out.println("이미 존재하는 아이디입니다.");
					}

				} catch (SQLException ex2) {
					System.err.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
			}

			System.out.printf("<성명>: ");
			iUser_Name = keyboard.next();

			System.out.printf("<비밀번호>: ");
			iUser_Pw = keyboard.next();

			System.out.printf("<주소>: ");
			iUser_Addr = keyboard.next();

			System.out.printf("<나이>: ");
			iUser_Age = keyboard.next();

			System.out.printf("<전화번호>: 010-xxxx-xxxx 형식으로 입력해 주세요: ");
			iUser_PhoneN = keyboard.next();

			System.out.printf("<성별>: F(여성) 또는 M(남성)을 입력해 주세요: ");
			iUser_Gender = keyboard.next();

			System.out.printf("<이메일 주소>: ");
			iUser_Email = keyboard.next();

			try {
				sql = "INSERT INTO \"USER\" VALUES (" + "'" + iUser_Id + "', " + "'" + iUser_Name + "', " + "'"
						+ iUser_Pw + "', " + "'" + iUser_Addr + "', " + iUser_Age + ", " + iUser_PhoneN + ", " + "'"
						+ iUser_Gender + "'" + "'" + iUser_Email + "')";
				res = stmt.executeUpdate(sql);
				if (res == 1) {
					System.out.println("회원가입에 성공하였습니다.\n");
					conn.commit();
					return;
				} else {
					System.out.println("회원가입에 실패하였습니다.\n");
					return;
				}
			} catch (SQLException ex2) {
				System.err.println("sql error = " + ex2.getMessage());
				System.exit(1);
			}
			break;
		/* 회원가입: [2] 판매 기관 */
		case 2:
			System.out.println("회원가입: [2] 판매 기관을 선택하셨습니다!\n\n");
			String iOrgName = null, iOrg_Purpose, iOrg_Region, iOrg_AffiliateDate, iOrg_AffiliatePeriod, iOrg_PW;
			int iOrg_wrong_num = 0;

			System.out.println("회원가입을 진행합니다. 기관 이름을 작성해주새요.\n");
			while (true) {
				if (iOrg_wrong_num > 1) {
					System.out.println("회원가입에 실패하였습니다. \n\n");
					return;
				}
				System.out.printf("<기관이름>: ");
				iOrgName = keyboard.next();

				try {
					sql = "SELECT O.ORGNAME \r\n" + "FROM ORGANIZATION O \r\n" + "WHERE O.ORGNAME = '" + iOrgName + "'";
					ResultSet rs = stmt.executeQuery(sql);
					if (!(rs.next())) {
						System.out.println("등록 가능한 기관입니다.");
						break;
					} else {
						iOrg_wrong_num++;
						System.out.println("이미 존재하는 기관입니다.");
					}

				} catch (SQLException ex2) {
					System.err.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
			}

			System.out.printf("<기관 목적: L(대여) 또는 S(판매)를 입력해주세요.>: ");
			iOrg_Purpose = keyboard.next();

			System.out.printf("<지역>: ");
			iOrg_Region = keyboard.next();

			System.out.printf("<제휴시작 날짜: yyyy-mm-dd 형식으로 입력해주세요. ex) 2021-03-01>: ");
			iOrg_AffiliateDate = keyboard.next();

			System.out.printf("<제휴기간: 일 수로 입력해주세요. ex) 365>: ");
			iOrg_AffiliatePeriod = keyboard.next();

			System.out.printf("<비밀번호>: ");
			iOrg_PW = keyboard.next();

			try {
				sql = "INSERT INTO ORGANIZATION VALUES (" + "'" + iOrgName + "', " + "'" + iOrg_Purpose + "', " + "'"
						+ iOrg_Region + "', " + "'" + iOrg_AffiliateDate + "', " + iOrg_AffiliatePeriod + ", " + "'"
						+ iOrg_PW + "')";
				res = stmt.executeUpdate(sql);
				if (res == 1) {
					System.out.println("회원가입에 성공하였습니다.\n");
					conn.commit();
					return;
				} else {
					System.out.println("회원가입에 실패하였습니다.\n");
					return;
				}
			} catch (SQLException ex2) {
				System.err.println("sql error = " + ex2.getMessage());
				System.exit(1);
			}
			break;
		} // 회원가입 분류 switch문 종료
	}// 회원가입 함수 종료

	public static void orderoption(Connection conn, Statement stmt) {
		System.out.println("씨앗 주문 방법을 선택해주세요.");
		System.out.println("[1] 이름으로 검색하기  [2] 용도에 따라 검색하기  [3] 인기 순위에 따라 검색하기");
		String OrderMenu = keyboard.next();

		if (OrderMenu.equalsIgnoreCase("1")) {
			System.out.println("\r\n'[1] 이름으로 검색하기'를 선택하셨습니다!\r\n");

			// 씨앗 전체 목록 출력
			try {
				String sql = "SELECT SeedName, VarietyName, H.OrgName, Org_Purpose\r\n"
						+ "FROM \"SEED\" S INNER JOIN HAS H ON S.VarietyID = H.VarietyID INNER JOIN \"ORGANIZATION\" ORG ON H.OrgName = ORG.OrgName";
				ResultSet rs = stmt.executeQuery(sql);
				System.out.println("SEEDNAME | VARIETYNAME | ORGNAME | ORG_PURPOSE");
				System.out.println("------------------------------------");
				while (rs.next()) {
					String outputSeedName = rs.getString(1);
					String outputVarietyName = rs.getString(2);
					String outputOrgName = rs.getString(3);
					String outputOrgPurpose = rs.getString(4);
					if (outputOrgPurpose.equalsIgnoreCase("L"))
						outputOrgPurpose = "대여";
					else if (outputOrgPurpose.equalsIgnoreCase("S"))
						outputOrgPurpose = "판매";
					System.out.println(outputSeedName + " | " + outputVarietyName + " | " + outputOrgName + " | "
							+ outputOrgPurpose);
				}
			} catch (SQLException ex2) {
				System.err.println("sql error = " + ex2.getMessage());
				System.exit(1);
			}

			ordering(conn, stmt);
		} else if (OrderMenu.equalsIgnoreCase("2")) {
			System.out.println("\r\n'[2] 용도에 따라 검색하기'를 선택하셨습니다!\r\n");
			int orgcnt = 0;

			// 씨앗 용도 출력
			try {
				String sql = "SELECT DISTINCT S.SeedPurPose \r\n" + "FROM SEED S \r\n";
				ResultSet rs = stmt.executeQuery(sql);
				System.out.println("씨앗 용도");
				System.out.println("------------------------------------");
				while (rs.next()) {
					String outputSeedPurPose = rs.getString(1);
					System.out.println(outputSeedPurPose);
				}
			} catch (SQLException ex2) {
				System.err.println("sql error = " + ex2.getMessage());
				System.exit(1);
			}

			String inputSeedPurPose = "";
			inputSeedPurPose = keyboard.next(); // 씨앗 용도 입력받기
			// 용도에 따른 씨앗 출력
			try {
				String sql = "SELECT SeedName, VarietyName, H.OrgName, Org_Purpose\r\n"
						+ "FROM \"SEED\" S INNER JOIN HAS H ON S.VarietyID = H.VarietyID INNER JOIN \"ORGANIZATION\" ORG ON H.OrgName = ORG.OrgName\r\n"
						+ "WHERE S.SeedPurpose = '" + inputSeedPurPose + "'";
				ResultSet rs = stmt.executeQuery(sql);
				System.out.println("SEEDNAME | VARIETYNAME | ORGNAME | ORG_PURPOSE");
				System.out.println("-----------------------------------------------");
				while (rs.next()) {
					String outputSeedName = rs.getString(1);
					String outputVarietyName = rs.getString(2);
					String outputOrgName = rs.getString(3);
					String outputOrgPurpose = rs.getString(4);
					if (outputOrgPurpose.equalsIgnoreCase("L"))
						outputOrgPurpose = "대여";
					else if (outputOrgPurpose.equalsIgnoreCase("S"))
						outputOrgPurpose = "판매";
					System.out.println(outputSeedName + " | " + outputVarietyName + " | " + outputOrgName + " | "
							+ outputOrgPurpose);
					orgcnt++;
				}
				if (orgcnt == 0) {
					System.out.println("죄송합니다. '" + inputSeedPurPose + "' 씨앗을 보유한 기관이 없습니다.");
					return;
				}
			} catch (SQLException ex2) {
				System.err.println("sql error = " + ex2.getMessage());
				System.exit(1);
			}
			ordering(conn, stmt);
		}

		else if (OrderMenu.equalsIgnoreCase("3")) {
			System.out.println("\r\n[3] 인기순위에 따라 검색하기를 선택하셨습니다!\r\n");
			System.out.println("[1] 나이별 인기 순위  [2] 기간별 인기 순위");
			int topMenu = keyboard.nextInt();
			String VarietyName = "";
			String SeedName = "";

			if (topMenu == 1) {
				System.out.println("\r\n'[1] 나이별 인기순위에 따라 검색하기'를 선택하셨습니다!\r\n");
				System.out.println("	[1] 청소년  [2] 청년  [3] 중년  [4] 장년");
				// varietyID를 입력
				int ageMenu = keyboard.nextInt();
				if (ageMenu == 1) {
					System.out.println("\r\n'[1] 청소년 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						// 품종 번호 출력
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_UserID, OD_VarietyID, Age, Quantity, SeedName, VarietyName\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_USERID, OD.OD_VarietyID, AGE, OD.Quantity, S.SeedName, S.VarietyName\r\n"
								+ "                 FROM \"USER\" U\r\n"
								+ "                 INNER JOIN \"ORDER\" OD ON U.UserID = OD.OD_UserID\r\n"
								+ "                 FULL OUTER JOIN SEED S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE Age < 20)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}
					} catch (SQLException ex2) {
						System.err.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				} else if (ageMenu == 2) {
					System.out.println("\r\n'[2] 청년 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						// 품종 번호 출력
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_UserID, OD_VarietyID, Age, Quantity, SeedName, VarietyName\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_USERID, OD.OD_VarietyID, AGE, OD.Quantity, S.SeedName, S.VarietyName\r\n"
								+ "                 FROM \"USER\" U\r\n"
								+ "                 INNER JOIN \"ORDER\" OD ON U.UserID = OD.OD_UserID\r\n"
								+ "                 FULL OUTER JOIN SEED S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE 20 <= Age AND AGE < 31)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}
					} catch (SQLException ex2) {
						System.err.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				} else if (ageMenu == 3) {
					System.out.println("\r\n'[3] 중년 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						// 품종 번호 출력
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_UserID, OD_VarietyID, Age, Quantity, SeedName, VarietyName\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_USERID, OD.OD_VarietyID, AGE, OD.Quantity, S.SeedName, S.VarietyName\r\n"
								+ "                 FROM \"USER\" U\r\n"
								+ "                 INNER JOIN \"ORDER\" OD ON U.UserID = OD.OD_UserID\r\n"
								+ "                 FULL OUTER JOIN SEED S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE 31 <= Age AND AGE < 51)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}
					} catch (SQLException ex2) {
						System.err.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				} else if (ageMenu == 4) {
					System.out.println("\r\n'[4] 장년 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						// 품종 번호 출력
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_UserID, OD_VarietyID, Age, Quantity, SeedName, VarietyName\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_USERID, OD.OD_VarietyID, AGE, OD.Quantity, S.SeedName, S.VarietyName\r\n"
								+ "                 FROM \"USER\" U\r\n"
								+ "                 INNER JOIN \"ORDER\" OD ON U.UserID = OD.OD_UserID\r\n"
								+ "                 FULL OUTER JOIN SEED S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE 51 <= Age)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}
					} catch (SQLException ex2) {
						System.err.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				}
			}

			else if (topMenu == 2) {
				System.out.println("\r\n'[2] 기간별 인기순위'를 선택하셨습니다!\r\n");
				System.out.println("	[1] 오늘  [2] 일주일  [3] 한달  [4] 1년");
				int periodMenu = keyboard.nextInt();

				if (periodMenu == 1) {
					System.out.println("'[1]오늘 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_VarietyID, Purchase_Date, SeedName, VarietyName, Quantity\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_VarietyID, OD.Purchase_Date, S.SeedName, S.VarietyName, OD.Quantity\r\n"
								+ "                 FROM \"ORDER\" OD FULL OUTER JOIN \"SEED\" S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE TO_CHAR(SYSDATE, 'YYYY-MM-DD') < Purchase_Date)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}

					} catch (SQLException ex2) {
						System.out.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				}

				else if (periodMenu == 2) {
					System.out.println("'[2]일주일 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_VarietyID, Purchase_Date, SeedName, VarietyName, Quantity\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_VarietyID, OD.Purchase_Date, S.SeedName, S.VarietyName, OD.Quantity\r\n"
								+ "                 FROM \"ORDER\" OD FULL OUTER JOIN \"SEED\" S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE TO_CHAR(SYSDATE - 7, 'YYYY-MM-DD') < Purchase_Date)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}

					} catch (SQLException ex2) {
						System.out.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				}

				else if (periodMenu == 3) {
					System.out.println("'[3]한달 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_VarietyID, Purchase_Date, SeedName, VarietyName, Quantity\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_VarietyID, OD.Purchase_Date, S.SeedName, S.VarietyName, OD.Quantity\r\n"
								+ "                 FROM \"ORDER\" OD FULL OUTER JOIN \"SEED\" S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE TO_CHAR(SYSDATE - 30, 'YYYY-MM-DD') < Purchase_Date)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}

					} catch (SQLException ex2) {
						System.out.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				}

				else if (periodMenu == 4) {
					System.out.println("'[4]1년 인기순위 검색하기'를 선택하셨습니다!\r\n");
					try {
						String sql = "SELECT\r\n"
								+ "DENSE_RANK() OVER (ORDER BY VARIETYCOUNT DESC, Total_QUANTITY DESC) ORDER_COUNT, TEEN_SEEDNAME AS SEEDNAME, TEEN_VARIETYNAME AS VARIETYNAME\r\n"
								+ "FROM(\r\n"
								+ "     SELECT VarietyName AS TEEN_VARIETYNAME, SeedName AS TEEN_SEEDNAME, COUNT(*) AS VARIETYCOUNT, SUM(Quantity) AS Total_QUANTITY\r\n"
								+ "     FROM (\r\n"
								+ "           SELECT OD_VarietyID, Purchase_Date, SeedName, VarietyName, Quantity\r\n"
								+ "           FROM (\r\n"
								+ "                 SELECT OD.OD_VarietyID, OD.Purchase_Date, S.SeedName, S.VarietyName, OD.Quantity\r\n"
								+ "                 FROM \"ORDER\" OD FULL OUTER JOIN \"SEED\" S ON OD.OD_VarietyID = S.VarietyID)\r\n"
								+ "           WHERE TO_CHAR(SYSDATE - 365, 'YYYY-MM-DD') < Purchase_Date)\r\n"
								+ "     GROUP BY OD_VarietyID, VarietyName, SeedName\r\n"
								+ "     ORDER BY COUNT(OD_VarietyID) DESC)";
						ResultSet rs = stmt.executeQuery(sql);
						System.out.println("순위 |   씨앗 이름   |  품종 이름");
						System.out.println("-----------------------------------");
						while (rs.next()) {
							String OrderCount = rs.getString(1);
							SeedName = rs.getString(2);
							VarietyName = rs.getString(3);
							System.out.println(OrderCount + " | " + SeedName + " | " + VarietyName);
						}

					} catch (SQLException ex2) {
						System.out.println("sql error = " + ex2.getMessage());
						System.exit(1);
					}
					topOrdering(conn, stmt);
					ordering(conn, stmt);
				}
			}
		}
	}

	public static void home(Connection conn, Statement stmt) {
		System.out.println("welcome");
	}

	public static void topOrdering(Connection conn, Statement stmt) {
		String inputSeedName = "";

		// 씨앗 품종 목록이랑 보유기관 출력
		System.out.println("\r\n<< 구매/대여 하실 씨앗의 이름을 알려주세요 >>");
		inputSeedName = keyboard.next();

		try {
			String sql = "SELECT S.VarietyName, ORG.OrgName, ORG.Org_Purpose \r\n"
					+ "FROM \"SEED\" S INNER JOIN HAS H ON S.VarietyID = H.VarietyID INNER JOIN ORGANIZATION ORG ON H.OrgName = ORG.OrgName \r\n"
					+ "WHERE S.SeedName LIKE '" + inputSeedName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("<< 검색하신 씨앗의 품종과 그 씨앗을 보유하고 있는 기관의 목록입니다 >>");
			System.out.println("Selected SEEDNAME: " + inputSeedName);
			System.out.println("VARIETYNAME | ORGANIZATION NAME               | PURPOSE");
			System.out.println("--------------------------------------------------------");

			while (rs.next()) {
				String outputVarietyName = rs.getString(1);
				String outputOrgName = rs.getString(2);
				String outputOrgPurpose = rs.getString(3);
				if (outputOrgPurpose.equalsIgnoreCase("L"))
					outputOrgPurpose = "대여";
				else if (outputOrgPurpose.equalsIgnoreCase("S"))
					outputOrgPurpose = "판매";
				System.out.println(outputVarietyName + " | " + outputOrgName + " | " + outputOrgPurpose);
			}
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	public static void ordering(Connection conn, Statement stmt) {
		System.out.println("\r\n구매 및 대여를 원하는 씨앗의 이름, 품종, 기관을 선택해주세요.");
		System.out.println("(공백으로만 구분하여 값을 입력해주세요!)");
		String inputSeedName = keyboard.next();
		String inputVarietyName = keyboard.next();
		String inputOrgName = keyboard.next();

		String inputOrgPurpose = "";
		String PurposeKOR = "";
		int inputQuantity = 0;
		// 목적 자동 입력
		try {
			String sql = "SELECT Org_Purpose \r\n" + "FROM ORGANIZATION \r\n" + "WHERE OrgName LIKE '" + inputOrgName
					+ "' \r\n";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				inputOrgPurpose = rs.getString(1);
			}
			if (inputOrgPurpose.equalsIgnoreCase("L"))
				PurposeKOR = "대여";
			else if (inputOrgPurpose.equalsIgnoreCase("S"))
				PurposeKOR = "구매";
			else if (inputOrgPurpose.equalsIgnoreCase("A")) {
				System.out.println("이 기관은 구매와 대여 모두가 가능한 기관입니다. 구매를 원하시면 '구매'를, 대여를 원하시면 '대여'를 입력해주세요.");
				PurposeKOR = keyboard.next();
			}
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		if (PurposeKOR.equalsIgnoreCase("대여")) {
			System.out.println("대여는 한 번에 5g씩 가능하므로 대여하시는 양은 자동 입력되었습니다.");
			inputQuantity = 5;
		} else {
			System.out.println("\r\n<< 얼마나 많은 양의 씨앗을 구매/대여 할지 입력해주세요. (단위는 g입니다!) >>");
			inputQuantity = keyboard.nextInt();
			keyboard.nextLine();
		}

		// 주문 내역 확인
		System.out.println("\r\n<< 주문 내역을 확인해주시고, 맞으면 YES, 틀리면 NO를 입력해 주세요 >>");
		System.out.println("씨앗 이름: " + inputSeedName + ", 품종명: " + inputVarietyName + ", 대여/구매 기관: " + inputOrgName
				+ " (" + PurposeKOR + "), 수량: " + inputQuantity + "g");

		String answer = keyboard.next();
		if (answer.equalsIgnoreCase("YES")) {
			/*---------------------------------------------*/
			// 로그인 클래스 만들어지면 INSERT부분 수정하겠습니다
			/*---------------------------------------------*/
			System.out.printf("\r\n유저 아이디: %s\n", now_USER_ID);
			String outputVarietyID = "id";
			try {
				// VarietyID 구하기
				String sql = "SELECT S.VarietyID \r\n"
						+ "FROM \"SEED\" S FULL OUTER JOIN HAS H ON S.VarietyID = H.VarietyID \r\n"
						+ "WHERE S.SeedName LIKE '" + inputSeedName + "' \r\n" + "AND S.VarietyName LIKE'"
						+ inputVarietyName + "' \r\n" + "AND H.OrgName LIKE '" + inputOrgName + "'";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					outputVarietyID = rs.getString(1);
				}
			} catch (SQLException ex2) {
				System.err.println("sql error = " + ex2.getMessage());
				System.exit(1);
			}
			if (PurposeKOR.equalsIgnoreCase("구매"))
				inputOrgPurpose = "B";
			else if (PurposeKOR.equalsIgnoreCase("대여"))
				inputOrgPurpose = "R";
			try {
				String sql = "INSERT INTO \"ORDER\" VALUES (ORDER_SEQ.NEXTVAL, '" + now_USER_ID
						+ "', TO_CHAR(SYSDATE, 'YYYY-MM-qDD HH24:MI'), '" + inputOrgPurpose + "', '" + inputOrgName
						+ "', '" + outputVarietyID + "', " + inputQuantity + ")";
				System.out.println(sql);
				ResultSet rs = stmt.executeQuery(sql);
				System.out.println("\r\n<< 주문이 완료되었습니다. >>");
				conn.commit();
			} catch (SQLException ex2) {
				System.err.println("sql error = " + ex2.getMessage());
				System.exit(1);
			}
		} else if (answer.equalsIgnoreCase("NO")) {
			System.out.println("메뉴 선택 페이지로 돌아갑니다.");
		}
	}

	// 관리자 주문 목록 첫 페이지_사용자 검색/기관 검색
	public static void manOrderList(Connection conn, Statement stmt) {
		while (true) {
			System.out.println("[1] 사용자 주문 목록 검색  [2] 기관 주문 목록 검색");
			int opt = keyboard.nextInt();
			if (opt == 1) {
				myOrderList(conn, stmt);
				break;
			} else if (opt == 2) {
				OrgOrderList(conn, stmt);
				break;
			} else
				System.out.println("잘못된 옵션을 선택하셨습니다. 다시 선택해주세요.");
		}
	}

	// 사용자와 관리자가 사용할 주문 목록 출력 함수_아이디로 주문 목록 검색
	public static void myOrderList(Connection conn, Statement stmt) {
		System.out.println("<< 주문 목록 출력을 위해 아이디를 입력해주세요 >>");
		System.out.print("ID: ");
		String UserID = keyboard.next();
		try {
			// VarietyID 구하기
			String sql = "SELECT OrderNum, Purchase_Date, SeedName, VarietyName, Quantity, OD_OrgName, Order_Purpose\r\n"
					+ "FROM \"ORDER\" INNER JOIN \"SEED\" ON OD_VarietyID = VarietyID\r\n" + "WHERE OD_UserID LIKE '"
					+ UserID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(UserID + "의 주문 내역");
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				String outputOrderNum = rs.getString(1);
				String outputPurchaseDate = rs.getString(2);
				String outputSeedName = rs.getString(3);
				String outputVarietyName = rs.getString(4);
				String outputQuantity = rs.getString(5);
				String outputOrgName = rs.getString(6);
				String outputOrderPurpose = rs.getString(7);
				if (outputOrderPurpose.equalsIgnoreCase("R"))
					outputOrderPurpose = "대여";
				else if (outputOrderPurpose.equalsIgnoreCase("B"))
					outputOrderPurpose = "구매";
				System.out.println("주문번호: " + outputOrderNum + " | 주문날짜: " + outputPurchaseDate + " | 주문한 씨앗 이름: "
						+ outputSeedName + " | 주문한 품종 이름 : " + outputVarietyName + " | 주문량(g) : " + outputQuantity
						+ " | 주문 기관: " + outputOrgName + " | 주문 목적: " + outputOrderPurpose);
			}

		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	// 기관과 관리자가 사용할 주문 목록 출력 함수_기관 이름으로 주문 목록 검색
	public static void OrgOrderList(Connection conn, Statement stmt) {
		System.out.println("<< 주문 목록 출력을 위해 기관명을 입력해주세요 >>");
		System.out.print("OrgName: ");
		String OrgName = keyboard.next();
		try {
			// VarietyID 구하기
			String sql = "SELECT OrderNum, OD_UserID, Purchase_Date, SeedName, VarietyName, Quantity\r\n"
					+ "FROM \"ORDER\" INNER JOIN \"SEED\" ON OD_VarietyID = VarietyID\r\n" + "WHERE OD_OrgName LIKE '"
					+ OrgName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("기관" + OrgName + "의 주문서 내역");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (rs.next()) {
				String outputOrderNum = rs.getString(1);
				String outputUserID = rs.getString(2);
				String outputPurchaseDate = rs.getString(3);
				String outputSeedName = rs.getString(4);
				String outputVarietyName = rs.getString(5);
				String outputQuantity = rs.getString(6);
				System.out.println("주문번호: " + outputOrderNum + "주문한 유저 아이디: " + outputUserID + " | 주문날짜: "
						+ outputPurchaseDate + " | 주문한 씨앗 이름: " + outputSeedName + " | 주문한 품종 이름 : " + outputVarietyName
						+ " | 주문량(g) : " + outputQuantity);
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

	/* 마이페이지 함수 */
	public static void Mypage(Connection conn, Statement stmt) {

		int menu_num, res;
		int wrong_count = 0;
		String check_ID, check_PW, sql, 
				change_Name, change_PW, change_Address, 
				change_Gender, change_Email;
		int change_Age, change_PhoneN;
		
		System.out.println("-----------------------------------");
		System.out.println("---------------메뉴-----------------");
		System.out.println("[1] 회원 정보 수정  [2] 회원 탈퇴");
		System.out.println("-----------------------------------");
		System.out.println("-----------------------------------");
		System.out.println("메뉴를 선택하세요: ");

		menu_num = keyboard.nextInt();

		switch (menu_num) {
		/*[1] 회원 정보 수정*/
		case 1:
			System.out.println("[1] 회원 정보 수정을 선택하셨습니다!\n");
			System.out.println("-----------------------------------");
			System.out.println("회원 정보 수정을 위해 본인 인증을 진행합니다.");
			System.out.println("아이디와 비밀번호를 다시 한 번 입력해주세요.");
			
			while(true)
			{
				if (wrong_count > 1) {
					System.out.println("본인인증에 실패하였습니다!\n\n\n");
					wrong_count = 0;
					return;
				}
				
				System.out.println("-----------------------------------");
				System.out.println("아이디와 비밀번호를 입력해주세요.\n\n");
				System.out.printf("<아이디>: ");
				check_ID = keyboard.next();
				System.out.printf("<비밀번호>: ");
				check_PW = keyboard.next();

				if (now_USER_ID.equals(check_ID) && now_USER_PW.equals(check_PW)) 
				{
					System.out.printf("%s 님 본인 인증을 완료하였습니다!\n", check_ID);
					break;
				} 
				else 
				{
					System.out.println("존재하지 않는 아이디이거나 비밀번호가 일치하지 않습니다.\n");
					wrong_count++;
				}
			}
			
			System.out.println("수정할 정보를 선택해주세요. 단, 아이디는 변경이 불가능합니다.");
			System.out.println("-----------------------------------");
			System.out.println("---------------메뉴-----------------");
			System.out.println("[1] 이름     [2] 비밀번호  [3] 주소  [4] 나이");
			System.out.println("[5] 전화번호  [6] 성별     [7] 이메일 주소");
			System.out.println("-----------------------------------");
			System.out.println("-----------------------------------");
			System.out.println("메뉴를 선택하세요: ");
			menu_num = keyboard.nextInt();
			
			switch(menu_num)
			{
			case 1:
				System.out.println("[1] 이름을 수정합니다.");
				System.out.println("-----------------------------------");
				System.out.printf("현재 등록된 이름: %s\n", now_USER_ID);
				System.out.println("변경하실 이름을 입력해주세요.");
				System.out.printf("<성명>: ");
				change_Name = keyboard.next();
				
				try {
					sql = "UPDATE \"USER\" SET USER_NAME = "
							+ "\'"+change_Name+"\' WHERE USERID = \'" + now_USER_ID + "\'";
					System.out.println(sql);
					res = stmt.executeUpdate(sql);
					conn.commit();
				}catch(SQLException ex2) {
					System.out.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
				break;
			case 2:
				System.out.println("[2] 비밀번호를 수정합니다.");
				System.out.println("-----------------------------------");
				System.out.println("변경하실 비밀번호를 입력해주세요: ");
				System.out.printf("<비밀번호>: ");
				change_PW = keyboard.next();
				
				try {
					sql = "UPDATE \"USER\" SET PW = "
							+ "\'"+change_PW+"\' WHERE USERID = \'" + now_USER_ID + "\'";
					
					res = stmt.executeUpdate(sql);
					conn.commit();
				}catch(SQLException ex2) {
					System.out.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
				break;
			case 3:
				System.out.println("[3] 주소를 수정합니다.");
				System.out.println("-----------------------------------");
				System.out.println("변경하실 주소를 입력해주세요: ");
				System.out.printf("<주소>: ");
				change_Address = keyboard.next();
				
				try {
					sql = "UPDATE \"USER\" SET ADDRESS = "
							+ "\'"+change_Address+"\' WHERE USERID = \'" + now_USER_ID + "\'";
					
					res = stmt.executeUpdate(sql);
					conn.commit();
				}catch(SQLException ex2) {
					System.out.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
				break;
			case 4:
				System.out.println("[4] 나이를 수정합니다.");
				System.out.println("-----------------------------------");
				System.out.println("변경하실 나이를 입력해주세요: ");
				System.out.printf("<나이>: ");
				change_Age = keyboard.nextInt();
				
				try {
					sql = "UPDATE \"USER\" SET AGE = "
							+change_Age+" WHERE USERID = \'" + now_USER_ID + "\'";
					
					res = stmt.executeUpdate(sql);
					conn.commit();
				}catch(SQLException ex2) {
					System.out.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
				break;
			case 5:
				System.out.println("[5] 전화번호를 수정합니다.");
				System.out.println("-----------------------------------");
				System.out.println("변경하실 전화번호를 입력해주세요: ");
				System.out.printf("<전화번호>: 010-xxxx-xxxx 형식으로 입력해 주세요: ");
				change_PhoneN = keyboard.nextInt();
				
				try {
					sql = "UPDATE \"USER\" SET PHONE_NUM = "
							+change_PhoneN+" WHERE USERID = \'" + now_USER_ID + "\'";
					
					res = stmt.executeUpdate(sql);
					conn.commit();
				}catch(SQLException ex2) {
					System.out.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
				break;
			case 6:
				System.out.println("[6] 성별을 수정합니다.");
				System.out.println("-----------------------------------");
				System.out.println("변경하실 성별을 입력해주세요: ");
				System.out.printf("<성별>: F(여성) 또는 M(남성)을 입력해 주세요: ");
				change_Gender = keyboard.next();
				
				try {
					sql = "UPDATE \"USER\" SET GENDER = "
							+ "\'"+change_Gender+"\'"+"\' WHERE USERID = \'" + now_USER_ID + "\'";
					
					res = stmt.executeUpdate(sql);
					conn.commit();
				}catch(SQLException ex2) {
					System.out.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
				break;
			case 7:
				System.out.println("[7] 이메일 주소를 수정합니다.");
				System.out.println("-----------------------------------");
				System.out.println("변경하실 이메일 주소를 입력해주세요: ");
				System.out.printf("<이메일 주소>: ");
				change_Email = keyboard.next();
				
				try {
					sql = "UPDATE \"USER\" SET EMAIL_ADR = "
							+ "\'"+change_Email+"\' WHERE USERID = \'" + now_USER_ID + "\'";
					
					res = stmt.executeUpdate(sql);
					conn.commit();
				}catch(SQLException ex2) {
					System.out.println("sql error = " + ex2.getMessage());
					System.exit(1);
				}
				break;
			}
			break;
		/*[2] 회원 탈퇴*/
		case 2:
			String yesOrNo;
			System.out.println("[2] 회원 탈퇴를 선택하셨습니다!\n");
			System.out.println("-----------------------------------");
			System.out.println("회원 탈퇴를 위해 본인 인증을 진행합니다.");
			System.out.println("아이디와 비밀번호를 다시 한 번 입력해주세요.");
			
			while(true)
			{
				if (wrong_count > 1) {
					System.out.println("본인인증에 실패하였습니다!\n\n\n");
					return;
				}
				
				System.out.println("-----------------------------------");
				System.out.println("아이디와 비밀번호를 입력해주세요.\n\n");
				System.out.printf("<아이디>: ");
				check_ID = keyboard.next();
				System.out.printf("<비밀번호>: ");
				check_PW = keyboard.next();

				if (now_USER_ID.equals(check_ID) && now_USER_PW.equals(check_PW)) 
				{
					System.out.printf("%s 님 본인 인증을 완료하였습니다!\n", check_ID);
					break;
				} 
				else 
				{
					System.out.println("존재하지 않는 아이디이거나 비밀번호가 일치하지 않습니다.\n");
					wrong_count++;
				}
			}
			
			System.out.println("-----------------------------------");
			System.out.println("회원 탈퇴를 진행합니다.");
			System.out.println("회원 탈퇴를 정말로 진행하시겠습니까? (y 또는 n을 입력해주세요.)");
			
			yesOrNo = keyboard.next();
			
			if(yesOrNo.equalsIgnoreCase("y"))
			{
				System.out.println("회원탈퇴가 진횅됩니다.");
				
				/*탈퇴 진행 전, 주문 목록 모두 삭제*/
				/*탈퇴 진행 전, 작성한 커뮤니티 글 목록 모두 삭제*/
				/*탈퇴 진행*/
				
				System.out.println("회원탈퇴를 완료하였습니다. 이용해주셔서 감사합니다.");
			}
			else if(yesOrNo.equalsIgnoreCase("n"))
			{
				System.out.println("회원탈퇴를 진행하지 않습니다.");
				System.out.println("메인페이지로 돌아갑니다.");
			}
			break;
		} /*마이페이지 메뉴 선택 switch문 종료*/
	} /*마이페이지 함수 종료*/
	
	/*기업 씨앗 함수*/
	public static void SEEDIN(Connection conn, Statement stmt)
    {
        
        String VarietyID, Quantity, sql, query;
        int res, menuN;
        
		System.out.println("-----------------------------------");
		try {
 			sql = "SELECT H.VARIETYID " + "FROM \"HAS\" H " 
 					+ "WHERE H.ORGNAME = '" + now_USER_ID + "'";
 			ResultSet rs = stmt.executeQuery(sql);
 			
 			System.out.println("-----------------------------------");
 			System.out.println("현재 보유 씨앗");
 			System.out.println("-----------------------------------");
 			while(rs.next())
 			{
 				String tmpVID = rs.getString(1);
 				
 				System.out.println(tmpVID);
 				System.out.println("-----------------------------------");
 			}
 		} catch (SQLException ex2) {
 			System.err.println("sql error = " + ex2.getMessage());
 			System.exit(1);
 		}
		System.out.println("---------------메뉴-----------------");
		System.out.println("[1] 씨앗 등록  [2] 씨앗 삭제  [3] 씨앗 수정");
		System.out.println("-----------------------------------");
		System.out.println("-----------------------------------");
		System.out.println("메뉴를 선택하세요: ");
		
		menuN = keyboard.nextInt();
		
        switch(menuN)
        {
        /*[1] 씨앗 등록*/
        case 1:
        	 System.out.println("등록하실 씨앗의 정보를 입력해주세요");
        	 System.out.printf("출원번호 : ");
             VarietyID = keyboard.next();
             System.out.printf("수량 : ");
             Quantity = keyboard.next();

             try {
     			sql = "SELECT H.VARIETYID " + "FROM \"HAS\" H " 
     					+ "WHERE H.ORGNAME = '" + now_USER_ID + "'";
     			ResultSet rs = stmt.executeQuery(sql);
     			
     			while(rs.next())
     			{
     				String tmpVID = rs.getString(1);
     				
     				if(VarietyID.equals(tmpVID)) {
     					System.out.println("이미 존재하는 품종입니다.");
     					return;
     				}
     				else {
     					System.out.println("등록가능한 품종입니다.");
     					break;
     				}
     			}
     		} catch (SQLException ex1) {
     			System.err.println("sql error = " + ex1.getMessage());
     			System.exit(1);
     		}
             try {
             	query = "INSERT INTO HAS VALUES ("
             			+ "'" + VarietyID + "', "
             			+ "'" + now_USER_ID + "', " 
             			+  Quantity + ", 0)";
     			res = stmt.executeUpdate(query);
     			if (res == 1) {
     				System.out.println("씨앗등록에 성공하였습니다.\n");
     				conn.commit();
     				return;
     			} else {
     				System.out.println("씨앗등록에 실패하였습니다.\n");
     				return;
     			}
     		} catch (SQLException ex3) {
     			System.err.println("sql error = " + ex3.getMessage());
     			System.exit(1);
     		}
        	break;
        /*[2] 씨앗 삭제*/
        case 2:
        	System.out.println("삭제하실 씨앗의 품번을 입력해주세요");
        	System.out.printf("출원번호 : ");
            VarietyID = keyboard.next();
        	 try {
      			sql = "SELECT H.VARIETYID " + "FROM \"HAS\" H " 
      					+ "WHERE H.ORGNAME = '" + now_USER_ID + "'";
      			ResultSet rs = stmt.executeQuery(sql);
      			
      			int result = 0;
      			while(rs.next())
      			{
      				String tmpVID = rs.getString(1);
      				
      				if(VarietyID.equals(tmpVID)) {
      					result = 1;
      					break;
      				}
      			}
      			
      			if(result == 0)
      			{
      				System.out.println("해당 씨앗이 존재하지 않습니다.");
  					return;
      			}
      			
      			try {
                 	/*해당 씨앗 삭제*/
      				query = "DELETE FROM HAS WHERE VARIETYID = '"
                 			+ VarietyID + "'";
         			res = stmt.executeUpdate(query);
         			if (res == 1) {
         				System.out.println("씨앗삭제에 성공하였습니다.\n");
         				conn.commit();
         				return;
         			} else {
         				System.out.println("씨앗삭제에 실패하였습니다.\n");
         				return;
         			}
         		} catch (SQLException ex3) {
         			System.err.println("sql error = " + ex3.getMessage());
         			System.exit(1);
         		}

      		} catch (SQLException ex4) {
      			System.err.println("sql error = " + ex4.getMessage());
      			System.exit(1);
      		}
        	break;
        /*[3] 씨앗 수정*/
        case 3:
        	System.out.println("수정하실 씨앗의 품번과 수량을 입력해주세요");
        	System.out.printf("출원번호 : ");
            VarietyID = keyboard.next();
            System.out.printf("수정하실 수량 : ");
            Quantity = keyboard.next();
        	 try {
      			sql = "SELECT H.VARIETYID " + "FROM \"HAS\" H " 
      					+ "WHERE H.ORGNAME = '" + now_USER_ID + "'";
      			ResultSet rs = stmt.executeQuery(sql);
      			
      			int result = 0;
      			while(rs.next())
      			{
      				String tmpVID = rs.getString(1);
      				
      				if(VarietyID.equals(tmpVID)) {
      					result = 1;
      					break;
      				}
      			}
      			
      			if(result == 0)
      			{
      				System.out.println("해당 씨앗이 존재하지 않습니다.");
  					return;
      			}
      			
      			try {
                 	/*해당 씨앗 수량 수정*/
      				query = "UPDATE HAS SET QUANTITY = "
      						+ Quantity
      						+ " WHERE VARIETYID = '"
                 			+ VarietyID + "'";
         			res = stmt.executeUpdate(query);
         			if (res == 1) {
         				System.out.println("씨앗 수량 수정에 성공하였습니다.\n");
         				conn.commit();
         				return;
         			} else {
         				System.out.println("씨앗 수량 수정에 실패하였습니다.\n");
         				return;
         			}
         		} catch (SQLException ex3) {
         			System.err.println("sql error = " + ex3.getMessage());
         			System.exit(1);
         		}

      		} catch (SQLException ex4) {
      			System.err.println("sql error = " + ex4.getMessage());
      			System.exit(1);
      		}
        	break;
        } 
    }
	/*씨앗 등록 함수 종료*/
	
	/*관리자 씨앗 승인 함수*/
	public static void Permit_Seed_registration(Connection conn, Statement stmt)
	{
	        int res, menuN;
	        String sql, query;
	        
			System.out.println("-----------------------------------");
			try {
	 			sql = "SELECT H.ORGNAME, H.VARIETYID, S.SEEDNAME, S.VARIETYNAME, H.QUANTITY, " 
	 					+ "FROM HAS H, SEED S H.VARIETYID = S.VARIETYID AND H.PERMIT=0";
	 			ResultSet rs = stmt.executeQuery(sql);
	 			
	 			System.out.println("-----------------------------------");
	 			System.out.println("현재 승인 요청된 씨앗");
	 			System.out.println("-----------------------------------");
	 			while(rs.next())
	 			{
	 				String tmpORG = rs.getString(1);
	 				String tmpVID = rs.getString(2);
	 				String tmpSeedN = rs.getString(3);
	 				String tmpVarietyN = rs.getString(4);
	 				String tmpQ = rs.getString(5);
	 				
	 				System.out.println("기업이름: " +tmpORG + ", 품종번호: " + tmpVID 
	 						+ ", 씨앗이름: " + tmpSeedN + ", 품종이름: " + tmpVarietyN
	 						+ ", 수량: " + tmpQ);
	 				System.out.println("-----------------------------------");
	 			}
	 		} catch (SQLException ex2) {
	 			System.err.println("sql error = " + ex2.getMessage());
	 			System.exit(1);
	 		}
			
			String permitSeed, permitOrg;
			System.out.println("승인을 원하는 기업과 씨앗을 입력하세요");
			System.out.printf("기업: ");
			permitOrg = keyboard.next();
			System.out.printf("품종 번호: ");
			permitSeed = keyboard.next();
			
			try {
             	/*해당 씨앗 수량 수정*/
  				query = "UPDATE HAS SET RPERMIT = 1"
  						+ "WHERE ORGNMAE = '"
  						+ permitOrg
  						+ "' AND VARIETYID = '"
             			+ permitSeed + "'";
     			res = stmt.executeUpdate(query);
     			if (res == 1) {
     				System.out.println("씨앗 승인에 성공하였습니다.\n");
     				conn.commit();
     				return;
     			} else {
     				System.out.println("씨앗 승인에 실패하였습니다.\n");
     				return;
     			}
     		} catch (SQLException ex3) {
     			System.err.println("sql error = " + ex3.getMessage());
     			System.exit(1);
     		}
	}
	/*관리자 씨앗 승인 함수*/
	
	/*커뮤니티 게시판 글 작성 함수*/
	public static void BBwrite(Connection conn, PreparedStatement pstmt)
    {
	   String Purpose;
       String Title;
       String UserID;
       String Content;
       
       Scanner scan = new Scanner(System.in);
       
       System.out.println("목적 : ");
       Purpose = scan.nextLine();
       System.out.println("제목 : ");
       Title = scan.nextLine();
       System.out.println("아이디 : ");
       UserID = scan.nextLine();
       System.out.println("내용 : ");
       Content = scan.nextLine();

        try {
            String sql = "insert into \"BULLETIN_BOARD\" (BWNum,BWPurpose,BWTitle,BUserID,BWTime,BWContent) "
            		+ "values (BW_SEQ.NEXTVAL, ? , ? , ? , TO_CHAR(SYSDATE, 'YYYY.MM.DD HH24:MI'), ? )";
            
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, Purpose);
            pstmt.setString(2, Title);
            pstmt.setString(3, UserID);
            pstmt.setString(4, Content);
            int rowCount = pstmt.executeUpdate();
            //System.out.println(rowCount + "개의 행이 추가되었습니다.");
        }catch(SQLException ex2) {
          System.err.println("sql error = " + ex2.getMessage());
          System.exit(1);
       }
    }
	
	/*커뮤니티 글 읽기 함수*/
	public static void BBread(Connection conn, PreparedStatement pstmt)
    {
        System.out.println("1. 제목으로 검색  2.회원 아이디로 검색");
        Scanner a = new Scanner(System.in);
        int se = keyboard.nextInt();
        if (se == 1) 
        {
           System.out.println("검색할 제목을 입력하세요\n");
           String title = keyboard.nextLine();
           keyboard.nextLine();
        
           try {
              String sql = "select *"
                    + "from \"BULLETIN_BOARD\""
                    + "where BWTitle LIKE '%" + title +"%'";

              //Class.forName("oracle.jdbc.OracleDriver");
              pstmt = conn.prepareStatement(sql);
              ResultSet rs = pstmt.executeQuery();
              while(rs.next()) {
                 int no = rs.getInt("BWNum");
                 String p = rs.getString("BWPurpose");
                 String title1 = rs.getString("BWTitle");
                 String id = rs.getString("BUserID");
                 String co = rs.getString("BWContent");

                 System.out.println(no+", "+p+", "+title1+", "+id+", "+co);
              }
           }catch(SQLException ex2) {
              System.err.println("sql error = " + ex2.getMessage());
              System.exit(1);
           }
        }
        else
        {
           System.out.println("검색할 회원 아이디를 입력하세요\n");
            String id = keyboard.nextLine();
            
            try {
               String sql = "select *"
                        + "from \"BULLETIN_BOARD\""
                        + "where BUserID LIKE '"+id+"' ";

               //Class.forName("oracle.jdbc.OracleDriver");
           
               pstmt = conn.prepareStatement(sql);

               ResultSet rs = pstmt.executeQuery();
               while(rs.next()) {
                              int no = rs.getInt("BWNum");
                              String p = rs.getString("BWPurpose");
                              String title = rs.getString("BWTitle");
                              String id1 = rs.getString("BUserID");
                              String co = rs.getString("BWContent");
                              System.out.println(no+", "+p+", "+title+", "+id1+", "+co);
               }
            }catch(SQLException ex2) {
              System.err.println("sql error = " + ex2.getMessage());
              System.exit(1);
           }
        }
    }
	
	/*커뮤니티 글 삭제 함수*/
	public static void BBdelete(Connection conn, PreparedStatement pstmt)
    {
        int num;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("삭제를 원하는 글번호 : ");
        num = scan.nextInt();
        
        
        try {
           String sql ="delete from \"BULLETIN_BOARD\" where BWNum = "
        		   		+ num;
            //Class.forName("oracle.jdbc.OracleDriver");

            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,num);
            int rowCount = pstmt.executeUpdate();
            System.out.println(rowCount + "개의 글이 삭제되었습니다.");
       }catch(SQLException ex2) {
          System.err.println("sql error = " + ex2.getMessage());
          System.exit(1);
       }
    }
	
	/*공지사항 게시판 글 작성 함수*/
	public static void NBwrite(Connection conn, PreparedStatement pstmt)
    {
        String Title;     
        String Content;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("제목 : ");
        Title = scan.nextLine();
        System.out.println("내용 : ");
        Content = scan.nextLine();

        try {
          // VarietyID 구하기
            String sql = "insert into NOTICE_BOARD"
                    +"(NWNum,NWTitle,NWTime,NWContent)"
                    +"values"
                    +"(NW_SEQ.NEXTVAL, "
                    + Title 
                    + ", TO_CHAR(SYSDATE, 'YYYY.MM.DD HH24:MI'), ? )"; 
               
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Title);
            pstmt.setString(2, Content);
            int rowCount = pstmt.executeUpdate();
            //System.out.println(rowCount + "개의 행이 추가되었습니다.");
                
            pstmt.close();
            conn.close();
        }catch(SQLException ex2) {
           System.err.println("sql error = " + ex2.getMessage());
          System.exit(1);
        }
    }
	
	/*공지사항 글 읽기 함수*/
	public static void NBread(Connection conn, PreparedStatement pstmt)
    {
       try {
          System.out.println("검색할 제목을 입력하세요\n");
          String title = keyboard.nextLine();
    
          String sql = "select *"
                     + "from NOTICE_BOARD"
                     + "where NWTitle LIKE '%" +title+"%' ";

          //Class.forName("oracle.jdbc.OracleDriver");
    
          pstmt = conn.prepareStatement(sql);

    
          ResultSet rs = pstmt.executeQuery();

          while(rs.next()) {
                           int no = rs.getInt("NWNum");
                           String title1 = rs.getString("NWTitle");
                           String con = rs.getString("NWContent");

                           System.out.println(no+", "+title1+", "+con);
             }
       }catch(SQLException ex2) {
          System.err.println("sql error = " + ex2.getMessage());
          System.exit(1);
       }
    }
	
	/*공지사항 글 삭제 함수*/
	public static void NBdelete(Connection conn, PreparedStatement pstmt) 
	   {
	        int num;
	        
	        Scanner scan = new Scanner(System.in);
	        
	        System.out.println("삭제를 원하는 글번호 : ");
	        num = scan.nextInt();

	        try {
	           String sql ="delete from NOTICE_BOARD where NWNum = " + num;
	            //Class.forName("oracle.jdbc.OracleDriver");
	             
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1,num);
	            int rowCount = pstmt.executeUpdate();
	            System.out.println(rowCount + "개의 글이 삭제되었습니다.");
	        }catch(SQLException ex2) {
	          System.err.println("sql error = " + ex2.getMessage());
	          System.exit(1);
	       }
	    
	   }
}