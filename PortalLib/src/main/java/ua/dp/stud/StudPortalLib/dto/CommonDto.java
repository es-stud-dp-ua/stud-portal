package ua.dp.stud.StudPortalLib.dto;



public class CommonDto {
    private  Integer id;
    private String name;
    private String imgPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonDto(String imgPath, String name, Integer id) {
        this.imgPath = imgPath;
        this.name = name;
        this.id = id;
    }

    public CommonDto()
    {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonDto)) return false;

        CommonDto commonDto = (CommonDto) o;

        if (id != null ? !id.equals(commonDto.id) : commonDto.id != null) return false;
        if (imgPath != null ? !imgPath.equals(commonDto.imgPath) : commonDto.imgPath != null) return false;
        if (name != null ? !name.equals(commonDto.name) : commonDto.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imgPath != null ? imgPath.hashCode() : 0);
        return result;
    }
}
