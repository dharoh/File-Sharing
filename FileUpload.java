import java.io.*;
import java.util.*;
 
import javax.servlet.*;
import javax.servlet.http.*;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

public class FileUpload extends HttpServlet{
	
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 3 * 1024 * 1024 * 1024;
   private int maxMemSize =  3 * 1024 * 1024 * 1024;
   private File file ;
   String url;

   public void init( ){
      // Get the file location where it would be stored.

   }
   
   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {
   
      // Check that we have a file upload request
      url=request.getRequestURL().toString();
      if(url.contains("localhost"))
      {
      	filePath = getServletContext().getInitParameter("temp"); 
      }
      else
      {
      	filePath = getServletContext().getInitParameter("file-upload"); 
      }
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
   out.println(url);
      if( !isMultipart ) {
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
         out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
      }
  
      DiskFileItemFactory factory = new DiskFileItemFactory();
   
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
   
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
   
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );

      try { 
         // Parse the request to get file items.
         List fileItems = upload.parseRequest(request);
	
         // Process the uploaded file items
         Iterator i = fileItems.iterator();

         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
   
  
         File f=new File("G:\\File_Transfer");
         if (!f.exists()) f.mkdirs();

         while ( i.hasNext () ) {
			
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () ) {
               // Get the uploaded file parameters
               String fieldName = fi.getFieldName();
               String fileName = fi.getName();
               String contentType = fi.getContentType();
               boolean isInMemory = fi.isInMemory();
               long sizeInBytes = fi.getSize();
            
               // Write the file
               if( fileName.lastIndexOf("\\") >= 0 ) {
                  file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
               } else {
				   
                  file = new File(filePath+fileName) ;
               }
               fi.write( file ) ;
			 
               out.println("Uploaded Filename: " + fileName + "<br>");
            }
			
         }
         
         } catch(Exception ex) {
            out.println(ex.toString());
         }
		 
		 out.println("</body>");
         out.println("</html>");
      }
      
      public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, java.io.IOException {

         throw new ServletException("GET method used with " +
            getClass( ).getName( )+": POST method required.");
      }
}