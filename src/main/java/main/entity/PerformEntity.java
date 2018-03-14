package main.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liyipeng on 2018/2/8.
 */

@Entity
@Table(name = "perform", schema = "XiaoMai")
public class PerformEntity {
    private int id;
    private String name;
    private String time;
    private String address;
    //private Integer addressId;
    private String priceMin;
    private Integer type; // 1演唱会 2音乐会 3舞蹈 4话剧 5体育比赛
    private Integer state; // 1代表演出未开始 2代表演出已经结束 3代表演出已经结算完毕(网站和场馆)

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

/*    @Basic
    @Column(name = "addressID")
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }*/

    @Basic
    @Column(name = "priceMin")
    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "state")
    public Integer getState(){
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerformEntity that = (PerformEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        //if (addressId != null ? !addressId.equals(that.addressId) : that.addressId != null) return false;
        if (priceMin != null ? !priceMin.equals(that.priceMin) : that.priceMin != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        //result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (priceMin != null ? priceMin.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
