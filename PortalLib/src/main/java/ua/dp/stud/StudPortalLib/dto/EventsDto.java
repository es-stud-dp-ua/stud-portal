package ua.dp.stud.StudPortalLib.dto;

import java.util.Date;

public class EventsDto extends CommonDto{
   private Date startDate;
    private  Date endDate;
   private String desc;
    private String location;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    EventsDto(){

    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public EventsDto(String imgPath, String name, Integer id, Date startDate, Date endDate, String desc, String location) {
        super(imgPath, name, id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.desc = desc;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof EventsDto)){
            return false;
        }
        if (!super.equals(o)){
            return false;
        }

        EventsDto eventsDto = (EventsDto) o;

        if (desc != null ? !desc.equals(eventsDto.desc) : eventsDto.desc != null){
            return false;
        }
        if (endDate != null ? !endDate.equals(eventsDto.endDate) : eventsDto.endDate != null) {
            return false;
        }
        if (location != null ? !location.equals(eventsDto.location) : eventsDto.location != null){
            return false;
        }
        if (startDate != null ? !startDate.equals(eventsDto.startDate) : eventsDto.startDate != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
