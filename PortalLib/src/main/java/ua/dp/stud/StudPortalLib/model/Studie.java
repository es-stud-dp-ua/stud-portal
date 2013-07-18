/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ольга
 */
@Entity
@Table(name = "studies_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Studie extends BaseImagesSupport implements Serializable {

    @Column(name = "title")
    private String title;  //название вуза
    @Column(name = "text", length = TEXT_LENGTH)
    private String text;   // подробнее о вузе
    @Column(name = "city")
    private String city; //город
    @Column(name = "years")
    private Integer years; //год
    @Column(name = "status")
    private String status; //статус
    @Column(name = "accreditacion")
    private String accreditacion; //уровень аккредитации
    @Column(name = "formOfTraining")
    private String formOfTraining; //форма обучения
    @Column(name = "qualificationLevel")
    private String qualificationLevel; //квалификационный уровень
    @Column(name = "adress")
    private String adress; //адрес
    @Column(name = "phone")
    private String phone; //телефон
    @Column(name = "phoneAdmissions")
    private String phoneAdmissions; //телефон приемной коммисии
    @Column(name = "website")
    private String website; //сайт
    @Column(name = "freeTrainig")
    private Boolean freeTrainig; //наличие бесплатного очубения
    @Column(name = "paidTrainig")
    private Boolean paidTrainig; //наличие платного обучения
    @Column(name = "militaryDepartment")
    private Boolean militaryDepartment; //наличие военной кафедры
    @Column(name = "hostel")
    private Boolean hostel; //наличие общежития
    @Column(name = "postgraduateEducation")
    private Boolean postgraduateEducation; //наличие последипломного образования
    @Column(name = "postgraduateAndDoctoralStudies")
    private Boolean postgraduateAndDoctoralStudies; //наличие аспирантуры и докторантуры
    @Column(name = "preparatoryDepartment")
    private Boolean preparatoryDepartment; //надичие подготовительных курсов
    @Column(name = "countOfStudents")
    private Integer countOfStudents; //количество студентов
    @Column(name = "countOfTeachers")
    private Integer countOfTeachers;//количество преподавателей
    @Column(name = "countOfCandidates")
    private Integer countOfCandidates;//количество кандидатов наук
    @Column(name = "countOfProfessors")
    private Integer countOfProfessors;//количество докторов и проффесоров
    @Column(name = "enrollees")
    private String enrollees;//информация аббитуриентам
    private static final int TEXT_LENGTH = 10000;

    private List<Faculties> faculties;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "base", fetch = FetchType.LAZY)
    public List<Faculties> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculties> faculties) {
        this.faculties = faculties;
    }

    public Studie() {
    }

    public Studie(String topic, String text, String city, String status, Integer year, String accreditacion,
            String formOfTraining, String qualificationLevel, String adress, String phone, String phoneAdmissions,
            String website, Boolean freeTrainig, Boolean paidTrainig, Boolean militaryDepartment, Boolean hostel,
            Boolean postgraduateEducation, Boolean postgraduateAndDoctoralStudies, Boolean preparatoryDepartment,
            Integer countOfStudents, Integer countOfTeachers, Integer countOfCandidates, Integer countOfProfessors,
            String enrollees) {
        this.title = topic;
        this.text = text;
        this.city = city;
        this.years = year;
        this.status = status;
        this.accreditacion = accreditacion;
        this.formOfTraining = formOfTraining;
        this.qualificationLevel = qualificationLevel;
        this.adress = adress;
        this.phone = phone;
        this.phoneAdmissions = phoneAdmissions;
        this.website = website;
        this.freeTrainig = freeTrainig;
        this.paidTrainig = paidTrainig;
        this.militaryDepartment = militaryDepartment;
        this.hostel = hostel;
        this.postgraduateEducation = postgraduateEducation;
        this.postgraduateAndDoctoralStudies = postgraduateAndDoctoralStudies;
        this.preparatoryDepartment = preparatoryDepartment;
        this.countOfStudents = countOfStudents;
        this.countOfTeachers = countOfTeachers;
        this.countOfCandidates = countOfCandidates;
        this.countOfProfessors = countOfProfessors;
        this.enrollees = enrollees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer year) {
        this.years = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccreditacion() {
        return accreditacion;
    }

    public void setAccreditacion(String accreditacion) {
        this.accreditacion = accreditacion;
    }

    public String getFormOfTraining() {
        return formOfTraining;
    }

    public void setFormOfTraining(String formOfTraining) {
        this.formOfTraining = formOfTraining;
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneAdmissions() {
        return phoneAdmissions;
    }

    public void setPhoneAdmissions(String phoneAdmissions) {
        this.phoneAdmissions = phoneAdmissions;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getFreeTrainig() {
        return freeTrainig;
    }

    public void setFreeTrainig(Boolean freeTrainig) {
        this.freeTrainig = freeTrainig;
    }

    public Boolean getPaidTrainig() {
        return paidTrainig;
    }

    public void setPaidTrainig(Boolean paidTrainig) {
        this.paidTrainig = paidTrainig;
    }

    public Boolean getMilitaryDepartment() {
        return militaryDepartment;
    }

    public void setMilitaryDepartment(Boolean militaryDepartment) {
        this.militaryDepartment = militaryDepartment;
    }
    public Boolean getHostel() {
        return hostel;
    }

    public void setHostel(Boolean hostel) {
        this.hostel = hostel;
    }

    public Boolean getPostgraduateEducation() {
        return postgraduateEducation;
    }

    public void setPostgraduateEducation(Boolean postgraduateEducation) {
        this.postgraduateEducation = postgraduateEducation;
    }

    public Boolean getPostgraduateAndDoctoralStudies() {
        return postgraduateAndDoctoralStudies;
    }

    public void setPostgraduateAndDoctoralStudies(Boolean postgraduateAndDoctoralStudies) {
        this.postgraduateAndDoctoralStudies = postgraduateAndDoctoralStudies;
    }

    public Boolean getPreparatoryDepartment() {
        return preparatoryDepartment;
    }

    public void setPreparatoryDepartment(Boolean preparatoryDepartment) {
        this.preparatoryDepartment = preparatoryDepartment;
    }

    public Integer getCountOfStudents() {
        return countOfStudents;
    }

    public void setCountOfStudents(Integer countOfStudents) {
        this.countOfStudents = countOfStudents;
    }

    public Integer getCountOfTeachers() {
        return countOfTeachers;
    }

    public void setCountOfTeachers(Integer countOfTeachers) {
        this.countOfTeachers = countOfTeachers;
    }

    public Integer getCountOfCandidates() {
        return countOfCandidates;
    }

    public void setCountOfCandidates(Integer countOfCandidates) {
        this.countOfCandidates = countOfCandidates;
    }

    public Integer getCountOfProfessors() {
        return countOfProfessors;
    }

    public void setCountOfProfessors(Integer countOfProfessors) {
        this.countOfProfessors = countOfProfessors;
    }

    public String getEnrollees() {
        return enrollees;
    }

    public void setEnrollees(String enrollees) {
        this.enrollees = enrollees;
    }
    private static final int START_HASH = 55;
    private static final int MULT_HASH = 77;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(START_HASH, MULT_HASH).append(this.title).append(this.text).append(this.adress).
                append(this.city).append(this.status).append(this.years).append(this.accreditacion).append(this.formOfTraining).
                append(this.qualificationLevel).append(this.phone).append(this.phoneAdmissions).append(this.website).append(this.freeTrainig).
                append(this.paidTrainig).append(this.militaryDepartment).append(this.hostel).append(this.postgraduateEducation).append(this.postgraduateAndDoctoralStudies).
                append(this.preparatoryDepartment).append(this.countOfStudents).append(this.countOfTeachers).append(this.countOfCandidates).
                append(this.countOfProfessors).append(this.enrollees).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Studie)) {
            return false;
        }
        final Studie other = (Studie) obj;
        return new EqualsBuilder().append(other.adress, adress).append(other.text, text).append(other.title, title).
                append(other.city, city).append(other.status, status).append(other.years, years).
                append(other.accreditacion, accreditacion).append(other.formOfTraining, formOfTraining).
                append(other.qualificationLevel, qualificationLevel).append(other.phone, phone).
                append(other.phoneAdmissions, phoneAdmissions).append(other.website, website).
                append(other.freeTrainig, freeTrainig).append(other.paidTrainig, paidTrainig).
                append(other.militaryDepartment, militaryDepartment).append(other.hostel, hostel).
                append(other.postgraduateEducation, postgraduateEducation).
                append(other.postgraduateAndDoctoralStudies, postgraduateAndDoctoralStudies).
                append(other.preparatoryDepartment, preparatoryDepartment).append(other.countOfStudents, countOfStudents).
                append(other.countOfTeachers, countOfTeachers).append(other.countOfCandidates, countOfCandidates).
                append(other.countOfProfessors, countOfProfessors).append(other.enrollees, enrollees).isEquals();
    }

    @Override
    public String toString() {
        return new StringBuffer().append("Studie[").append("adress=").append(adress)
                .append(", text=").append(text).append(", title=").append(title).append(']').toString();
    }
}
