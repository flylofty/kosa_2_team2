package kr.or.kosa.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.KoreaMemberDao;
import kr.or.kosa.dto.KoreaMember;
import kr.or.kosa.service.UserDatailService;
import kr.or.kosa.service.UserJoinService;
import kr.or.kosa.service.UserLoginService;
import kr.or.kosa.service.UserLogoutService;
import kr.or.kosa.service.UserSearchService;
import kr.or.kosa.service.UserUpdateService;

@WebServlet({ "/KoreaMemberController", "*.do" })
public class KoreaMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public KoreaMemberController() {}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String conntextPath = request.getContextPath();
		String urlcommand = requestURI.substring(conntextPath.length());

		//request.setCharacterEncoding("UTF-8");

		Action action = null;
		ActionForward forward = null;

		if (urlcommand.equals("/managepage.do")) { // 관리 페이지 가기
			forward = new ActionForward();
			String session = (String) request.getSession().getAttribute("userid");
			if (session == null || !session.equals("admin")) {
				//UI 제공
	    		forward.setRedirect(false);
	    		//forward.setPath("/WEB-INF/views/memoview.jsp");
	    		forward.setPath("/WEB-INF/views/Ex02_JDBC_Login.jsp");
			} else { // 회원 리스트 페이지
				List<KoreaMember> list = new KoreaMemberDao().findAll();
				request.setAttribute("list", list);
	    		forward.setRedirect(false);
	    		forward.setPath("WEB-INF/views/Ex03_Memberlist.jsp");
			}
		} else if (urlcommand.equals("/mainpage.do")) { // 메인페이지 가기
			forward = new ActionForward();
    		forward.setRedirect(false);
    		forward.setPath("Ex02_JDBC_Main.jsp");
		} else if (urlcommand.equals("/loginpage.do")) { // 로그인 페이지 가기
			forward = new ActionForward();
			String session = (String) request.getSession().getAttribute("userid");
			if (session == null) { // 로그인 페이지 가기!
	    		forward.setRedirect(false);
	    		forward.setPath("WEB-INF/views/Ex02_JDBC_Login.jsp");
			} else { // 메인 페이지로 가기
				// viewPage = "Ex02_JDBC_Main.jsp";
	    		forward.setRedirect(true);
	    		forward.setPath(request.getContextPath() + "/mainpage.do");
			}
		} else if (urlcommand.equals("/joinformpage.do")) { // 가입 페이지 가기
			forward = new ActionForward();
			String session = (String) request.getSession().getAttribute("userid");
			if (session == null) { // 가입 페이지로 가기
	    		forward.setRedirect(false);
	    		forward.setPath("WEB-INF/views/Ex02_JDBC_JoinForm.jsp");
			} else { // 메인 페이지로 가기
				// viewPage = "Ex02_JDBC_Main.jsp";
	    		forward.setRedirect(true);
	    		forward.setPath(request.getContextPath() + "/mainpage.do");
			}
		} else if (urlcommand.equals("/joinok.do")) { // 회원가입 요청
			//UI + 로직
    		action = new UserJoinService();
    		forward = action.execute(request, response);
		} else if (urlcommand.equals("/loginok.do")) { // 로그인 요청
			action = new UserLoginService();
			forward = action.execute(request, response);
		} else if (urlcommand.equals("/logout.do")) { // 로그아웃 요청
			action = new UserLogoutService();
			forward = action.execute(request, response);
		} else if (urlcommand.equals("/memberDetail.do")) { // 멤버 상세 페이지 요청
			//UI + 로직
    		action = new UserDatailService();
    		forward = action.execute(request, response);
		} else if (urlcommand.equals("/memberDelete.do")) { // 회원 삭제
			String id = request.getParameter("id");
			int result = new KoreaMemberDao().deleteOne(id);
			// viewPage = "Ex03_Memberlist.jsp";
			forward = new ActionForward();
    		forward.setRedirect(true);
    		forward.setPath(request.getContextPath() + "/managepage.do");
		} else if (urlcommand.equals("/memberEditPage.do")) { // 수정 페이지 요청
			String session = (String) request.getSession().getAttribute("userid");
			forward = new ActionForward();
			forward.setRedirect(false);
			if (session == null || !session.equals("admin")) {
				forward.setPath("/WEB-INF/views/Ex02_JDBC_Login.jsp");
			} else {
				String id = request.getParameter("id");
				KoreaMember km = new KoreaMemberDao().findOne(id);
				request.setAttribute("member", km);
				forward.setPath("/WEB-INF/views/Ex03_MemberEdit.jsp");
			}
		} else if (urlcommand.equals("/memberEditok.do")) { // 회원 수정
			//UI + 로직
    		action = new UserUpdateService();
    		forward = action.execute(request, response);
		} else if (urlcommand.equals("/memberSearch.do")) { // 회원검색
			//UI + 로직
    		action = new UserSearchService();
    		forward = action.execute(request, response);
		}
		
		if(forward != null) {
    		if(forward.isRedirect()) { //true 페이지 재 요청 (location.href="페이지"
    			response.sendRedirect(forward.getPath());
    		}else { //기본적으로 forward ....
    			    //1. UI 전달된 경우
    			    //2. UI + 로직
    			RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
    			dis.forward(request, response);
    		}
    	}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}