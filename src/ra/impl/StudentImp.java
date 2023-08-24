package ra.impl;

import ra.entity.Student;

import java.io.*;
import java.util.*;

public class StudentImp {
    static List<Student>listStudent=new ArrayList<>();

    public static void main(String[] args) {
        //Đọc dữ liệu từ file listStudent.txt
        Scanner scanner=new Scanner(System.in);
        do {
            System.out.println("1. Nhập thông tin các sinh viên");
            System.out.println("2. Tính tuổi các sinh viên");
            System.out.println("3. Tính điểm trung bình và xếp loại");
            System.out.println("4. Sắp xếp sinh viên theo tuổi tăng dần");
            System.out.println("5. Thống kê sinh viên theo xếp loại sinh viên");
            System.out.println("6. Cập nhật thông tin sinh viên");
            System.out.println("7. Tìm kiếm sinh viên theo tên");
            System.out.println("8. Thoát");
            System.out.println("Sự lựa chọn của bạn: ");
            try {
                int choice=Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        StudentImp.inputListStudent(scanner);
                        break;
                    case 2:
                        StudentImp.calListAge();
                        break;
                    case 3:
                        StudentImp.calAvg_rank();
                        break;
                    case 4:
                        StudentImp.sortStudent();
                        break;
                    case 5:
                        StudentImp.statisticStudent();
                        break;
                    case 6:
                        StudentImp.updateStudent(scanner);
                        break;
                    case 7:
                        StudentImp.searchStudentByName(scanner);
                        break;
                    case 8:
                        writeDataToFile();
                        System.exit(0);
                        break;
                    default:
                        System.err.println("Vui lòng chọn từ 1-8.");
                }
            } catch (NumberFormatException nfe){
                System.err.println("Vui lòng chọn số.");
            } catch (Exception ex){
                System.err.println("Có lỗi trong quá trình thực hiện, vui lòng kiểm tra lại.");
            }
        }while (true);
    }
    public static void readDataFromFile(){
        //1. Khởi tạo đối tượng File
        File file=new File("listStudent.txt");
        FileInputStream fis=null;
        ObjectInputStream ois=null;
        try {
            //2. Khởi tạo đối tượng FileInputStream
            fis=new FileInputStream(file);
            //3. Khởi tạo đối tượng ObjectInputStream
            ois=new ObjectInputStream(fis);
            //4. Đọc dữ liệu object từ file (readObject())
            if(ois.readObject()!=null){
                listStudent=(List<Student>) ois.readObject();
            }
        } catch (FileNotFoundException e){
            System.err.println("Không tồn tại file.");
        } catch (IOException e){
            System.err.println("Lỗi trong quá trình thực hiện, vui lòng kiểm tra lại.");
        } catch (ClassNotFoundException e){
            System.err.println("Lớp không tồn tại.");
        } finally {
            //5. Đóng các stream
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    System.err.println("Có lỗi khi đóng stream");
                }
            }
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    System.err.println("Có lỗi trong quá trình đóng các stream");
                }
            }
        }
    }
    public static void writeDataToFile(){
        //1. Khởi tạo đối tượng file
        File file=new File("listStudent.txt");
        FileOutputStream fos=null;
        ObjectOutput oos=null;
        try {
            //2. Khởi tạo đối tượng FileOutputStream từ file - Checked Excetion
            fos=new FileOutputStream(file);
            //3. Khởi tạo đối tượng ObjectOutputStream từ fos
            oos=new ObjectOutputStream(fos);
            //4. Sử dụng writeObject để ghi object ra file
            oos.writeObject(listStudent);
            //5. Đẩy dữ liệu từ Stream xuống file
            oos.flush();
        } catch (FileNotFoundException e){
            System.err.println("File không tồn tại");
        } catch (IOException e){
            System.err.println("Lỗi khi ghi dữ liệu ra file");
        } catch (Exception ex){
            System.err.println("Xảy ra lỗi trong quá trình ghi dữ liệu ra file");
        } finally {
            //6. Đóng các stream
            try {
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        System.err.println("Xảy ra lỗi khi đóng các stream");
                    }
                }
                if(oos!=null){
                    try {
                        oos.close();
                    } catch (IOException e) {
                        System.err.println("Xảy ra lỗi khi đóng các stream");
                    }
                }
            } catch (Exception ex){
                System.err.println("Xảy ra lỗi trong quá trình đóng các stream");
            }
        }
    }
    // Methor nhập thông tin các sinh viên
    public static void inputListStudent(Scanner scanner){
        System.out.println("Nhập số sinh viên cần nhập dữ liệu :");
        do {
            try {
                int number=Integer.parseInt(scanner.nextLine());
                for (int i = 0; i <number ; i++) {
                    System.out.println("Nhập thông tin cho sách thứ " + (i + 1) + ":");
                    Student student=new Student();
                    student.inputData(scanner,listStudent);
                    listStudent.add(student);
                }
                break;
            } catch (NumberFormatException e){
                System.err.println("Dữ liệu nhập là số, vui lòng nhập lại.");
            } catch (Exception ex){
                System.err.println("Lỗi xảy ra trong quá trình nhập, vui lòng kiểm tra lại.");
            }
        }while (true);
    }
    // Methor tính tuổi các sinh viên
    public static void calListAge(){
        for (Student student:listStudent) {
            student.calAge();
        }
        System.out.println("Đã tính xong tuổi cho tất cả sinh viên");
    }
    // Methor tính điểm trung bình và xếp loại
    public static void calAvg_rank(){
        for (Student student:listStudent) {
            student.calAvgMark_Rank();
        }
        System.out.println("Đã tính xong điểm trung bình và xếp loại cho tất cả các sinh viên");
    }
    // Methor sắp xếp sinh viên theo tuổi tăng dần
    public static void sortStudent(){
        Collections.sort(listStudent, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge()-o2.getAge();
            }
        });
        System.out.println("Đã xong sắp xếp sinh viên theo tuổi tăng dần ");
    }
    //Methor thống kê sinh viên theo xếp loại sinh viên
    public static void statisticStudent(){
        int cntYeu=0;
        int cntTrungBinh=0;
        int cntKha=0;
        int cntGioi=0;
        int cntXuatsac=0;
        for (Student student:listStudent) {
            if(student.getRank().equals("Yếu")){
                cntYeu++;
            }else if(student.getRank().equals("rung bình")){
                cntTrungBinh++;
            }else if(student.getRank().equals("Khá")){
                cntKha++;
            }else if(student.getRank().equals("Giỏi")){
                cntGioi++;
            }else{
                cntXuatsac++;
            }
        }
        System.out.printf("Thống kê: Xuất sắc: %d - Giỏi: %d - Khá: %d - Trung bình: %d - Yếu: %d\n", cntXuatsac, cntGioi, cntKha, cntTrungBinh, cntYeu);
    }
    // Methor cập nhật thông tin sinh viên
    public static void updateStudent(Scanner scanner){
        System.out.println("Nhập vào mã sinh viên cần cập nhật :");
        String studentId= scanner.nextLine();
        int index=-1;
        for (int i = 0; i < listStudent.size(); i++) {
            if(listStudent.get(i).getStudentId().equals(studentId)){
                index=i;
                break;
            }
        }
        if(index!=-1){
            // Cập nhật
            listStudent.get(index).setStudentName(Student.validateStudentName(scanner));
            listStudent.get(index).setSex(Student.validateSex(scanner));
            listStudent.get(index).setBirthDay(Student.validateBirthDay(scanner));
            listStudent.get(index).setMark_html(Student.validateMarkHTML(scanner));
            listStudent.get(index).setMark_css(Student.validateMarkCss(scanner));
            listStudent.get(index).setMark_javascript(Student.validateMarkJavascript(scanner));
            listStudent.get(index).calAge();
            listStudent.get(index).calAvgMark_Rank();
        }else{
            System.err.println("Mã sinh viên không tồn tại, vui lòng nhập lại.");
        }
    }
    // Methor tìm kiếm sinh viên theo tên
    public static void searchStudentByName(Scanner scanner){
        System.out.println("Nhập vào tên sinh viên cần tìm :");
        String searchName= scanner.nextLine();
        for (Student student:listStudent) {
            if(student.getStudentName().toLowerCase().contains(searchName.toLowerCase())){
               student.displayData();
            }
        }
    }
}
