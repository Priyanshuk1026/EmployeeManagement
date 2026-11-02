
import java.util.*;

enum LeaveType {
    CASUAL, SICK, EARNED
}

enum LeaveStatus {
    PENDING, APPROVED, REJECTED
}

class Employee {
    private String id;
    private String name;
    private String department;
    private String email;
    protected int leaveBalance;

    public Employee(String id, String name, String department,
                    String email, int leaveBalance) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.leaveBalance = leaveBalance;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getLeaveBalance() {
        return leaveBalance;
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Email: " + email);
        System.out.println("Leave Balance: " + leaveBalance);
    }

    public String getType() {
        return "Employee";
    }
}

class PermanentEmployee extends Employee {
    public PermanentEmployee(String id, String name, String department,
                             String email, int balance) {
        super(id, name, department, email, balance);
    }

    @Override
    public String getType() {
        return "Permanent";
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String id, String name, String department,
                            String email, int balance) {
        super(id, name, department, email, balance);
    }

    @Override
    public String getType() {
        return "Contract";
    }
}

class LeaveRequest {
    String requestId;
    Employee employee;
    LeaveType type;
    int days;
    String reason;
    LeaveStatus status;

    public LeaveRequest(String requestId, Employee employee,
                        LeaveType type, int days, String reason) {
        this.requestId = requestId;
        this.employee = employee;
        this.type = type;
        this.days = days;
        this.reason = reason;
        this.status = LeaveStatus.PENDING;
    }

    public void display() {
        System.out.println("Request ID: " + requestId);
        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Leave Type: " + type);
        System.out.println("Days: " + days);
        System.out.println("Reason: " + reason);
        System.out.println("Status: " + status);
    }
}

