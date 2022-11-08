package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dao.KoreaMemberDao;
import kr.or.kosa.dto.KoreaMember;

public class UserUpdateService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		
		String session = (String) request.getSession().getAttribute("userid");
		if (session == null || !session.equals("admin")) {
			forward.setPath("/WEB-INF/views/Ex02_JDBC_Login.jsp");
		} else {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			int age = Integer.parseInt(request.getParameter("age"));
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			KoreaMember km = new KoreaMember();
			km.setId(id);
			km.setName(name);
			km.setAge(age);
			km.setEmail(email);
			km.setGender(gender);
			int result = new KoreaMemberDao().updateOne(km);
			forward.setRedirect(true); // true
			forward.setPath(request.getContextPath() + "/managepage.do");
		}
		
		return forward;
	}
}
