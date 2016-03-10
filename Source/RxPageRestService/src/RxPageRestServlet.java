

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;
//import com.ibm.json.java.JSON;
//import com.ibm.json.java.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;;

/**
 * Servlet implementation class RxPageRestServlet
 */
@WebServlet(description = "Will manage the rest calls made when submitting the request on the Clinical Test Rx Page", urlPatterns = { "/RxPageRestServlet" })
public class RxPageRestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RxPageRestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("I am here in doPost!!!!!");
		
		
		String testName=request.getParameter("testName");
		String specialInstructions = request.getParameter("special_instructions");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateTimeStamp = dateFormat.format(date); //2014/08/06 15:59:48
		BasicDBObject clinicalTestRow = new BasicDBObject();
		
		//HardCoded for now.
		String patientName = "Shweta Parihar";
		String patientID = "1234567890.00";
		String physicianName = "Dr. Anil Jain";
		String physicianClinicName = "Dr Lal Multi Speciality Clinic";
			
		System.out.println("1. patientID : "+patientID);
		System.out.println("2. patientName : "+patientName);
		System.out.println("3. physicianName : "+physicianName);
		System.out.println("4. physicianClinicName : "+physicianClinicName);
		System.out.println("5. testName : "+testName);
		System.out.println("6. specialInstructions : "+specialInstructions);
		System.out.println("7. dateTimeStamp : "+dateTimeStamp);		
		
		clinicalTestRow.put("patientID",patientID);
		clinicalTestRow.put("patientName",patientName);
		clinicalTestRow.put("physicianName",physicianName);
		clinicalTestRow.put("physicianClinicName",physicianClinicName);
		clinicalTestRow.put("testName",testName);
		clinicalTestRow.put("specialInstructions",specialInstructions);
		clinicalTestRow.put("dateTimeStamp",dateTimeStamp);		
		
		MongoClientURI uri = new MongoClientURI("mongodb://shweta:shweta@ds023388.mlab.com:23388/clinic_db");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("clinical_test_table");
		WriteResult result = users.insert(clinicalTestRow);
		

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		response.getWriter().write(result.toString());
		
		
		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