public class Leave_Emp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Employee> employees = new ArrayList<>();
        Set<String> emails = new HashSet<>();
        Map<String, Employee> employeeMap = new HashMap<>();
        List<LeaveRequest> requests = new ArrayList<>();

        int requestNumber = 1001;
        int choice;

        do {
            System.out.println("\n1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Apply Leave");
            System.out.println("5. Approve Leave");
            System.out.println("6. Reject Leave");
            System.out.println("7. Display Leave Requests");
            System.out.println("8. Display Leave Balance");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Employee ID: ");
                    String id = sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Department: ");
                    String department = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Leave Balance: ");
                    int balance = Integer.parseInt(sc.nextLine());

                    System.out.print("1. Permanent  2. Contract: ");
                    int type = Integer.parseInt(sc.nextLine());

                    if (id.trim().isEmpty() || name.trim().isEmpty()
                            || department.trim().isEmpty()
                            || email.trim().isEmpty()) {
                        System.out.println("Details cannot be empty.");
                        break;
                    }

                    if (balance < 0) {
                        System.out.println("Balance cannot be negative.");
                        break;
                    }

                    if (employeeMap.containsKey(id)
                            || emails.contains(email.toLowerCase(Locale.ROOT))) {
                        System.out.println("Duplicate ID or email.");
                        break;
                    }

                    Employee employee;

                    if (type == 1) {
                        employee = new PermanentEmployee(
                            id, name, department, email, balance);
                    } else if (type == 2) {
                        employee = new ContractEmployee(
                            id, name, department, email, balance);
                    } else {
                        System.out.println("Invalid employee type.");
                        break;
                    }

                    employees.add(employee);
                    employeeMap.put(id, employee);
                    emails.add(email.toLowerCase(Locale.ROOT));

                    System.out.println("Employee added successfully.");
                    System.out.println("Type: " + employee.getType());
                    break;

                case 2:
                    if (employees.isEmpty()) {
                        System.out.println("No employees found.");
                        break;
                    }

                    Iterator<Employee> iterator = employees.iterator();

                    while (iterator.hasNext()) {
                        Employee e = iterator.next();
                        e.display();
                        System.out.println("Type: " + e.getType());
                        System.out.println("----------------");
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID: ");
                    id = sc.nextLine();

                    employee = employeeMap.get(id);

                    if (employee == null) {
                        System.out.println("Employee not found.");
                    } else {
                        employee.display();
                        System.out.println("Type: " + employee.getType());
                    }
                    break;

                case 4:
                    System.out.print("Enter Employee ID: ");
                    id = sc.nextLine();

                    employee = employeeMap.get(id);

                    if (employee == null) {
                        System.out.println("Employee not found.");
                        break;
                    }

                    System.out.print("Leave Type (CASUAL/SICK/EARNED): ");
                    LeaveType leaveType = LeaveType.valueOf(
                        sc.nextLine().toUpperCase(Locale.ROOT));

                    System.out.print("Number of Days: ");
                    int days = Integer.parseInt(sc.nextLine());

                    if (days <= 0) {
                        System.out.println(
                            "Leave days must be greater than 0.");
                        break;
                    }

                    if (days > employee.getLeaveBalance()) {
                        System.out.println("Insufficient leave balance.");
                        System.out.println("Available: "
                                           + employee.getLeaveBalance());
                        System.out.println("Requested: " + days);
                        break;
                    }

                    System.out.print("Reason: ");
                    String reason = sc.nextLine();

                    LeaveRequest request = new LeaveRequest(
                        "LR" + requestNumber++, employee,
                        leaveType, days, reason);

                    requests.add(request);

                    System.out.println("Leave request created.");
                    System.out.println("Request ID: " + request.requestId);
                    System.out.println("Status: " + request.status);
                    break;

                case 5:
                    System.out.print("Enter Request ID: ");
                    String requestId = sc.nextLine();

                    LeaveRequest found = null;

                    for (LeaveRequest r : requests) {
                        if (r.requestId.equalsIgnoreCase(requestId)) {
                            found = r;
                            break;
                        }
                    }

                    if (found == null) {
                        System.out.println("Request not found.");
                    } else if (found.status != LeaveStatus.PENDING) {
                        System.out.println("Request already processed.");
                    } else if (found.days > found.employee.getLeaveBalance()) {
                        System.out.println("Insufficient leave balance.");
                    } else {
                        int previous = found.employee.getLeaveBalance();

                        found.employee.leaveBalance -= found.days;
                        found.status = LeaveStatus.APPROVED;

                        System.out.println("Leave approved.");
                        System.out.println("Previous Balance: " + previous);
                        System.out.println("Leave Used: " + found.days);
                        System.out.println("Remaining Balance: "
                                + found.employee.getLeaveBalance());
                    }
                    break;

                case 6:
                    System.out.print("Enter Request ID: ");
                    requestId = sc.nextLine();

                    found = null;

                    for (LeaveRequest r : requests) {
                        if (r.requestId.equalsIgnoreCase(requestId)) {
                            found = r;
                            break;
                        }
                    }

                    if (found == null) {
                        System.out.println("Request not found.");
                    } else if (found.status != LeaveStatus.PENDING) {
                        System.out.println("Request already processed.");
                    } else {
                        found.status = LeaveStatus.REJECTED;
                        System.out.println("Leave rejected.");
                        System.out.println("Balance unchanged.");
                    }
                    break;

                case 7:
                    if (requests.isEmpty()) {
                        System.out.println("No leave requests.");
                    }

                    for (LeaveRequest r : requests) {
                        r.display();
                        System.out.println("----------------");
                    }
                    break;

                case 8:
                    System.out.print("Enter Employee ID: ");
                    id = sc.nextLine();

                    employee = employeeMap.get(id);

                    if (employee == null) {
                        System.out.println("Employee not found.");
                    } else {
                        System.out.println("Name: " + employee.getId());
                        System.out.println("Leave Balance: "
                                           + employee.getLeaveBalance());
                    }
                    break;

                case 9:
                    System.out.println("Application closed.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 9);

        sc.close();
    }
}
