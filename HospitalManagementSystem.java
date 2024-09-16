package hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static String url="jdbc:mysql://localhost:3306/hospitalmanagement";
	private static String username="root";
	private static String password="pass123";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner s=new Scanner(System.in);
		try {
			Connection c=DriverManager.getConnection(url,username,password); 
			Patient p=new Patient(c, s);
			Doctors d=new Doctors(c);
			
			while(true)
			{
				System.out.println("**********HOSPITAL MANAGEMENT SYSTEM**********");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patient");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.print("Please enter your choice: ");
				int choice=s.nextInt();
				switch(choice) {
				case 1:p.AddPatient();
				break;
				case 2:p.ViewPatients();
				break;
				case 3:d.ViewDoctors();
				break;
				case 4:BookAppointment(p, d, c, s);
				System.out.println();
				break;
				case 5:System.out.println("Exiting.....!");;
				return;
				default:p.AddPatient();
				break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		s.close();
	}
	
	public static void BookAppointment(Patient p,Doctors d,Connection c, Scanner s) {
	System.out.print("Please enter patient Id: ");	
	int patientid=s.nextInt();
	System.out.print("Please enter doctor Id: ");	
	int doctorid=s.nextInt(); 
	System.out.print("Please enter appointment date (YYYY-MM-DD): ");
	String appointmentDate=s.next();
	if(p.getPatientById(patientid) && d.getDoctorById(doctorid))
	{
		if(checkDoctorAvailability(doctorid, appointmentDate,c)) {
			String appointmentQuery="INSERT INTO appointments(patient_id, doctor_id, appointment_date) values(?,?,?)";
			try {
				PreparedStatement preparedStatement=c.prepareStatement(appointmentQuery);
				preparedStatement.setInt(1, patientid);
				preparedStatement.setInt(2, doctorid);
				preparedStatement.setString(3, appointmentDate);
				int rowsAffected=preparedStatement.executeUpdate();
				if(rowsAffected>0)
				{
					System.out.println("Appointment booked...");
				}
				else {
					System.out.println("Failed to book appointment");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Doctor not available on this date");
		}
	}
	else {
		System.out.println("Either doctor or patient doesn't exist..!");
	}
	}
	
	public static boolean checkDoctorAvailability(int doctorid,String appointmentDate,Connection c) {
		String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		try {
			PreparedStatement p=c.prepareStatement(query);
			p.setInt(1, doctorid);
			p.setString(2, appointmentDate);
			ResultSet resultSet=p.executeQuery();
			if(resultSet.next()) {
				int count =resultSet.getInt(1);
				if(count==0)
				{
					return true;
				}
				else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}































