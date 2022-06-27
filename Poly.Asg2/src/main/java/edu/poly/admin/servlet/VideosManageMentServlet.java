package edu.poly.admin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.common.UploadUtils;
import edu.poly.dao.VideoDao;
import edu.poly.model.Video;

/**
 * Servlet implementation class VideosManageMentServlet
 */
@WebServlet({ "/Admin/VideosManageMent", "/Admin/VideosManageMent/create", "/Admin/VideosManageMent/update",
		"/Admin/VideosManageMent/delete", "/Admin/VideosManageMent/reset", "/Admin/VideosManageMent/edit" })
@MultipartConfig
public class VideosManageMentServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Dang o doGet Edit");
		String url = request.getRequestURL().toString();
		if (url.contains("edit")) {
			edit(request, response);
			return;
		}
		if (url.contains("delete")) {
			delete(request, response);
			return;
		}
		if (url.contains("reset")) {
			reset(request, response);
			return;
		}
		findAll(request, response);
		Video video = new Video();
		video.setPoster("pic/1.jpg");

		request.setAttribute("video", video);

		PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("videoId");

		if (id == null) {
			request.setAttribute("error", "Video is required!");
			PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
			return;
		}
		try {
			VideoDao dao = new VideoDao();
			Video video = dao.findbyId(id);
			if (id == null) {
				request.setAttribute("error", "Video is required!");
				PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
				return;
			}
			dao.delete(id);
			request.setAttribute("message", "Video is deleted!");
			request.setAttribute("video", new Video());
			findAll(request,response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: " + e.getMessage());
		}
		PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}
		
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Dang o doPoPosst create");
		String url = request.getRequestURL().toString();

		if (url.contains("create")) {
			System.out.println("Dang o IF doPoPosst create");
			create(request, response);
			return;
		}
		if (url.contains("delete")) {
			delete(request, response);
			return;
		} 
		if (url.contains("update")) {
			update(request, response);
			return;
		}
		if (url.contains("reset")) {
			reset(request, response);
			return;
		}
		System.out.println("Dung huong 2");
		PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);

	}

private void reset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Video video = new Video();
	video.setPoster("pic/1.jpg");
	request.setAttribute("video", video);
	findAll(request, response);
	PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
}
		
	

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Video video = new Video();
		System.out.println("Dung huong 3");
		try {
			BeanUtils.populate(video, request.getParameterMap());
			VideoDao dao = new VideoDao();
			Video oldVideo = dao.findbyId(video.getVideoId());
			if (request.getPart("cover").getSize() == 0) {
				video.setPoster(
						"uploads/" + UploadUtils.processUploadFiled("cover", request, "/uploads", video.getVideoId()));

			}
			dao.update(video);
			request.setAttribute("message", "Video is updated");
			findAll(request, response);

			request.setAttribute("video", video);
			System.out.println("Dung huong 4");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: " + e.getMessage());
			System.out.println("Dung huong 5");

		}
		PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		try {
			VideoDao dao = new VideoDao();
			List<Video> list = dao.findAll();
			request.setAttribute("videos", list);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: " + e.getMessage());
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("videoId");

		if (id == null) {
			request.setAttribute("error", "Video is required!");
			PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
			return;
		}
		try {
			VideoDao dao = new VideoDao();
			Video video = dao.findbyId(id);
			request.setAttribute("video", video);
			findAll(request,response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: " + e.getMessage());
		}
		PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Video video = new Video();
		System.out.println("Dung huong 1");
		try {
			BeanUtils.populate(video, request.getParameterMap());
			video.setPoster(
					"upload/" + UploadUtils.processUploadFiled("cover", request, "/uploads", video.getVideoId()));
			System.out.println("1");
			VideoDao dao = new VideoDao();
			System.out.println("2");
			dao.insert(video);
			System.out.println("3");
			request.setAttribute("video", video);
			request.setAttribute("message", "Video is insert");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error :" + e.getMessage());
		}
		findAll(request,response);

		PageInfo.prepareAndForward(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}
}
