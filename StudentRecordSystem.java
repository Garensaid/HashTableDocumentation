import java.util.Scanner;

public class StudentRecordSystem {

    private static final int TABLE_SIZE = 10;
    private Node[] table;

    public StudentRecordSystem() {
        table = new Node[TABLE_SIZE];
    }

    private int hashFunction(String key) {
        int sum = 0;
        for (char c : key.toCharArray()) {
            sum += c;
        }
        return sum % TABLE_SIZE;
    }

    public void addStudentRecord(String studentID, String name, String grade) {
        int index = hashFunction(studentID);
        if (table[index] == null) {
            table[index] = new Node(studentID, name, grade);
        } else {
            Node node = table[index];
            while (node.next != null) {
                node = node.next;
            }
            node.next = new Node(studentID, name, grade);
        }
    }

    public String searchStudentRecord(String studentID) {
        int index = hashFunction(studentID);
        if (table[index] == null) {
            return null;
        } else {
            Node node = table[index];
            while (node != null) {
                if (node.studentID.equals(studentID)) {
                    return node.name + ", " + node.grade;
                }
                node = node.next;
            }
            return null;
        }
    }

    public void deleteStudentRecord(String studentID) {
        int index = hashFunction(studentID);
        if (table[index] == null) {
            return;
        } else {
            Node node = table[index];
            if (node.studentID.equals(studentID)) {
                table[index] = node.next;
                return;
            }
            Node prev = node;
            node = node.next;
            while (node != null) {
                if (node.studentID.equals(studentID)) {
                    prev.next = node.next;
                    return;
                }
                prev = node;
                node = node.next;
            }
        }
    }

    public void displayStudentRecords() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            Node node = table[i];
            System.out.print("Index " + i + ": ");
            while (node != null) {
                System.out.print(node.studentID + " = " + node.name + ", " + node.grade + ", ");
                node = node.next;
            }
            System.out.println();
        }
    }

    private static class Node {
        String studentID;
        String name;
        String grade;
        Node next;

        public Node(String studentID, String name, String grade) {
            this.studentID = studentID;
            this.name = name;
            this.grade = grade;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        StudentRecordSystem studentRecordSystem = new StudentRecordSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student Record");
            System.out.println("2. Search Student Record");
            System.out.println("3. Delete Student Record");
            System.out.println("4. Display Student Records");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();
                    studentRecordSystem.addStudentRecord(studentID, name, grade);
                    break;
                case 2:
                    System.out.print("Enter student ID to search: ");
                    studentID = scanner.nextLine();
                    String result = studentRecordSystem.searchStudentRecord(studentID);
                    if (result != null) {
                        System.out.println("Name, Grade: " + result);
                    } else {
                        System.out.println("Student record not found");
                    }
                    break;
                case 3:
                    System.out.print("Enter student ID to delete: ");
                    studentID = scanner.nextLine();
                    studentRecordSystem.deleteStudentRecord(studentID);
                    break;
                case 4:
                    studentRecordSystem.displayStudentRecords();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}