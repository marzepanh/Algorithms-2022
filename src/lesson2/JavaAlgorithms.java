package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;
import java.util.Arrays;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     * Затраты:
     *  T = O(n^2)
     *  R = O(n^2)
     */
    static public String longestCommonSubstring(String first, String second) {
        int[][] matrix = new int[second.length()][first.length()]; //O(n^2)
        int[] firstIndex = {0};
        int maxValue = -1;
        for (int col = 0; col < first.length(); col++) { //O(n^2)
            for (int row = 0; row < second.length(); row++) {
                if (first.charAt(col) == second.charAt(row)) {
                    if (row < 1 || col < 1)
                        matrix[row][col] = 1;
                    else
                        matrix[row][col] = matrix[row-1][col-1] + 1;
                    if (matrix[row][col] > maxValue) {
                        maxValue = matrix[row][col];
                        firstIndex[0] = col;
                    }
                } else
                    matrix[row][col] = 0;
            }
        }
        if (maxValue == -1) return "";
        return first.substring(firstIndex[0] - maxValue + 1, firstIndex[0] + 1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     *  Затраты:
     *  T = O(n*log(log n))
     *  R = O(n)
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) return 0;

        boolean[] numIsPrime = new boolean[limit + 1]; //O(n)
        Arrays.fill(numIsPrime, true);
        numIsPrime[0] = false;
        numIsPrime[1] = false;
        for (int i = 2; i*i <= limit; i++) { // Sieve of Eratosthenes, O(n*log(log n))
            if (numIsPrime[i]) {
                for (int p = i*i; p <= limit; p += i) {
                    numIsPrime[p] = false;
                }
            }
        }
        int result = 0;
        for (boolean b : numIsPrime) {
            if (b) result++;
        }
        return result;
    }
}
