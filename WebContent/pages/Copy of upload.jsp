<%@ page import="java.io.*,java.util.*, javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.apache.commons.io.output.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload file</title>
</head>
<body>
<%
	File file;
	ServletContext context = pageContext.getServletContext();
	//get init parameter
	String filePath = context.getInitParameter("file-upload");
	int maxFileSize = Integer.parseInt(context.getInitParameter("max-file-size"));
	int maxMemSize = Integer.parseInt(context.getInitParameter("max-mem-size"));
	// Verify the content type
	String contentType = request.getContentType();
	if (contentType!=null && (contentType.indexOf("multipart/form-data") >= 0)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File("c:\\demo"));
	
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// maximum file size to be uploaded.
			upload.setSizeMax(maxFileSize);
			try {
				// Parse the request to get file items.
				List fileItems = upload.parseRequest(request);
				// Process the uploaded file items
				Iterator i = fileItems.iterator();
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (!fi.isFormField()) {
						// Get the uploaded file parameters
						String fieldName = fi.getFieldName();
						String fileName = fi.getName();
						String mimeType = getServletContext().getMimeType(fileName);
						if (mimeType.startsWith("image")) {
							String type = fi.getContentType();
							String realPath = getServletContext().getRealPath("/");
							System.out.println(realPath);
							String extension = fileName.substring(fileName.lastIndexOf("."));
							UUID uniqueName = UUID.randomUUID();
							String newFileName = realPath + filePath + uniqueName + extension;
							// Write the file
							file = new File(getServletContext().getRealPath("/")+ filePath+ "\\"+ uniqueName+ extension);
							fi.write(file);
						}else{
							out.println("day khong phai anh.");
						}
					}
				}
			} catch (Exception ex) {
				out.println("<p>Error</p>");
				ex.printStackTrace();
			}
	} else {
		out.println("<p>No file uploaded</p>");
	}
%>
</body>
</html>