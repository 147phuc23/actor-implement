package org.example;

import java.util.Random;

public class Application {

    public static void main(String[] args) {
        Actor<Student> studentActor = new Actor<Student>("/student1", Student.class);
        Actor<Teacher> teacherActor = new Actor<Teacher>("/teacher1", Teacher.class);
        Actor<SystemActor> systemActor = new Actor<SystemActor>("/", SystemActor.class);


        // teacher tell student to set value = 10
        studentActor.tell(teacherActor, (Student st)->{
            st.setValue(10);
        });

        // student ask teacher total value, then set that value to itself
        teacherActor.ask(studentActor, (Teacher teacher) -> {
            for (int i = 0; i< new Random().nextInt(10000)*100; i++) {
                continue;
            }
            return teacher.totalValue;
        }, (student, value)-> {
            System.out.println("value return async operation" + value);
            ((Student) student).setValue((Integer)value);
        });

        // student tell system to print anything
        systemActor.tell(studentActor, (sys)->{
            System.out.println(1999);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}