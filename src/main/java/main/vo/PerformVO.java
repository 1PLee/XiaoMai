package main.vo;

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
}
