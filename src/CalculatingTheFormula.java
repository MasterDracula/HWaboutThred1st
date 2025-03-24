import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * задание 3.10.1 реализация асинхронного вычисления формулы с помощью CompletableFuture
 * и его функционала по комбинированию и асинхронному выполнению задач
 */

public class CalculatingTheFormula {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        // Ввод данных пользователем
        System.out.println("Введите значения a, b, c, d через пробел:");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();
        double d = scanner.nextDouble();
        scanner.close(); // Закрываем Scanner

        try {
            // Запуск асинхронного вычисления
            double result = calculateFormulaAsync(a, b, c, d);
            System.out.println("Result of the formula: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static double calculateFormulaAsync(double a, double b, double c, double d) throws ExecutionException, InterruptedException {
        // Асинхронное вычисление суммы квадратов
        CompletableFuture<Double> sumOfSquares = CompletableFuture.supplyAsync(() -> {
            delay(1);
            double result = Math.pow(a, 2) + Math.pow(b, 2);
            System.out.println("Calculating sum of squares: " + result);
            return result;
        });

        // Асинхронное вычисление натурального логарифма
        CompletableFuture<Double> logC = CompletableFuture.supplyAsync(() -> {
            delay(3);
            double result = Math.log(c);
            System.out.println("Calculating log(c): " + result);
            return result;
        });

        // Асинхронное вычисление квадратного корня
        CompletableFuture<Double> sqrtD = CompletableFuture.supplyAsync(() -> {
            delay(2);
            double result = Math.sqrt(d);
            System.out.println("Calculating sqrt(d): " + result);
            return result;
        });

        // Объединяем результаты и вычисляем итоговую формулу
        return sumOfSquares
                .thenCombine(logC, (squares, logValue) -> squares * logValue)
                .thenCombine(sqrtD, (partialResult, sqrtValue) -> partialResult / sqrtValue)
                .get(); // Ожидание результата
    }

    /**
     * Симулирует задержку выполнения для демонстрации асинхронности
     */
    private static void delay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}