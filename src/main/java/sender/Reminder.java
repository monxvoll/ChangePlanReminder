package sender;

import java.time.*;
import java.util.concurrent.*;

import static spark.Spark.*;

public class Reminder {
    public static void main(String[] args) {
        // Arrancar servidor web para pings
        port(getHerokuAssignedPort());
        get("/health", (req, res) -> "OK");
        //Iniciar Bot
        int paymentDay = Integer.parseInt(System.getenv("PAYMENT_DAY"));  //Dia de la siguiente facturacion
        scheduleReminder(paymentDay);
    }

    private static int getHerokuAssignedPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return 4567; //Puerto Default
    }

    public static void scheduleReminder(int paymentDay) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(); // programador de tareas en un hilo separado.
        //Bloque d codigo q se ejecuta en un hilo
        Runnable task = () -> Message.sendMessage("\uD81A\uDCC2 Mañana es el cobro de Netflix, recuerda cambiar tu plan hoy");

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
