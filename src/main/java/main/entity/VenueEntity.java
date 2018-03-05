package main.entity;

import javax.persistence.*;

/**
 * Created by liyipeng on 2018/3/5.
 */
@Entity
@Table(name = "venue", schema = "XiaoMai")
public class VenueEntity {
    private int venueId;
    private String description;
    private String venue;
    private String location;
    private int type; //0 代表未审核 1代表已审核 2代表提交修改申请的
    private int capacity; //场馆容纳量

    private String mail;
    private int code;

    @Id
    @Column(name = "venueID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "venue")
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "capacity")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VenueEntity that = (VenueEntity) o;

        if (venueId != that.venueId) return false;
        if (type != that.type) return false;
        if (capacity != that.capacity) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (venue != null ? !venue.equals(that.venue) : that.venue != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = venueId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (venue != null ? venue.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + capacity;
        return result;
    }

    @Basic
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
