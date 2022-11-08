package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

public class UserLogoutService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		request.getSession().invalidate();
		forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/views/Ex02_JDBC_Login.jsp");
		return forward;
	}

}
