package task_2;

public abstract class Animal {

    private static int animalCount = 0;
    protected String name;

    public static int getAnimalCount() {
        return animalCount;
    }

    public Animal(String name) {
        this.name = name;
        animalCount ++;
    }

    public abstract void run(int distance);
    public abstract void swim(int distance);
}