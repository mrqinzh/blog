public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Test test = new Test();
        test.name = "asdas";
        System.out.println(test.name);
    }
}

class Test {
    public String name;
}
