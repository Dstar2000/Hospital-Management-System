package hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection c;
	private Scanner s;
	public Patient(Connection c, Scanner s)
	{
		this.c=c;
		this.s=s;
	}
	
	public void AddPatient()
	{
		System.out.print("Please enter patient name: ");
		String name=s.next();
		System.out.print("Please enter patient age: ");
		int age=s.nextInt();
		System.out.print("Please enter patient gender: ");
		String gender=s.next();
		
		try {
			String query="INSERT INTO patients(name,age,gender) values(?,?,?)";
			PreparedStatement p=c.prepareStatement(query);
			p.setString(1, name);
			p.setInt(2, age);
			p.setString(3, gender);
			int affectedRows=p.executeUpdate();
			if(affectedRows>0)
			{
				System.out.println("Patient added successfully....");
			}
			else {
				System.out.println("failed to add patient...!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ViewPatients()
	{
		String query="SELECT * FROM patients";
		try {
			PreparedStatement p=c.prepareStatement(query);
			ResultSet resultSet=p.executeQuery();
			System.out.println("Patients Details: ");
			System.out.println("+------------+---------------------+--------------+----------------+");
			System.out.println("| Patient Id |Name                 |Age           |Gender          |");
			System.out.println("+------------+---------------------+--------------+----------------+");
			
			while(resultSet.next())
			{
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				int age=resultSet.getInt("age");
				String gender=resultSet.getString("gender");
				System.out.printf("|%12s|%21s|%14s|%16s|\n",id,name,age,gender);
				System.out.println("+------------+---------------------+--------------+----------------+");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id) {
		String query="SELECT * FROM patients WHERE id=?";
		try {
			PreparedStatement p=c.prepareStatement(query);
			p.setInt(1, id);
			ResultSet resultSet=p.executeQuery();
			if(resultSet.next())
			{
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}



































































































