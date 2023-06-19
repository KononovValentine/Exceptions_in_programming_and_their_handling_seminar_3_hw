import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке,
        // разделенные пробелом: Фамилия Имя Отчество датарождения номертелефона пол
        // Форматы данных:
        // фамилия, имя, отчество - строки
        // дата_рождения - строка формата dd.mm.yyyy
        // номер_телефона - целое беззнаковое число без форматирования
        // пол - символ латиницей f или m.
        // Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым,
        // вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных,
        // чем требуется.
        // Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если
        // форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать
        // встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено
        // сообщение с информацией, что именно неверно.
        // Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну
        // строку должны записаться полученные данные, вида <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
        // Однофамильцы должны записаться в один и тот же файл, в отдельные строки. Не забудьте закрыть соединение
        // с файлом. При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
        // пользователь должен увидеть стектрейс ошибки.

        wrightData();
    }

    public static void wrightData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона " +
                "(число без разделителей) и пол(символ латиницей f или m), разделенные пробелом");

        String input = scanner.nextLine();
        String[] dataArray = getArray(input);
        checkFormats(dataArray);
        writeFile(dataArray);
    }

    public static String[] getArray(String input) {
        String[] result = input.split(" ");
        if (result.length < 6) {
            throw new RuntimeException("Введены не все данные!");
        } else if (result.length > 6) {
            throw new RuntimeException("Введены лишние данные!");
        }
        return result;
    }

    public static void checkFormats(String[] dataArray) {

        if (dataArray[0].matches(".*\\d.*")) {
            throw new RuntimeException("Фамилия не может содержать цифры!");
        }
        if (dataArray[1].matches(".*\\d.*")) {
            throw new RuntimeException("Имя не может содержать цифры!");
        }
        if (dataArray[2].matches(".*\\d.*")) {
            throw new RuntimeException("Отчество не может содержать цифры!");
        }
        if (dataArray[3].length() != 10 || !dataArray[3].contains(".")
                || dataArray[3].matches("[a-zA-Zа-яА-Я]*$")) {
            throw new RuntimeException("Неверный формат даты!");
        }
        if (dataArray[4].length() != 11 || dataArray[4].matches(".*\\D.*")) {
            throw new RuntimeException("Неверный формат телефона!");
        }
        if (!dataArray[5].equals("m") && !dataArray[5].equals("f")) {
            throw new RuntimeException("Неверно указан пол!");
        }
    }

    public static void writeFile(String[] dataArray) {
        String fileName = dataArray[0].toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s %s %s", dataArray[0], dataArray[1],
                    dataArray[2], dataArray[3], dataArray[4], dataArray[5]));
        }catch (IOException e){
            throw new RuntimeException("Возникла ошибка при работе с файлом");
        }
    }
}