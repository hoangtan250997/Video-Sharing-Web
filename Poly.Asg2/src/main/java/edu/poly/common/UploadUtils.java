package edu.poly.common;

import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UploadUtils {
	public static String processUploadFiled(String fieldName, HttpServletRequest request, String storedFolder, String storedFilename) throws IOException, ServletException {
		Part filePart = request.getPart(fieldName);
		
		if (filePart == null || filePart.getSize() == 0) {
			return "";
			
		}
		if (storedFolder == null) {
			storedFolder = "/uploads";
		}
		if (storedFilename == null) {
			storedFilename = Path.of(filePart.getSubmittedFileName()).getFileName().toString();
			
		}else {
			storedFilename += "." + FilenameUtils.getExtension(java.nio.file.Path.of(filePart.getSubmittedFileName()).toString()); 
		}
		String uploadFolder = request.getServletContext().getRealPath(storedFolder);
		
		Path uploadPath = Paths.get(uploadFolder); 
		if (!Files.exists(uploadPath)) {
			Files.createDirectory(uploadPath);
			
		}
		filePart.write(Paths.get(uploadFolder.toString(),storedFilename).toString());
		
		return storedFilename;
	}

}
