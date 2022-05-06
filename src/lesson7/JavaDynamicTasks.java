package lesson7;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Затраты:
     *  T = O(m * n) где m, n - длины first и second
     *  R = O(m * n)
     */
    public static String longestCommonSubSequence(String first, String second) {
        if (first.equals(second)) return first;
        int [][] matrix = new int[first.length() + 1][second.length() + 1];
        for (int i = 0; i < first.length() + 1; i++)
            matrix[i][0] = 0;
        for (int i = 1; i < second.length() + 1; i++)
            matrix[0][i] = 0;

        for (int i = 1; i < first.length() + 1; i++) {
            for (int j = 1; j < second.length() + 1; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    matrix[i][j] = matrix[i-1][j-1] + 1;
                else
                    matrix[i][j] = Math.max(matrix[i-1][j], matrix[i][j-1]);
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = first.length();
        int j = second.length();

        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                sb.append(first.charAt(i - 1));
                i--;
                j--;
            }
            else if (matrix[i-1][j] > matrix[i][j-1])
                i--;
            else
                j--;
        }
        return sb.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     * Затраты:
     * T = O(n * log n)
     * R = O(n)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.isEmpty()) return new ArrayList<>();
        if (list.size() == 1) return list;

        int [] subsequence = new int[list.size() + 1];
        int [] pos = new int[list.size() + 1];
        int [] prev = new int[list.size()];
        int len = 0;
        Arrays.fill(subsequence, Integer.MIN_VALUE);
        subsequence[0] = Integer.MAX_VALUE;

        for (int i = list.size() - 1; i >= 0; i--) {
            int j = binarySearch(subsequence, list.get(i));
            if (list.get(i) < subsequence[j - 1] && list.get(i) > subsequence[j]) {
                subsequence[j] = list.get(i);
                pos[j] = i;
                prev[i] = pos[j - 1];
                if (j > len) len = j;
            }
        }

        List<Integer> result = new ArrayList<>();
        int index = pos[len];
        for (int i = len - 1; i >= 0; i--) {
            result.add(list.get(index));
            index = prev[index];
        }
        return result;
    }

    private static int binarySearch(int [] array, int value) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            if (value < array[middle]) {
                left = middle + 1;
            }
            else {
                right = middle - 1;
            }
        }
        return left;
    }


    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }
}
