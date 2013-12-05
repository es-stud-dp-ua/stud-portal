package ua.dp.stud.StudPortalLib.dto;

import java.util.Date;

public class EventsDto extends CommonDto{
   private Date startDate;
   private String desc;

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

    EventsDto(){}

    public EventsDto(String imgPath, String name, Integer id, Date startDate, String desc) {
        super(imgPath, name, id);
        this.startDate = startDate;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventsDto)) return false;
        if (!super.equals(o)) return false;

        EventsDto eventsDto = (EventsDto) o;

        if (desc != null ? !desc.equals(eventsDto.desc) : eventsDto.desc != null) return false;
        if (startDate != null ? !startDate.equals(eventsDto.startDate) : eventsDto.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
