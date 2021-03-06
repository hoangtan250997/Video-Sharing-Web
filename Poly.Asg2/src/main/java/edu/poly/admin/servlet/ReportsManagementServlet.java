package edu.poly.admin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.dao.FavoriteDao;
import edu.poly.dao.VideoDao;
import edu.poly.domain.FavoriteReport;
import edu.poly.domain.FavoriteUserReport;
import edu.poly.model.Video;

@WebServlet("/ReportsManagementServlet")
public class ReportsManagementServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Dang o do Get");
		reportFavoritesByVideos(request, response);
		reportFavoriteUsersByVideos(request, response);
		PageInfo.prepareAndForward(request, response, PageType.REPORT_MANAGEMENT_PAGE);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Dang o do Post");

	}

	protected void reportFavoriteUsersByVideos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String videoUserId = request.getParameter("videoUserId");
			VideoDao vdao = new VideoDao();
			List<Video> vList = vdao.findAll();
			if (videoUserId == null && vList.size() > 0) {
				videoUserId = vList.get(0).getVideoId();

			}
			FavoriteDao dao = new FavoriteDao();
			List<FavoriteUserReport> list = dao.reportFavoriteUserbyVideo(videoUserId);
			System.out.println("la la al "+list.size());
			request.setAttribute("videoUserId", videoUserId);
			request.setAttribute("vidList", vList);
			request.setAttribute("favUsers", list);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: " + e.getMessage());
			System.out.print(e.getMessage());
		}
	}

	protected void reportFavoritesByVideos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("Dang o do reportFavoritesByVideos");
			FavoriteDao dao = new FavoriteDao();
			System.out.println("re1");
			List<FavoriteReport> list = dao.reportFavoritesByVideos();
			System.out.println("re2");
			request.setAttribute("favList", list);
			System.out.println("re3");
			System.out.print(list.size());

		} catch (Exception e) {
			request.setAttribute("error", "Error: " + e.getMessage());
			System.out.print(e.getMessage());
		}
	}

}
