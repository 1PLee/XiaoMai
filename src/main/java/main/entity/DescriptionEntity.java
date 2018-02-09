package main.entity;

import javax.persistence.*;

/**
 * Created by liyipeng on 2018/2/8.
 */
@Entity
@Table(name = "description", schema = "XiaoMai")
public class DescriptionEntity {
    private int performId;
    private String description;

    @Id
    @Column(name = "performID")
    public int getPerformId() {
        return performId;
    }

    public void setPerformId(int performId) {
        this.performId = performId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionEntity that = (DescriptionEntity) o;

        if (performId != that.performId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = performId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
