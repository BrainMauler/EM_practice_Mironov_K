package task_2;

public class Dog extends Animal {

    private static int dogsCount = 0;
    private final static int MAX_RUN_DISTANCE = 500;
    private final static int MAX_SWIM_DISTANCE = 10;

    public static int getDogsCount() {
        return dogsCount;
    }

    public Dog(String name) {
        super(name);
        dogsCount ++;
    }

    @Override
    public void swim(int distance) {
        if (distance == 0) {
            System.out.println("Расстояние должно быть больше 0");
            return;
        }
        if (distance <= MAX_SWIM_DISTANCE) {
            System.out.println(Dog.super.name + " проплыл " + distance + " метров.");
        }
        else {
            System.out.println(Dog.super.name + " проплыл " + MAX_SWIM_DISTANCE +
                               " метров и утоп, не доплыв " +
                               (distance - MAX_SWIM_DISTANCE) + " метров.");
        }
    }

    @Override
    public void run(int distance) {
        if (distance == 0) {
            System.out.println("Расстояние должно быть больше 0");
            return;
        }
        if (distance <= Dog.MAX_RUN_DISTANCE) {
            System.out.println(Dog.super.name + " пробежал " + distance + " метров.");
        }
        else {
            System.out.println(Dog.super.name + " пробежал " + Dog.MAX_RUN_DISTANCE +
                               " метров и выдохся, не добежав " +
                               (distance - Dog.MAX_RUN_DISTANCE) + " метров.");
        }
    }
}