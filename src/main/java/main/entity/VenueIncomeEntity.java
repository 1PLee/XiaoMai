package main.entity;

import javax.persistence.*;

/**
 * Created by liyipeng on 2018/3/13.
 */
@Entity
@Table(name = "venueIncome", schema = "XiaoMai")
@IdClass(VenueIncomeID.class)
public class VenueIncomeEntity {
    private Integer venueId;
    private Integer year;
    private Double income;

    @Id
    @Column(name = "venueId")
    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    @Id
    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "income")
    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VenueIncomeEntity that = (VenueIncomeEntity) o;

        if (venueId != null ? !venueId.equals(that.venueId) : that.venueId != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = venueId != null ? venueId.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        return result;
    }
}
