

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class SetPostVisitSurvey
 */
@WebServlet("/SetPostVisitSurvey")
public class SetPostVisitSurvey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPostVisitSurvey() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds023388.mlab.com:23388/clinic_db");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("clinic_db");
		
		db.getCollection("follow_up_survey").insertOne(new Document()
				.append("username", request.getParameter("username"))
				.append("timestamp", request.getParameter("timestamp"))
				.append("q1", request.getParameter("q1"))
				.append("q2", request.getParameter("q2"))
				.append("q3", request.getParameter("q3"))
				.append("q4", request.getParameter("q4"))
				.append("q5", request.getParameter("q5"))
				.append("q6", request.getParameter("q6"))
				.append("q7", request.getParameter("q7"))
				.append("q8", request.getParameter("q8")));
		
		JSONObject info = new JSONObject();
		try {
			info.put("success", "success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mongoClient.close();
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
		doGet(request, response);
	}

}
