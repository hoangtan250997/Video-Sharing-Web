<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="col mt-4">
	<jsp:include page="/common/inform.jsp"></jsp:include>

	<ul class="nav nav-tabs" id="myTab" role="tablist">
		<li class="nav-item" role="presentation"><a
			class="nav-link active" id="videoEditing-tab" data-toggle="tab"
			href="#videoEditing" role="tab" aria-controls="videoEditing"
			aria-selected="true">Video Editing</a></li>
		<li class="nav-item" role="presentation"><a class="nav-link"
			id="videoList-tab" data-toggle="tab" href="#videoList" role="tab"
			aria-controls="videoList" aria-selected="false">Video List</a></li>

	</ul>
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="videoEditing"
			role="tabpanel" aria-labelledby="videoEditing-tab">
			<form action="" method="post" enctype="multipart/form-data">
				<div class="card">
					<div class="card-body">
						<div class="row">
							<div class="col-3">
								<div class="border p-3">
									<img
										src="${video.poster != null?video.poster:'/Poly.Asg2/pic/1.jpg' }"
										alt="" class="img-fluid">
									<div class="input-group mb-3 mt-3">
										<div class="input-group-prepend">
											<span class="input-group-text">Upload</span>
										</div>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="cover"
												name="cover" /> <label for="cover">Choose File</label>
										</div>
									</div>
								</div>
							</div>
							<div class="col-9">
								<div class="form-group">
									<label for="youtubeId">Youtube ID</label> <input type="text"
										class="form-control" name="videoId" id="youtubeId"
										value="${video.videoId }" aria-describedby="youtubeIdHid"
										placeholder="Youtube ID"> <small id="youtubeIdHid"
										class="form-text text-muted">Youtube ID is required</small>
								</div>
								<div class="form-group">
									<label for="title">Video Title</label> <input type="text"
										class="form-control" name="title" id="title"
										value="${video.title }" aria-describedby="title"
										placeholder="Video Title"> <small id="title"
										class="form-text text-muted">Video Title is required</small>
								</div>
								<div class="form-group">
									<label for="viewCount">View Count</label> <input type="text"
										class="form-control" name="views" id="viewCount"
										value="${video.views }" aria-describedby="viewCountHid"
										placeholder="View Count"> <small id="viewCountHid"
										class="form-text text-muted">View count is required</small>
								</div>
								<div class="form-check form-check-inline">
									<label> <input type="radio" class="form-check-input"
										value="true" name="active" id="status"
										${video.active?'checked':'' }>Active
									</label>
								</div>
								<div class="form-check form-check-inline">
									<label> <input type="radio" class="form-check-input"
										value="false" name="active" id="status"
										${!video.active?'checked':'' }>Inactive
									</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<label for="description">Description</label>
								<textarea name="description" id="description" cols="30" rows="4"
									class="form-control">${video.description }</textarea>
							</div>
						</div>
					</div>
					<div class="card-footer text-muted">
						<button class="btn btn-primary"
							formaction="Admin/VideosManageMent/create">Create</button>
						<button class="btn btn-warning"
							formaction="Admin/VideosManageMent/update">Update</button>
						<button class="btn btn-danger"
							formaction="Admin/VideosManageMent/delete">Delete</button>
						<button class="btn btn-info"
							formaction="Admin/VideosManageMent/reset">Reset</button>

					</div>
				</div>
			</form>
		</div>
		<div class="tab-pane fade" id="videoList" role="tabpanel"
			aria-labelledby="videoList-tab">
			<table class="table table-strip">
				<tr>
					<td>Youtube</td>
					<td>Video Title</td>
					<td>View Count</td>
					<td>Status</td>
					<td>&nbsp;</td>
				</tr>
				<c:forEach var="item" items="${videos }">
				<tr>
					<td>${item.videoId}</td>
					<td>${item.title}</td>
					<td>${item.views}</td>
					<td>${item.active? 'Active':'Deactive'}</td>
					<td><a href="Admin/VideosManageMent/edit?videoId=${item.videoId }"><i class="fa fa-edit" aria-hidden="true"></i>
							Edit</a>
						 <a href="Admin/VideosManageMent/delete?videoId=${item.videoId }"><i class="fa fa-trash" aria-hidden="true"></i>Delete</a></td>
				</tr>
				</c:forEach>
				
			</table>
		</div>
	</div>
</div>
