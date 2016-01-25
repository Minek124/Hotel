package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Period {
    LocalDate from;
    LocalDate to;

    public Period(String from, String to) throws Exception {
        this.from = LocalDate.parse(from, DateTimeFormatter.ISO_LOCAL_DATE);
        this.to = LocalDate.parse(to, DateTimeFormatter.ISO_LOCAL_DATE);
        if(this.from.isAfter(this.to)){
            throw new Exception("bad period");
        }
    }

    public long getDays(){
        return ChronoUnit.DAYS.between(from, to) + 1;
    }

    public String getTextFormat(){
        return from.format(DateTimeFormatter.ISO_LOCAL_DATE) + "," + from.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public long getDaysFromNow(){
        return ChronoUnit.DAYS.between(LocalDate.now(), from);
    }

    public boolean isOverlapping(Period p){
        return !from.isAfter(p.to) && !p.from.isAfter(to);
    }

    public String toString(){
        return from.format(DateTimeFormatter.ISO_LOCAL_DATE) + "," + from.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
