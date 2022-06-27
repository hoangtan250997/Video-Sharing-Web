package edu.poly.admin.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.common.SessionUtils;
import edu.poly.dao.UserDao;
import edu.poly.domain.ChangePasswordForm;

/**
 * Servlet implementation class ChangePassswordServlet
 */
@WebServlet("/ChangePassword")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = SessionUtils.getLoginedUsername(request);
		System.out.println("Dang o do Get ChangePW");

		if (username == null) {
			request.getRequestDispatcher("/Login").forward(request, response);
//			PageInfo.prepareAndForwardSite(request, response, PageType.SITE_LOGIN_PAGE);

//			PageInfo.prepareAndForwardSite(request, response, PageType.SITE_LOGIN_PAGE);
//			return;		
			} 
		System.out.println("Dang o do Get ChangePW 1");
		request.setAttribute("username", username);
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_CHANGE_PASSWPORD_PAGE);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("Dang o do Post ChangePW");
			String username  = SessionUtils.getLoginedUsername(request);
//			System.out.println( username.toString());
			ChangePasswordForm form = new ChangePasswordForm();
			BeanUtils.populate(form, request.getParameterMap());
			request.setAttribute("username", username);
//			System.out.println("Dang o do Post ChangePW " + username.toString());

		if (!form.getConfirmPassword().equals(form.getPassword())) {
			request.setAttribute("error","new password and new confirm password are not identical");
	
		}else {
			UserDao dao = new UserDao();
			dao.changePassword(form.getUsername(), form.getCurrentPassword(), form.getPassword());
			request.setAttribute("message", "Password has been changed");
		}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error",e.getMessage());
		}
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_CHANGE_PASSWPORD_PAGE);

	}

}
