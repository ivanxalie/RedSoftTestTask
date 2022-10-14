import java.sql.Connection;
import java.util.Properties;
import javax.swing.*;

public class Main{
    public static void main(String[] args) throws Exception {

        String path = ".\\db\\users.fdb";
        String fullPath = new java.io.File( "." ).getCanonicalPath() + path;
        String databaseURL = "jdbc:firebirdsql:localhost/3050:" + fullPath;

        Properties props = new Properties();
        props.setProperty("user", "SYSDBA");
        props.setProperty("encoding", "UNICODE_FSS");
        GetAdminPassword myPswd = new GetAdminPassword(props, path, databaseURL);
        myPswd.pack();
        myPswd.setVisible(true);

        if (myPswd.getStatus() == 0)
        {
            Connection con = myPswd.getConnection();

            props.setProperty("password", myPswd.getPswd());
            MainForm myForm = new MainForm(props, databaseURL, con);
            myForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            myForm.setSize(720, 500);

            myForm.setVisible(true);
        }

    }
}
