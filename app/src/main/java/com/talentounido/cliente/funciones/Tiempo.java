package com.talentounido.cliente.funciones;

import com.talentounido.cliente.modelo.Horario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Tiempo {

    public static boolean inInterval (String horaInicio, String horaFinaliza){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

        try {
            Date initDate = format.parse(horaInicio);
            Date finishDate = format.parse(horaFinaliza);

            Calendar currentTime = Calendar.getInstance();
            int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
            int currentMinute = currentTime.get(Calendar.MINUTE);

            Calendar calendarInit = Calendar.getInstance();
            calendarInit.setTime(Objects.requireNonNull(initDate));

            int initHour = calendarInit.get(Calendar.HOUR_OF_DAY);
            int initMinute = calendarInit.get(Calendar.MINUTE);

            Calendar calendarFinish = Calendar.getInstance();
            calendarFinish.setTime(Objects.requireNonNull(finishDate));

            int finishHour = calendarFinish.get(Calendar.HOUR_OF_DAY);

            return (currentHour >= initHour && currentMinute > initMinute) && (currentHour < finishHour );

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Horario> selectOrder(ArrayList<Horario> horarios) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        ArrayList<Integer> horas = new ArrayList<>();

        for (Horario horario : horarios) {
            Date initDate = format.parse(horario.getInicia());

            Calendar calendarInit = Calendar.getInstance();
            calendarInit.setTime(initDate);

            horas.add(calendarInit.get(Calendar.HOUR_OF_DAY));
        }

        // Implementación del método de selección para ordenar las horas
        int longHorasArray = horas.size();
        for (int i = 0; i < longHorasArray - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < longHorasArray; j++) {
                if (horas.get(j) < horas.get(minIndex)) {
                    minIndex = j;
                }
            }
            int temp = horas.get(minIndex);
            horas.set(minIndex, horas.get(i));
            horas.set(i, temp);

            Horario tempHorario = horarios.get(minIndex);
            horarios.set(minIndex, horarios.get(i));
            horarios.set(i, tempHorario);
        }

        return horarios;
    }


}
