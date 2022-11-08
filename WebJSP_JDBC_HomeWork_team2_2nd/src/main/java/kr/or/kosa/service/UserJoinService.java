package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.KoreaMemberDao;
import kr.or.kosa.dto.KoreaMember;

public class UserJoinService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;

		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("mname");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String ip = request.getRemoteAddr().toString();
		KoreaMember koreaMember = new KoreaMember(id, pwd, name, age, gender, email, ip);
		int result = new KoreaMemberDao().saveOne(koreaMember);

		forward = new ActionForward();
		forward.setRedirect(false);

		if (result > 0) {
			forward.setPath("/WEB-INF/views/Ex02_JDBC_Login.jsp");
		} else {
			forward.setPath("/WEB-INF/views/Ex02_JDBC_JoinForm.jsp");
		}

		return forward;
	}

}
