<%@ page import="java.io.*,java.util.*,java.util.zip.*,javax.servlet.*,javax.servlet.http.*" %>
<%!
 
void addFile( ZipOutputStream outZip, File f, String name )
{
FileInputStream in = null ;
try
{
// Add ZIP entry to output stream.
outZip.putNextEntry( new ZipEntry( name ) ) ;
 
in = new FileInputStream( f ) ;
 
// Transfer bytes from the file to the ZIP file
byte[] buf = new byte[ in.available() ] ;
int len ;
len = in.read(buf);
outZip.write(buf) ;
}
catch( IOException ex ) { ex.printStackTrace(); }
finally
{
// Complete the entry
try{ outZip.closeEntry() ; } catch( IOException ex ) { }
try{ in.close() ; } catch( IOException ex ) { }
}
}
%>
<%

out.clear();
out.clearBuffer();
 
try
{
         
// set the content type and the filename
response.setContentType( "application/zip" ) ;
response.addHeader( "Content-Disposition", "attachment; filename=myzipfile.zip" ) ;
 
// get a ZipOutputStream, so we can zip our files together
ZipOutputStream outZip = new ZipOutputStream( response.getOutputStream() );

File folder = new File("G:\\File_Transfer");


for (File fileEntry : folder.listFiles()) {
	
	if(!fileEntry.isDirectory()){
		// add some files to the zip...
		addFile( outZip, fileEntry, fileEntry.getName() ) ;
	}
}

for (File f : folder.listFiles()) {
	
	f.delete();
}

folder.delete();
 
// flush the stream, and close it
outZip.flush() ;
outZip.close() ;


return;
}
catch(Exception e)
{
e.printStackTrace();
}
%>