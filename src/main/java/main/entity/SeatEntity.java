package main.entity;

import javax.persistence.*;

/**
 * Created by liyipeng on 2018/2/26.
 */
@Entity
@Table(name = "seat", schema = "XiaoMai")
public class SeatEntity {
    private int performId;
    private Integer seatOne;
    private Integer seatTwo;
    private Integer seatThree;
    private Integer seatFour;
    private Integer seatFive;
    private Integer seatSix;

    @Id
    @Column(name = "performID")
    public int getPerformId() {
        return performId;
    }

    public void setPerformId(int performId) {
        this.performId = performId;
    }

    @Basic
    @Column(name = "seatOne")
    public Integer getSeatOne() {
        return seatOne;
    }

    public void setSeatOne(Integer seatOne) {
        this.seatOne = seatOne;
    }

    @Basic
    @Column(name = "seatTwo")
    public Integer getSeatTwo() {
        return seatTwo;
    }

    public void setSeatTwo(Integer seatTwo) {
        this.seatTwo = seatTwo;
    }

    @Basic
    @Column(name = "seatThree")
    public Integer getSeatThree() {
        return seatThree;
    }

    public void setSeatThree(Integer seatThree) {
        this.seatThree = seatThree;
    }

    @Basic
    @Column(name = "seatFour")
    public Integer getSeatFour() {
        return seatFour;
    }

    public void setSeatFour(Integer seatFour) {
        this.seatFour = seatFour;
    }

    @Basic
    @Column(name = "seatFive")
    public Integer getSeatFive() {
        return seatFive;
    }

    public void setSeatFive(Integer seatFive) {
        this.seatFive = seatFive;
    }

    @Basic
    @Column(name = "seatSix")
    public Integer getSeatSix() {
        return seatSix;
    }

    public void setSeatSix(Integer seatSix) {
        this.seatSix = seatSix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntity that = (SeatEntity) o;

        if (performId != that.performId) return false;
        if (seatOne != null ? !seatOne.equals(that.seatOne) : that.seatOne != null) return false;
        if (seatTwo != null ? !seatTwo.equals(that.seatTwo) : that.seatTwo != null) return false;
        if (seatThree != null ? !seatThree.equals(that.seatThree) : that.seatThree != null) return false;
        if (seatFour != null ? !seatFour.equals(that.seatFour) : that.seatFour != null) return false;
        if (seatFive != null ? !seatFive.equals(that.seatFive) : that.seatFive != null) return false;
        if (seatSix != null ? !seatSix.equals(that.seatSix) : that.seatSix != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = performId;
        result = 31 * result + (seatOne != null ? seatOne.hashCode() : 0);
        result = 31 * result + (seatTwo != null ? seatTwo.hashCode() : 0);
        result = 31 * result + (seatThree != null ? seatThree.hashCode() : 0);
        result = 31 * result + (seatFour != null ? seatFour.hashCode() : 0);
        result = 31 * result + (seatFive != null ? seatFive.hashCode() : 0);
        result = 31 * result + (seatSix != null ? seatSix.hashCode() : 0);
        return result;
    }
}
