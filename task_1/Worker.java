package task_1;

public class Worker {

    private String fullName;
    private String email;
    private String phone;
    private double salary;
    private int age;

    public int getAge() {
        return this.age;
    }

    public Worker(String fullName, String email, String phone,
                  double salary, int age) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    public void getWorkerInfo(Worker worker) {
        System.out.println("ФИО сотрудника: " + worker.fullName + "\nЭл. почта: "
                           + worker.email + "\nТелефон: " + worker.phone
                           + "\nЗарплата: " + worker.salary + "\nВозраст: "
                           + worker.age);
    }
}