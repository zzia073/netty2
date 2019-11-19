package com.study.base.protobuf;

import com.study.base.protobuf.protofile.AddressBookProtos.*;

import java.io.FileInputStream;

import static com.study.base.protobuf.protofile.AddressBookProtos.Student.PhoneType.MOBILE;

/**
 * @author ：fei
 * @date ：Created in 2019/11/19 0019 14:14
 */
public class ListPeople {
    // Iterates though all people in the AddressBook and prints info about them.
    static void Print(AddressBook addressBook) {
        for (Student person: addressBook.getPeopleList()) {
            System.out.println("Person ID: " + person.getId());
            System.out.println("Name: " + person.getName());
            if (person.hasEmail()) {
                System.out.println("E-mail address: " + person.getEmail());
            }

            for (Student.PhoneNumber phoneNumber : person.getPhonesList()) {
                switch (phoneNumber.getType()) {
                    case MOBILE:
                        System.out.print("Mobile phone #: ");
                        break;
                    case HOME:
                        System.out.print("Home phone #: ");
                        break;
                    case WORK:
                        System.out.print("Work phone #: ");
                        break;
                    default:
                        break;
                }
                System.out.println(phoneNumber.getNumber());
            }
        }
    }

    // Main function:  Reads the entire address book from a file and prints all
    //   the information inside.
    public static void main(String[] args) throws Exception {

        // Read the existing address book.
        AddressBook addressBook =
                AddressBook.parseFrom(new FileInputStream("address_book.txt"));

        Print(addressBook);
    }
}
