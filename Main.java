import java.sql.*;
//import com.mysql.jdbc.Driver;
import java.util.Scanner;

public class Main {
    public static Connection con;

    public static void main(String args[]){
        String url = "jdbc:mysql://localhost:3306/learning";
        String user = "root";
        String pass = "Eric0510";
        Scanner in = new Scanner(System.in);

        //Class.forName("com.mysql.jdbc.Driver");
        int status = connect(url, user, pass);
        if(status == 1) {
            System.out.println("Database connected");
            byte options = -1;
            while (options != 0) {
                try {
                    System.out.println("0. exit\n1. Insert entry\n2. Delete entry\n3. View table");
                    options = in.nextByte();
                    switch (options) {
                        case 1:
                            System.out.println("input [key] [First name] [Last name] [DOB] in separate lines");
                            PreparedStatement ps = con.prepareStatement("insert into friends values (?, ?, ?, ?)");
                            ps.setInt(1, in.nextInt());
                            ps.setString(2, in.next());
                            ps.setString(3, in.next());
                            ps.setString(4, in.next());

                            ps.execute();
                            break;
                        case 2:
                            System.out.println("input [key]");
                            ps = con.prepareStatement("delete from friends where PersonID=?");
                            ps.setInt(1, in.nextInt());

                            ps.execute();
                            break;
                        case 3:
                            ResultSet rs = con.createStatement().executeQuery("select * from friends");
                            while (rs.next())
                                System.out.printf("%-1d  %-10s %-10s %-10s\n",
                                        rs.getInt(1), rs.getString(2),
                                        rs.getString(3), rs.getDate(4));
                            break;
                        case 0:
                            disconnect();
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                }catch(Exception e){ System.out.println(e);}
            }
        }

    }

    public static int connect(String url, String root, String password) {
        try {
            con = DriverManager.getConnection(url, root, password);
        } catch(SQLException e){
            return 0;
        }
        return 1;
    }

    public static int disconnect() {
        if(con != null) {
            try {
                con.close();
            } catch(SQLException e){
                return 0;
            }
        }
        return 1;
    }
}
