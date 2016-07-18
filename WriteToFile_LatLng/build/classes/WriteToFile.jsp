<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%=request.getParameter("txt_nelat") %>--
<%=request.getParameter("txt_nelng") %><br/>
<%=request.getParameter("txt_swlat") %>--
<%=request.getParameter("txt_swlng") %><br/>



<!-- Writing to file : START -->
<%@page import="java.io.*"%>
<%
 //File creation
 String strPath = "C:\\Lucene\\Input\\input.txt";
 File strFile = new File(strPath);
 boolean fileCreated = strFile.createNewFile();
 //File appending
 Writer objWriter = new BufferedWriter(new FileWriter(strFile));
 objWriter.write(request.getParameter("txt_nelat")+"-"+ request.getParameter("txt_nelng")+"-"+ request.getParameter("txt_swlat")+"-"+ request.getParameter("txt_swlng"));
 objWriter.flush();
 objWriter.close();
 
 
%>

  <%= "Degerler "+strPath+ "adresine yazildi" %> 
<!-- Writing to file : END -->
</body>
</html>