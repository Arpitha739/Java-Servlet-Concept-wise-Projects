package com.dcl.ems;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/save")

public class AddEmployeeServlet extends GenericServlet
{

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException 
	{
		int employeeId = Integer.parseInt(req.getParameter("id"));
		String employeeName =req.getParameter("name");
		String employeeEmail =req.getParameter("email");
		String employeeDesc =req.getParameter("desc");
		int employeeSalary = Integer.parseInt(req.getParameter("salary"));
		
		/* JDBC logic */
		
		Connection conn = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/servelt_ems?user=root&password=root");
			
			PreparedStatement pst = conn.prepareStatement("INSERT INTO employee VALUES(?,?,?,?,?)");
			
			pst.setInt(1, employeeId);
			pst.setString(2, employeeName);
			pst.setString(3, employeeEmail);
			pst.setString(4, employeeDesc);
			pst.setInt(5, employeeSalary);
			
			int rowInserted = pst.executeUpdate();
			
			PrintWriter pw = res.getWriter();
			
			if(rowInserted > 0)
				pw.print("<h1>" + rowInserted + " data inserted </h1>");
			else
				pw.print("<h1> Data not inserted </h1>");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		
	}

}
