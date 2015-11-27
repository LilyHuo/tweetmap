package server;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;


/*import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;*/
/**
 * Servlet implementation class back
 */
@WebServlet("/back")
public class back extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int count;
	private static Connection dbconnect = null;
	
    public back() {}

	public void init(ServletConfig config) throws ServletException {
		try {
			dbconnect =  DBcon.createDBConnection();
	        System.out.println("Connect with the database for reading");
		} catch (Exception e) {e.printStackTrace();}
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
		 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//response.setHeader("Cache-Control", "no-cache");
		
		 
		String keyword = request.getParameter("tag");
		System.out.println(keyword);
		ArrayList<String> data = null;
		
		if(keyword.equals("all"))
		{
			try {
				data = DBread.readDB_all(dbconnect);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			data = DBread.readDB(dbconnect,keyword);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String send_data = data.get(count);
        //count ++;

		 PrintWriter out=response.getWriter();
		 out.write(send_data);
		
		 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	  public static void main(String[] args)  {

	    	try {
				TweetCollect.tweetget();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }


}
