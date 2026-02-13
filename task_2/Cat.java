package task_2;

public class Cat extends Animal {

    private static int catsCount = 0;
    private static final int MAX_RUN_DISTANCE = 200;

    public static int getCatsCount() {
        return catsCount;
    }

    public Cat(String name) {
        super(name);
        catsCount ++;
    }

    @Override
    public void swim(int distance) {
        System.out.println("Коты не умеют плавать");
    }

    @Override
    public void run(int distance) {
        if (distance == 0) {
            System.out.println("Расстояние должно быть больше 0");
            return;
        }
        if (distance <= MAX_RUN_DISTANCE) {
            System.out.println(Cat.super.name + " пробежал " + distance + " метров.");
        }
        else {
            System.out.println(Cat.super.name + " пробежал " + MAX_RUN_DISTANCE +
                               " метров и выдохся, не добежав " +
                               (distance - MAX_RUN_DISTANCE) + " метров.");
        }
    }
}