package com.yuxing.trainee.example.serialization;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author yuxing
 * @since 2022/1/24
 */
public class Main {

    @Test
    public void testWhenSerializingAndDeserializingThenObjectIsTheSame() throws Exception {
        Person person = new Person();
        person.setAge(20);
        person.setName("Joe");

        FileOutputStream fileOutputStream = new FileOutputStream("myPerson.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(person);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream("myPerson.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Person person2 = (Person) objectInputStream.readObject();
        objectInputStream.close();

        assertEquals(person2.getAge(), person.getAge());
        assertEquals(person2.getName(), person.getName());

    }
}
