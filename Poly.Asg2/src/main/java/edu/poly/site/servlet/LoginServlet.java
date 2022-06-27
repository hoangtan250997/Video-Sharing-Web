package edu.poly.site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.poly.common.CookieUtils;
import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.common.SessionUtils;
import edu.poly.dao.UserDao;
import edu.poly.domain.LoginForm;
import edu.poly.model.User;


@WebServlet("/Login") 	
public class LoginServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Dang o Login - Doget" );
		String username = CookieUtils.get("username", request);
		System.out.println("User  la " + username );
		if (username == null) {
			System.out.println("cho dang nghi ngo 1 ");
			PageInfo.prepareAndForwardSite(request, response, PageType.SITE_LOGIN_PAGE);
		System.out.println("cho dang nghi ngo 2 ");
		System.out.println("User  la " );
		return;
		}
				SessionUtils.add(request,"username", username);
				System.out.println("Van dang o Doget");
		
		request.getRequestDispatcher("/Homepage").forward(request, response);

		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Dang o Login - Dopost	");
		try {
	LoginForm form = new LoginForm();
	BeanUtils.populate(	form, request.getParameterMap());
	UserDao dao = new UserDao();
	User user = dao.findbyId(form.getUsername());
		
	if (user != null && user.getPassword().equals(form.getPassword())) {
		SessionUtils.add(request, "username", user.getUsername());
		System.out.println("login la " + user.getUsername());
		
		if (form.isRemember()) {
			CookieUtils.add("username", form.getUsername(), 24, response);
			
		}else {
			CookieUtils.add("username", form.getUsername(), 0, response);
		}
		request.setAttribute("isLogin", true);
//		request.setAttribute("name", user.getUsername());
		request.setAttribute("name", user.getFullname());
		request.getRequestDispatcher("/Homepage").forward(request, response);
		return;
	}
	request.setAttribute("error","invalid username or password");
	
	
} catch (Exception e) {
	
	request.setAttribute("error", e.getMessage());
}
PageInfo.prepareAndForwardSite(request, response, PageType.SITE_LOGIN_PAGE);
	}

}
