package main.entity;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Created by liyipeng on 2018/3/13.
 */
public class VenueIncomeID implements Serializable{

    private Integer venueId; //主键属性

    private Integer year; //主键属性

    public VenueIncomeID(){

    }

    public VenueIncomeID(int venueId, int year){
        this.venueId = venueId;
        this.year = year;

    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VenueIncomeID that = (VenueIncomeID) o;

        if (venueId != that.venueId) return false;
        if (year != that.year) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = venueId;
        result = 31 * result + (venueId != null ? venueId.hashCode() : 0);

        result = 31 * result + (year != null ? year.hashCode() : 0);

        return result;
    }
}
