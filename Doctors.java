package hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
	private Connection c;

	public Doctors(Connection c) {
		this.c = c;
	}

	public void ViewDoctors() {
		String query = "SELECT * FROM doctors";
		try {
			PreparedStatement p = c.prepareStatement(query);
			ResultSet resultSet = p.executeQuery();
			System.out.println("Doctor Details: ");
			System.out.println("+------------+---------------------+--------------------+");
			System.out.println("| doctor ID  |Doctor Name          |Specialization      |");
			System.out.println("+------------+---------------------+--------------------+");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String specialization = resultSet.getString("specialization");
				System.out.printf("|%12s|%21s|%20s|\n", id, name, specialization);
				System.out.println("+------------+---------------------+--------------------+");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM doctors WHERE id=?";
		try {
			PreparedStatement p = c.prepareStatement(query);
			p.setInt(1, id);
			ResultSet resultSet = p.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
