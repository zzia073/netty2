package com.study.base.protobuf;

import com.study.base.protobuf.protofile.AddressBookProtos.*;

/**
 * @author ：fei
 * @date ：Created in 2019/11/19 0019 14:19
 */
public class TestProtobuf {
    public static void main(String[] args) throws Exception {
        //构造对象
        Student student = Student.newBuilder()
                                .setId(1)
                                .setName("小飞飞")
                                .setEmail("6666@qq.com")
                                .build();
        //序列化
        byte[] bytes = student.toByteArray();
        //========================================================
        //反序列化
        Student student1 = Student.parseFrom(bytes);
        System.out.println(student1.getId());
        System.out.println(student1.getName());
        System.out.println(student1.getEmail());
    }
}
