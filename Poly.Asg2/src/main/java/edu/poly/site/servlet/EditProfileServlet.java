package edu.poly.site.servlet;

import java.io.IOException;
import java.net.UnknownServiceException;

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
import edu.poly.model.User;

/**
 * Servlet implementation class EditProfileServlet
 */
@WebServlet("/EditProfile")
public class EditProfileServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String username = SessionUtils.getLoginedUsername(request);
	System.out.println("Dang o Edit deGet");
	System.out.println("Edit la "+ username);
	if (username == null) {
		request.getRequestDispatcher("/Login").forward(request, response);
		return;
		
	}
	
	try {
		System.out.println("Usename la "+ username);
		UserDao dao = new UserDao();
		User user = dao.findbyId(username);
		request.setAttribute("user", user);
	} catch (Exception e) {
		e.printStackTrace();
//		request.setAttribute("error", e.getMessage());
		request.setAttribute("error", "cho nay edit 1");
	
	}
	PageInfo.prepareAndForwardSite(request, response, PageType.SITE_EDIT_PROFILE_PAGE);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Dang o Edit doPost");
	
		try {
			
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			// no bi null
			String username = SessionUtils.getLoginedUsername(request);
			System.out.print(username);
			UserDao dao = new UserDao();
			User oldUser = dao.findbyId(username);
	
			user.setAdmin(oldUser.getAdmin());
			dao.update(user);
			request.setAttribute("message", "User profile updated!!!");
			request.setAttribute("user", user);

		} catch (Exception e) {
			e.printStackTrace();
//			request.setAttribute("error", e.getMessage());
			request.setAttribute("error", "cho nay edit 2");
		}
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_EDIT_PROFILE_PAGE);

	}

}
