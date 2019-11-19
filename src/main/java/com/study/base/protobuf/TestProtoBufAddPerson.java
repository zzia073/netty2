package com.study.base.protobuf;

import com.study.base.protobuf.protofile.AddressBookProtos;
import com.study.base.protobuf.protofile.AddressBookProtos.Student;

import java.io.*;

/**
 * @author ：fei
 * @date ：Created in 2019/11/19 0019 11:00
 */
public class TestProtoBufAddPerson {
    public static void main(String[] args) throws IOException {
        AddressBookProtos.AddressBook.Builder addressBook = AddressBookProtos.AddressBook.newBuilder();
        Student student = promptForAddress(new BufferedReader(new InputStreamReader(System.in)),System.out);
        addressBook.addPeople(student);
        FileOutputStream outputStream = new FileOutputStream("address_book.txt");
        addressBook.build().writeTo(outputStream);
        outputStream.close();
    }
    static Student promptForAddress(BufferedReader stdin, PrintStream stdout) throws IOException {
        Student.Builder student = Student.newBuilder();
        stdout.print("Enter student ID:");
        student.setId(Integer.valueOf(stdin.readLine()));
        stdout.print("Enter name: ");
        student.setName(stdin.readLine());
        stdout.print("Enter email address (blank for none): ");
        String email = stdin.readLine();
        if (email.length() > 0) {
            student.setEmail(email);
        }
        while (true) {
            stdout.print("Enter a phone number (or leave blank to finish): ");
            String number = stdin.readLine();
            if (number.length() == 0) {
                break;
            }

            Student.PhoneNumber.Builder phoneNumber =
                    Student.PhoneNumber.newBuilder().setNumber(number);

            stdout.print("Is this a mobile, home, or work phone? ");
            String type = stdin.readLine();
            if (type.equals("mobile")) {
                phoneNumber.setType(Student.PhoneType.MOBILE);
            } else if (type.equals("home")) {
                phoneNumber.setType(Student.PhoneType.HOME);
            } else if (type.equals("work")) {
                phoneNumber.setType(Student.PhoneType.WORK);
            } else {
                stdout.println("Unknown phone type.  Using default.");
            }

            student.addPhones(phoneNumber);
        }
        return student.build();
    }
}
