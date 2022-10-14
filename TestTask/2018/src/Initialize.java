import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.Statement;

public class Initialize {

    public Initialize(Properties props, String path, String databaseURL)
    {
        try {
            String fullPath = new java.io.File( "." ).getCanonicalPath() + path;
            String driverName = "org.firebirdsql.jdbc.FBDriver";

            Class.forName("org.firebirdsql.jdbc.FBDriver");
            Connection con = DriverManager.getConnection(
                    databaseURL,
                    props
            );

            try
            {
                con.createStatement().execute(
                        "select * from departments"
                );
            }
            catch (java.sql.SQLSyntaxErrorException ex)
            {
                con.createStatement().execute(
                        "CREATE TABLE departments (" +
                                "id int NOT NULL primary key," +
                                "Name varchar(30) NOT NULL," +
                                "Phone integer NOT NULL," +
                                "email varchar(30) NOT NULL" +
                                ");"
                );
                con.createStatement().execute(
                        "INSERT INTO departments " +
                                "(id, Name, Phone, email) VALUES " +
                                "(1, 'accounts department'," +
                                "88002600, 'avon@mail.ru');"
                );
                con.createStatement().execute(
                        "INSERT INTO departments " +
                                "(id, Name, Phone, email) VALUES " +
                                "(2, 'technical department'," +
                                "880026666, 'brutal@mail.ru');"
                );
            }

            try
            {
                con.createStatement().execute(
                        "select * from jobs"
                );
            }
            catch (java.sql.SQLSyntaxErrorException ex)
            {
                con.createStatement().execute(
                        "CREATE TABLE jobs (" +
                                "id int NOT NULL primary key," +
                                "Name varchar(30) NOT NULL," +
                                "Salary integer NOT NULL" +
                                ");"
                );
                con.createStatement().execute(
                        "INSERT INTO jobs (id, Name, Salary) VALUES (1, 'Director', 100000);"
                );
                con.createStatement().execute(
                        "INSERT INTO jobs (id, Name, Salary) VALUES (2, 'Main engineer', 90000)"
                );
                con.createStatement().execute(
                        "INSERT INTO jobs (id, Name, Salary) VALUES (3, 'engineer', 75000);"
                );
                con.createStatement().execute(
                        "INSERT INTO jobs (id, Name, Salary) VALUES (4, 'worker', 50000);"
                );
            }

            try
            {
                con.createStatement().execute(
                        "select * from workers"
                );
            }
            catch (java.sql.SQLSyntaxErrorException ex)
            {
                con.createStatement().execute(
                        "CREATE TABLE workers (" +
                                "id int NOT NULL," +
                                "Name varchar(50) NOT NULL," +
                                "Sername varchar(50) NOT NULL," +
                                "Patronymic varchar(50) NOT NULL," +
                                "department_id int NOT NULL," +
                                "job_id int NOT NULL," +
                                "primary key(id));"
                );
                con.createStatement().execute(
                        "alter table workers add foreign key (department_id) references departments (id)" +
                                " on update cascade;"
                );
                con.createStatement().execute(
                        "alter table workers add foreign key (job_id) references jobs (id) on update cascade;"
                );
                con.createStatement().execute(
                        "INSERT INTO workers " +
                                "(id, Name, Sername, Patronymic, department_id, job_id) VALUES " +
                                "(1, 'Harry', 'Potter', 'James', 2, 1);"
                );
                con.createStatement().execute(
                        "INSERT INTO workers " +
                                "(id, Name, Sername, Patronymic, department_id, job_id) VALUES " +
                                "(2, 'Hordon', 'Freeman', 'Unnamed', 2, 2);"
                );
                con.createStatement().execute(
                        "INSERT INTO workers " +
                                "(id, Name, Sername, Patronymic, department_id, job_id) VALUES " +
                                "(3, 'Alex', 'Vens', 'Illay', 2, 3);"
                );
                con.createStatement().execute(
                        "INSERT INTO workers " +
                                "(id, Name, Sername, Patronymic, department_id, job_id) VALUES " +
                                "(4, 'Alladin', 'Unnamed', 'Unnamed', 1, 3);"
                );
                con.createStatement().execute(
                        "INSERT INTO workers " +
                                "(id, Name, Sername, Patronymic, department_id, job_id) VALUES " +
                                "(5, 'Alladin', 'Unnamed', 'Unnamed', 1, 2);"
                );
                con.createStatement().execute(
                        "INSERT INTO workers " +
                                "(id, Name, Sername, Patronymic, department_id, job_id) VALUES " +
                                "(6, 'Artemiy', 'Lebedev', 'Tatiana', 2, 4);"
                );
            }

            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
