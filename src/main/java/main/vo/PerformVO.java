package main.vo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
public class PerformVO {
    public int performID;
    public String name;
    public String time;
    public String priceMin;
    public String type;
    public String venue;
    public List<Integer> price;
    public List<Integer>  seat;
    public String description;
    public PerformIncomeVO performIncomeVO;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPerformID() {
        return performID;
    }

    public void setPerformID(int performID) {
        this.performID = performID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public List<Integer> getPrice() {
        return price;
    }

    public void setPrice(List<Integer> price) {
        this.price = price;
    }

    public List<Integer> getSeat() {
        return seat;
    }

    public void setSeat(List<Integer> seat) {
        this.seat = seat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PerformIncomeVO getPerformIncomeVO() {
        return performIncomeVO;
    }

    public void setPerformIncomeVO(PerformIncomeVO performIncomeVO) {
        this.performIncomeVO = performIncomeVO;
    }

}
