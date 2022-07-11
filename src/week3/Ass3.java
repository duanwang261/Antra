package week3;

import javax.swing.plaf.nimbus.State;
import java.sql.*;


/**
 * homework : deadline => next Monday 10am CDT
 * 1. download database
 * 2. create teacher(salary) m - m student
 * 3. use jdbc to query database
 *    select all teacher info (include student info)
 *    count student of each teacher
 *    get teacher which has 2nd highest salary(rank, dense_rank)
 *
 * Hibernate + JPA + Maven(pom.xml)
 * 1. ORM
 * 2. first level cache / second level cache
 * 3. connection pool
 * 4. Jpql / Hql
 * 5. data source
 * 6. lazying vs eager loading
 * 7. annotations
 *
 * Spring IOC + AOP
 * Spring boot
 * Network basic + Http
 * Restful api
 * security(Https, Jwt..)
 */
public class Ass3 {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "Duan2020mysql");

            Statement statement = connection.createStatement();

            //students & teachers table
            String sql1 = "SELECT * from Students";
            String sql2 = "SELECT * from Teachers";

            //students count
            String sql3 = "SELECT * from Students where Students.TeacherID = 1";
            String sql4 = "Select * from Teachers where Teachers.StudentsInClass > 0";

            //2nd high salary
            String sql5 = "SELECT max(Salary) from Teachers where Teachers.Salary < (SELECT max(Salary) from Teachers)";

            ResultSet rs = statement.executeQuery(sql1);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("ID_Students");
                int age = rs.getInt("Age");
                String name = rs.getString("Name");
                int teacherId = rs.getInt("TeacherID");

                //Display values
                System.out.print("studentID: " + id);
                System.out.print(", studentName: " + name);
                System.out.print(", studentAge: " + age);
                System.out.println(", TeacherID: " + teacherId);
            }
            conn.commit();
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            with . printStackTrace ();
        } catch ( Exception  e ){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
//                with . printStackTrace ();
            }//end finally try
        }//end try
    }//end main

}
