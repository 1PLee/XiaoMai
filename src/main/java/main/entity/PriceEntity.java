package main.entity;

import javax.persistence.*;

/**
 * Created by liyipeng on 2018/2/8.
 */
@Entity
@Table(name = "price", schema = "XiaoMai")
public class PriceEntity {
    private int performId;
    private Integer priceOne;
    private Integer priceTwo;
    private Integer priceThree;
    private Integer priceFour;
    private Integer priceFive;
    private Integer priceSix;

    @Id
    @Column(name = "performID")
    public int getPerformId() {
        return performId;
    }

    public void setPerformId(int performId) {
        this.performId = performId;
    }

    @Basic
    @Column(name = "priceOne")
    public Integer getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(Integer priceOne) {
        this.priceOne = priceOne;
    }

    @Basic
    @Column(name = "priceTwo")
    public Integer getPriceTwo() {
        return priceTwo;
    }

    public void setPriceTwo(Integer priceTwo) {
        this.priceTwo = priceTwo;
    }

    @Basic
    @Column(name = "priceThree")
    public Integer getPriceThree() {
        return priceThree;
    }

    public void setPriceThree(Integer priceThree) {
        this.priceThree = priceThree;
    }

    @Basic
    @Column(name = "priceFour")
    public Integer getPriceFour() {
        return priceFour;
    }

    public void setPriceFour(Integer priceFour) {
        this.priceFour = priceFour;
    }

    @Basic
    @Column(name = "priceFive")
    public Integer getPriceFive() {
        return priceFive;
    }

    public void setPriceFive(Integer priceFive) {
        this.priceFive = priceFive;
    }

    @Basic
    @Column(name = "priceSix")
    public Integer getPriceSix() {
        return priceSix;
    }

    public void setPriceSix(Integer priceSix) {
        this.priceSix = priceSix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceEntity that = (PriceEntity) o;

        if (performId != that.performId) return false;
        if (priceOne != null ? !priceOne.equals(that.priceOne) : that.priceOne != null) return false;
        if (priceTwo != null ? !priceTwo.equals(that.priceTwo) : that.priceTwo != null) return false;
        if (priceThree != null ? !priceThree.equals(that.priceThree) : that.priceThree != null) return false;
        if (priceFour != null ? !priceFour.equals(that.priceFour) : that.priceFour != null) return false;
        if (priceFive != null ? !priceFive.equals(that.priceFive) : that.priceFive != null) return false;
        if (priceSix != null ? !priceSix.equals(that.priceSix) : that.priceSix != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = performId;
        result = 31 * result + (priceOne != null ? priceOne.hashCode() : 0);
        result = 31 * result + (priceTwo != null ? priceTwo.hashCode() : 0);
        result = 31 * result + (priceThree != null ? priceThree.hashCode() : 0);
        result = 31 * result + (priceFour != null ? priceFour.hashCode() : 0);
        result = 31 * result + (priceFive != null ? priceFive.hashCode() : 0);
        result = 31 * result + (priceSix != null ? priceSix.hashCode() : 0);
        return result;
    }
}
