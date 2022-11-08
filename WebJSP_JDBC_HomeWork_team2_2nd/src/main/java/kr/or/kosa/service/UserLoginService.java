package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.KoreaMemberDao;
import kr.or.kosa.dto.KoreaMember;

public class UserLoginService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;

		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		KoreaMember koreaMember = new KoreaMemberDao().findOne(id);

		forward = new ActionForward();
		forward.setRedirect(false);

		if (koreaMember == null) { // 없는 사용자 혹은 아이디 다른 경우
			forward.setPath("/WEB-INF/views/Ex02_JDBC_JoinForm.jsp");
		} else {
			if (pwd.equals(koreaMember.getPwd())) { // 로그인 성공 && 비번 동일
				request.getSession().setAttribute("userid", id);
				forward.setPath("Ex02_JDBC_Main.jsp");
			} else { // 비번 다름
				forward.setPath("/WEB-INF/views/Ex02_JDBC_Login.jsp");
			}
		}
		
		return forward;
	}

}
