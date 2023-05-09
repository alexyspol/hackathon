package com.dynamis.options;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import com.dynamis.App;
import com.dynamis.SQLFile;

public class DisplayUsersOption implements Option {
    private SQLFile sql;

    @Override
    public void run(App app) {

        sql = new SQLFile("display_users.sql");

        // Step 1: Fetch information from database

        try(Connection c = DriverManager.getConnection("jdbc:sqlite:hackathon.db");
            PreparedStatement s = c.prepareStatement(sql.nextStatement());
            ResultSet rs = s.executeQuery()) {

            int i = 1;

            // Step 2: Print it out
            
            System.out.println();

            while(rs.next()) {
                String studentId = rs.getString("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String dob = rs.getString("dob");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String residence = rs.getString("residence");
                String skill = rs.getString("skill");
                String teamName = rs.getString("team_name");

                System.out.printf("""
                        > %d. %s %s
                            Student ID: %s
                            Date of Birth: %s
                            Age: %d
                            Team: %s
                            Phone: %s
                            E-mail: %s
                            Residence: %s
                            Skill: %s

                        """, i, firstName, lastName, studentId, dob, calculateAge(dob), teamName, phone, email, residence, skill);

                i++;
            }
        }
        catch(SQLException e) {

        }

    }

    private int calculateAge(String dob) {
        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public String toString() {
        return "Display list of users";
    }

}
