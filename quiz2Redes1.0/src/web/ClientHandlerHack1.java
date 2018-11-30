package web;

import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;

public class ClientHandlerHack1 implements Runnable{
	
	private final Socket socket;

	public ClientHandlerHack1(Socket socket)
	{
		this.socket =  socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("\nClientHandler Started for " + this.socket);
		handleRequest(this.socket);
		System.out.println("ClientHanlder Terminated for "+  this.socket + "\n");
	}
	
	public void handleRequest(Socket socket)
	{
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String headerLine = in.readLine();
			// A tokenizer is a process that splits text into a series of tokens
			StringTokenizer tokenizer =  new StringTokenizer(headerLine);
			//The nextToken method will return the next available token
			String httpMethod = tokenizer.nextToken();
			// The next code sequence handles the GET method. A message is displayed on the
			// server side to indicate that a GET method is being processed
			String httpQueryString = tokenizer.nextToken();
			
		
			
			if(httpMethod.equals("GET"))
			{
				System.out.println("Get method processed");
				
				StringBuilder responseBuffer = leerArchivoWeb();
				
				
				sendResponse(socket, 200, responseBuffer.toString());				
			}
			else if(httpMethod.equals("POST")) {
				
			
				while(!headerLine.equals("")) {
					//System.out.println(headerLine);
					headerLine = in.readLine();
				}
				headerLine = in.readLine();
				String ide = headerLine.split("=")[1];
				
				ServerDataBase bd = new ServerDataBase();
				
				System.out.println(ide);
				if((bd.consultarID(ide))) {
					ArrayList<String> list =bd.listaInfo(ide);
					
					StringBuilder responseBuffer =  new StringBuilder();
					responseBuffer
					.append("<html>")
					.append("<head>")
					.append("<style>")
					.append("body{")
					.append("	background-image: url(\"http://www.centropsicologialopezdefez.es/blog/wp-content/uploads/2013/09/juegos-y-apuesta-online.png\");")
					.append("}")
					.append("</style>")
					.append("<title>Username Info</title>")
					.append("</head>")
					.append("<body>")
					.append("<h1>Listado de Carreras Realizadas</h1>")
					.append("<table>")
					.append("<tr>")
					.append("<td><strong>Username</strong></td>")
					.append("<td><strong>Bet $</strong></td>")
					.append("<td><strong>Date</strong></td>")
					.append("<td><strong>Horse</strong></td>")
					.append("<td><strong>Win? </strong></td>");
					addList(list,responseBuffer, ide.trim());
					responseBuffer.append("<body>")
					.append("<table>")
					.append("<body>")
					.append("</html>");
					
					sendResponse(socket, 200, responseBuffer.toString());
					
					
					
					
				}else {
					System.out.println("The HTTP method is not recognized");
					sendResponse(socket, 405, "NO se ha encontrado el usuario");
				}
			}
			
			else
			{
				System.out.println("The HTTP method is not recognized");
				sendResponse(socket, 405, "Method Not Allowed");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void addList(ArrayList<String> list, StringBuilder responseBuffer, String trim) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).split(",")[0].compareToIgnoreCase(trim)==0) {
				responseBuffer.append("<tr>");
				responseBuffer.append("<td>"+list.get(i).split(",")[0]+"</td>");
				responseBuffer.append("<td>"+list.get(i).split(",")[1]+"</td>");
				responseBuffer.append("<td>"+list.get(i).split(",")[2]+"</td>");
				responseBuffer.append("<td>"+list.get(i).split(",")[3]+"</td>");
				responseBuffer.append("<tr>");
			}
			
		}
	}

	public void sendResponse(Socket socket, int statusCode, String responseString)
	{
		String statusLine;
		String serverHeader = "Server: WebServer\r\n";
		String contentTypeHeader = "Content-Type: text/html\r\n";
		
		try {
			DataOutputStream out =  new DataOutputStream(socket.getOutputStream());
			if (statusCode == 200) 
			{
				statusLine = "HTTP/1.0 200 OK" + "\r\n";
				String contentLengthHeader = "Content-Length: "
				+ responseString.length() + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes(serverHeader);
				out.writeBytes(contentTypeHeader);
				out.writeBytes(contentLengthHeader);
				out.writeBytes("\r\n");
				out.writeBytes(responseString);
				} 
			else if (statusCode == 405) 
			{
				statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			} 
			else 
			{
				statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public StringBuilder leerArchivoWeb() {
		
		StringBuilder  response = new StringBuilder();
		try {
			FileReader fReader = new FileReader("./pagina/index.html");
			BufferedReader bReader = new BufferedReader(fReader);
			
			while(bReader.ready()) {
				response.append(bReader.readLine());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}