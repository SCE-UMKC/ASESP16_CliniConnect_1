

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;

/**
 * Servlet implementation class LoginAuthentication
 */
@WebServlet("/LoginAuth")
public class LoginAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Boolean isVerified = false;
    HttpServletRequest request2;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginAuth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject info = new JSONObject();
		request2 = request;
		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds023388.mlab.com:23388/clinic_db");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("clinic_db");
		
		FindIterable<Document> iterable = db.getCollection("test_patients").find(eq("username", request.getParameter("username")));		
		
		iterable.forEach(new Block<Document>() {
			
		    @Override
		    public void apply(final Document document) {
		        System.out.println(document);
		        try {
					isVerified(document);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		});
		mongoClient.close();
		try{
			if(isVerified){
				//response.getWriter().append("Served Success at: ").append(request.getContextPath());
				info.put("login", "success");
			}
			else{
				//response.getWriter().append("Served fail at: ").append(request.getContextPath());
				info.put("login", "failure");
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter();  
		out.print(info);
		out.flush();
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("this is the post");
		System.out.println(request.getParameter("username"));
		isVerified = false;
		request2 = request;
		doGet(request, response);
	}


	void isVerified(Document loginInfo) throws IOException{

		
		if(loginInfo.get("username").equals(request2.getParameter("username")) &&
				loginInfo.get("password").equals(request2.getParameter("password"))){
			System.out.println(loginInfo);
			System.out.println(request2.getParameter("username"));
			System.out.println(request2.getParameter("password"));
			isVerified = true;
//			String apiKey = "AIzaSyD5T5ouxL80ESZIRBfVmNiKob1Cwt3biIc";
//			int numOfRetries = 3;
//			Sender sender = new Sender(apiKey);
//			Message message = new Message.Builder()
//			    .addData("message", request2.getParameter("username"))
//			    .addData("other-parameter", "some value")
//			    .build();
//			Result result = sender.send(message, loginInfo.get("token").toString(), numOfRetries);
		}

		
	}
}
