package org.nyjsl.reflect;

/**
 * Created by pc on 2017/3/23.
 */

public class AssignableFromTest {

    public static void main(String[] args) {

        System.out.println(Father.class.isAssignableFrom(Child.class));//true
        System.out.println(Father.class.isAssignableFrom(Father.class));//true
        System.out.println(Child.class.isAssignableFrom(Father.class));//false

    }
}



interface Father{

}

class Child implements Father{

}
