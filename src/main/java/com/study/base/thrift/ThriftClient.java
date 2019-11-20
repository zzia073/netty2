package com.study.base.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * @author ：fei
 * @date ：Created in 2019/11/20 0020 09:04
 */
public class ThriftClient {
    public static void main(String[] args) {
        //客户端的transport和protocol要和服务端是相同的
        TTransport tTransport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(tTransport);

        PersonService.Client client = new PersonService.Client(protocol);

        try {
            tTransport.open();
            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getUsername() + " " + person.getAge() + " " + person.isMarried());
            System.out.println("=============================");
            Person person1 = new Person();
            person1.setUsername("李四");
            person1.setAge(19);
            person1.setMarried(true);
            client.savePerson(person);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        } finally {
            tTransport.close();
        }
    }
}
