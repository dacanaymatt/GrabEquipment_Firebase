package Model;

/**
 * Created by Matthew on 4/7/2017.
 */
public class StudentRequestDetail {

    private String status;
    private String dateBorrowed;
    private String timeBorrowed;
    private String dateReturned;
    private String equipment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(String dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getTimeBorrowed() {
        return timeBorrowed;
    }

    public void setTimeBorrowed(String timeBorrowed) {
        this.timeBorrowed = timeBorrowed;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }
}
