package sender;

import io.javalin.Javalin;

import java.time.*;
import java.util.concurrent.*;


public class Reminder {
    public static void main(String[] args) {
        // Inicia Javalin para el pingeo
        Javalin.create().start(Integer.parseInt(System.getenv().getOrDefault("PORT", "8080")))
                .get("/health", ctx -> ctx.status(200).result("OK"));

        //Inicia el bot
        int paymentDay = Integer.parseInt(System.getenv("PAYMENT_DAY"));  //Dia de la siguiente facturacion
        scheduleReminder(paymentDay);
    }

    public static void scheduleReminder(int paymentDay) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(); // programador de tareas en un hilo separado.
        //Bloque d codigo q se ejecuta en un hilo
        Runnable task = () -> Message.sendMessage("\uD81A\uDCC2 Mañana es el cobro :0");

        long initialDelay =  calculateDelayInHours(paymentDay); // Horas a esperar desde ahora hasta el siguiente cobro d Netflix
        long period = 30L * 24L; // horas entre recordatorio

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.HOURS); //Config del scheduler
    }

    public static long calculateDelayInHours(int diaCobro) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate nextDate = LocalDate.of(now.getYear(), now.getMonth(), diaCobro)
                .minusDays(1); //Calculo de la fecha actual menos un dia

        //Si el dia de cobro es el siguiente mes sumamos un mes
        if (nextDate.isBefore(LocalDate.now())) {
            nextDate = nextDate.plusMonths(1);
        }

        Duration duration = Duration.between(now, nextDate.atTime(10, 0)); // Horas entre hoy y ese dia a las 10am
        return duration.toHours(); //Retorno en horas del calculo
    }
}