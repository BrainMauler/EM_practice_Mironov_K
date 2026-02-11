import java.util.*;

public class Runner {

    public static void main(String[] args) throws MyArrayDataException, MyArraySizeException {
        String testTextRus = "Это тестовая,        тестовая:., строка строка строка ";
        String testTextEng = "This is test,   test,./' text text...";
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList("Первый", "Второй", "Третий"));
        ArrayList<Integer> integerArrayList = new ArrayList<>(Arrays.asList(1, 2, 3));
        Integer[] integers = {1, 2, 3, 4, 5};
        String[][] correctArray = {{"1", "0", "15", "300"}, {"8", "14", "250", "777"},
                                   {"-30", "500", "850", "-0"}, {"12", "15", "3000", "-250"}};
        String[][] incorrectDimArray = {{"1", "0", "15", "300"}, {"8", "14", "250", "777"},
                                        {"-30", "500", "850", "-0"}, {"12", "15", "3000"}};
        String[][] incorrectDimsArray = {{"1", "0", "15"}, {"8", "14", "250"},
                                         {"-30", "500", "850.0", "-0"}};
        String[][] incorrectDataArray = {{"1", "0", "15", "300"}, {"8", "", "/", "Seven"},
                                         {"-30", "500", "850.0", "-0"}, {"12", "15", "3000", "-250"}};
        wordsCounter(testTextRus, "rus");
        wordsCounter(testTextEng, "eng");
        wordsCounter(testTextEng, "chn");
        arrayListRounder(stringArrayList);
        arrayListRounder(integerArrayList);
        System.out.println(arrayToArayList(integers));
        intsOpsStreams(0, 30, 10);
        intsOpsStreams(0, 1, 10);
        System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(correctArray));
        //System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(incorrectDimsArray));
        //System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(incorrectDimArray));
        //System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(incorrectDataArray));
    }

    //Задача 1
    public static void wordsCounter(String sentence, String language) {
        String kirRegex = "[^а-яА-ЯёЁ]+";
        String latRegex = "[^a-zA-Z]+";
        List<String> words = new ArrayList<>();
        switch (language) {
            case "rus":
                words = Arrays.asList(sentence.toLowerCase().split(kirRegex));
                break;
            case "eng":
                words = Arrays.asList(sentence.toLowerCase().split(latRegex));
                break;
            default:
                System.out.println("Метод не распознает строки на языке " + language);
                break;
        }
        HashMap<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                counter.put(word, counter.getOrDefault(word, 0) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            System.out.println("Слово \"" + entry.getKey() + "\" встречается в строке раз: " +
                               entry.getValue());
        }
    }
    //Задача 2
    public static <T> void arrayListRounder(ArrayList<T> arrayList) {
        for (int i = 0; i < arrayList.size(); i ++) {
            System.out.println((i + 1) + " элемент списка: " + arrayList.get(i));
        }
        System.out.println("Это через цикл for.");
        int index = 0;
        while (index < arrayList.size()) {
            System.out.println((index + 1)+ " элемент списка: " + arrayList.get(index));
            index ++;
        }
        System.out.println("Это через цикл while.");
        for (T element: arrayList) {
            System.out.println((arrayList.indexOf(element) + 1) + " элемент списка: " + element);
        }
        System.out.println("Это через цикл for-each.");
    }
    //Задача 3
    public static <T> ArrayList<T> arrayToArayList(T[] array) {
        return new ArrayList<>(Arrays.stream(array).toList());
    }
    //Задача 4
    public static void intsOpsStreams(int min, int max, int size) {
        Random random = new Random();
        List<Integer> integersList;
        integersList = random.ints(size, min, max + 1).boxed().toList();
        System.out.println("Сгенерирован список: " + integersList);
        List<Integer> evensList = integersList.stream().filter((x) -> x % 2 == 0).toList();
        System.out.println("Убраны нечетные: " + evensList);
        List<Integer> doubledList = evensList.stream().map((x) -> x * 2).toList();
        System.out.println("Удвоены оставшиеся: " + doubledList);
        int sum = doubledList.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Сумма оставшихся чисел: " + sum);
    }
    //Задача 5
    public static int twoDimArraySum(String[][] array) throws MyArraySizeException, MyArrayDataException {
        if (array.length != 4) {
            throw new MyArraySizeException("Количество сток массива должен быть 4, а не "
                                           + array.length);
        }
        for (int i = 0; i < 4; i ++) {
            if (array[i].length != 4) {
                throw new MyArraySizeException("Размер строки " + (i + 1) + " массива должен быть 4, а не "
                        + array[i].length);
            }
        }
        int sum = 0;
        for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 4; j ++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                }
                catch (NumberFormatException exception) {
                    throw new MyArrayDataException("Формат содержимого ячейки " + (i + 1) + " колонки и "
                                                   + (j + 1) + " строки, а именно " + array[i][j] +
                                                   " не соответствует целочисленному преобразованию");
                }
            }
        }
        return sum;
    }
}