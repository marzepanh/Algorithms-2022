package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     *
     * Затраты:
     *  T = O(n * log(n))
     *  R = O(n)
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException{
        HashMap<String, List<String>> addresses = new HashMap<>(); //O(n)

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter
                    (new OutputStreamWriter(new FileOutputStream(outputName), StandardCharsets.UTF_8));
            String line = reader.readLine();

            while (line != null) {
                if (!line.matches("[А-яЁё]+\\s[А-яЁё]+\\s-\\s[А-яЁё-]+\\s\\d+"))
                    throw new IOException("Wrong line format");

                String[] address = line.split("\\s-\\s");
                if (addresses.containsKey(address[1])) {
                    addresses.get(address[1]).add(address[0]);
                } else {
                    addresses.put(address[1], new ArrayList<>(Collections.singletonList(address[0])));
                }
                line = reader.readLine();
            }
            Collator ru_RUCollator = Collator.getInstance(new Locale("ru-RU"));

            Map<String, List<String>> treeMap = new TreeMap<>((o1, o2) -> {
                String[] so1 = o1.split(" ");
                String[] so2 = o2.split(" ");
                int alphabetCompare = ru_RUCollator.compare(so1[0], so2[0]);

                if (alphabetCompare == 0) return Integer.valueOf(so1[1]).compareTo(Integer.valueOf(so2[1]));
                return alphabetCompare;
            });
            treeMap.putAll(addresses); //O(n*log(n)
            for (Map.Entry<String, List<String>> entry : treeMap.entrySet()) {
                entry.getValue().sort(ru_RUCollator::compare);
                writer.write(entry.getKey()
                        .concat(" - ")
                        .concat(String.join(", ", entry.getValue())));
                writer.newLine();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     *
     * Затраты:
     *  T = O(n * log(n))
     *  R = O(n)
     */
    static public void sortTemperatures(String inputName, String outputName) {
        List<String> temperatures = new ArrayList<>(); //O(n)
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            String line = reader.readLine();

            while (line != null) {
                temperatures.add(line);

                line = reader.readLine();
            }
            temperatures.sort(Comparator.comparing(Float::valueOf)); //O(n*log(n))
            for (String temperature: temperatures) {
                writer.write(temperature);
                writer.newLine();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
