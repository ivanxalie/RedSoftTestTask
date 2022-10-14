import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Properties;
import javax.swing.*;

public class MainForm extends JFrame {
    private JPanel panel1;
    private JComboBox workerComboBox;
    private JTextField jobName;
    private JTextField sername;
    private JTextField patronymic;
    private JTextField salary;
    private JTextField phone;
    private JTextField email;
    private JButton addWorkerButton1;
    private JButton deleteWorkerButton1;
    private JButton editWorkerButton1;
    private JTextField firstName;
    private JTextField departmentName;
    private JButton changeDepartments;
    private JButton changeJobs;
    private JButton exitButton;
    MainForm myForm;
    private Connection con;
    private Properties props;
    private String databaseURL;

    public MainForm(Properties props, String databaseURL, Connection con) {
        this.setTitle("Тестовое задание");

        this.databaseURL = databaseURL;
        this.props = props;
        try {
            this.con = DriverManager.getConnection(databaseURL, props);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        myForm = this;
        fillForm();

        this.setContentPane(panel1);

        workerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillCredential();
            }
        });
        addWorkerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection myConnetction = DriverManager.getConnection(databaseURL, props);
                    AddWorker worker = new AddWorker(myConnetction, myForm);
                    worker.setSize(300, 300);
                    worker.setVisible(true);
                    myConnetction.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        deleteWorkerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection myCon = DriverManager.getConnection(databaseURL, props);

                    int index = workerComboBox.getSelectedIndex() - 1;
                    myCon.createStatement().execute(
                            "delete from workers where id=" + (String) workerComboBox.getSelectedItem()
                    );
                    fillForm();
                    if (index != 0)
                        workerComboBox.setSelectedIndex(index);

                    myCon.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        editWorkerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection myCon = DriverManager.getConnection(databaseURL, props);

                    String id = (String) workerComboBox.getSelectedItem();
                    EditWorker worker = new EditWorker(con, myForm, id);
                    worker.setSize(500, 300);
                    worker.setVisible(true);
                    myCon.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        changeDepartments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection myCon = DriverManager.getConnection(databaseURL, props);
                    DepartmentsEditor dEdit = new DepartmentsEditor(myCon);
                    dEdit.setSize(500, 300);
                    dEdit.setVisible(true);
                    myCon.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        changeJobs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection myCon = DriverManager.getConnection(databaseURL, props);
                    JobsEditor jEdit = new JobsEditor(myCon);
                    jEdit.setSize(500, 300);
                    jEdit.setVisible(true);
                    myCon.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void fillForm() {
        workerComboBox.removeAllItems();
        fillWorkers();
        fillCredential();
    }


    public void fillWorkers() {
        try {
            con = DriverManager.getConnection(databaseURL, props);
            ResultSet workersQuery = con.createStatement().executeQuery(
                    "select id from workers order by id"
            );

            workerComboBox.removeAllItems();
            while (workersQuery.next()) {
                workerComboBox.addItem((Object) workersQuery.getString("id"));
            }
            con.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void fillCredential() {
        try {
            String workerId = (String) workerComboBox.getSelectedItem();
            con = DriverManager.getConnection(databaseURL, props);

            String sql = "select workers.name, sername, patronymic, departments.name as dname, phone, email, " +
                    "jobs.name as jname, salary " +
                    "from workers inner join jobs on (workers.job_id=jobs.id) " +
                    "inner join departments on (workers.department_id=departments.id) where workers.id=" + workerId;

            ResultSet credentialQuery = con.createStatement().executeQuery(sql);

            while (credentialQuery.next()) {
                sername.setText(credentialQuery.getString("sername"));
                firstName.setText(credentialQuery.getString("name"));
                patronymic.setText(credentialQuery.getString("patronymic"));
                salary.setText(credentialQuery.getString("salary"));
                departmentName.setText(credentialQuery.getString("dname"));
                jobName.setText(credentialQuery.getString("jname"));
                phone.setText(credentialQuery.getString("phone"));
                email.setText(credentialQuery.getString("email"));
            }
            con.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setEnabled(true);
        panel1.setToolTipText("");
        final JLabel label1 = new JLabel();
        label1.setEnabled(true);
        label1.setText("Фамилия");
        label1.setVisible(true);
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setEnabled(true);
        label2.setText("Имя");
        label2.setVisible(true);
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setEnabled(true);
        label3.setText("Отчество");
        label3.setVisible(true);
        panel1.add(label3, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sername = new JTextField();
        sername.setEditable(false);
        sername.setEnabled(true);
        sername.setVisible(true);
        panel1.add(sername, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        patronymic = new JTextField();
        patronymic.setEditable(false);
        patronymic.setEnabled(true);
        patronymic.setVisible(true);
        panel1.add(patronymic, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setEnabled(true);
        label4.setText("Заработная плата");
        label4.setVisible(true);
        panel1.add(label4, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setEnabled(true);
        label5.setText("Рабочий телефон");
        label5.setVisible(true);
        panel1.add(label5, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setEnabled(true);
        label6.setText("Рабочий email");
        label6.setVisible(true);
        panel1.add(label6, new com.intellij.uiDesigner.core.GridConstraints(6, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        salary = new JTextField();
        salary.setEditable(false);
        salary.setEnabled(true);
        salary.setVisible(true);
        panel1.add(salary, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        phone = new JTextField();
        phone.setEditable(false);
        phone.setEnabled(true);
        phone.setVisible(true);
        panel1.add(phone, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        email = new JTextField();
        email.setEditable(false);
        email.setEnabled(true);
        email.setVisible(true);
        panel1.add(email, new com.intellij.uiDesigner.core.GridConstraints(7, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addWorkerButton1 = new JButton();
        addWorkerButton1.setEnabled(true);
        addWorkerButton1.setText("Добавить");
        addWorkerButton1.setVisible(true);
        panel1.add(addWorkerButton1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteWorkerButton1 = new JButton();
        deleteWorkerButton1.setEnabled(true);
        deleteWorkerButton1.setText("Удалить");
        deleteWorkerButton1.setVisible(true);
        panel1.add(deleteWorkerButton1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editWorkerButton1 = new JButton();
        editWorkerButton1.setText("Редактировать");
        panel1.add(editWorkerButton1, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstName = new JTextField();
        firstName.setEditable(false);
        panel1.add(firstName, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setEnabled(true);
        label7.setText("Выберете сотрудника");
        label7.setVisible(true);
        panel1.add(label7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        workerComboBox = new JComboBox();
        workerComboBox.setActionCommand("");
        workerComboBox.setEnabled(true);
        workerComboBox.setVisible(true);
        panel1.add(workerComboBox, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setEnabled(true);
        label8.setText("Должность");
        label8.setVisible(true);
        panel1.add(label8, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jobName = new JTextField();
        jobName.setEditable(false);
        jobName.setEnabled(true);
        jobName.setVisible(true);
        panel1.add(jobName, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Отдел");
        panel1.add(label9, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        departmentName = new JTextField();
        departmentName.setEditable(false);
        panel1.add(departmentName, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        changeDepartments = new JButton();
        changeDepartments.setText("Изменение списка должностей");
        panel1.add(changeDepartments, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changeJobs = new JButton();
        changeJobs.setText("Изменение списка работ");
        panel1.add(changeJobs, new com.intellij.uiDesigner.core.GridConstraints(6, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Выход");
        panel1.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(7, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
