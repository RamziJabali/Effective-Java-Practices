public class staticFactoryMethod {
    public static void main(String[] args) {
        String value1 = String.valueOf(1);
        String value2 = String.valueOf(1.0);
        String value3 = String.valueOf(true);
        String value4 = String.valueOf('h');
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);
        System.out.println(value4);
        CoordinatesStaticFactoryMethod coordinates = CoordinatesStaticFactoryMethod.fromAngles(30,1);
        System.out.println(coordinates);
    }
}

