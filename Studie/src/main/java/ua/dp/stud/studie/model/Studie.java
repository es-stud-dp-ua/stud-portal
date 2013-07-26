/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
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

    /**
     * Название вуза
     */
    private String title;

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Подробнее о вузе
     */
    private String text;
    private static final int TEXT_LENGTH = 10000;

    @Column(name = "text", length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Город
     */
    private String city;

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Год
     */
    private Integer years;

    @Column(name = "years")
    public Integer getYears() {
        return years;
    }

    public void setYears(Integer year) {
        this.years = year;
    }

    /**
     * Статус
     */
    private String status;

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Уровень аккредитации
     */
    private String accreditacion;

    @Column(name = "accreditacion")
    public String getAccreditacion() {
        return accreditacion;
    }

    public void setAccreditacion(String accreditacion) {
        this.accreditacion = accreditacion;
    }

    /**
     * Форма обучения
     */
    private String formOfTraining;

    @Column(name = "formOfTraining")
    public String getFormOfTraining() {
        return formOfTraining;
    }

    public void setFormOfTraining(String formOfTraining) {
        this.formOfTraining = formOfTraining;
    }

    /**
     * Квалификационный уровень
     */
    private String qualificationLevel;

    @Column(name = "qualificationLevel")
    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    /**
     * Адрес
     */
    private String adress;

    @Column(name = "adress")
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     * Телефон
     */
    private String phone;

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Телефон приемной комиссии
     */
    private String phoneAdmissions;

    @Column(name = "phoneAdmissions")
    public String getPhoneAdmissions() {
        return phoneAdmissions;
    }

    public void setPhoneAdmissions(String phoneAdmissions) {
        this.phoneAdmissions = phoneAdmissions;
    }

    /**
     * Сайт
     */
    private String website;

    @Column(name = "website")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Наличие бесплатного обучения
     */
    private Boolean freeTrainig;

    @Column(name = "freeTrainig")
    public Boolean getFreeTrainig() {
        return freeTrainig;
    }

    public void setFreeTrainig(Boolean freeTrainig) {
        this.freeTrainig = freeTrainig;
    }

    /**
     * Наличие платного обучния
     */
    private Boolean paidTrainig;

    @Column(name = "paidTrainig")
    public Boolean getPaidTrainig() {
        return paidTrainig;
    }

    public void setPaidTrainig(Boolean paidTrainig) {
        this.paidTrainig = paidTrainig;
    }

    /**
     * Наличие военной кафедры
     */
    private Boolean militaryDepartment;

    @Column(name = "militaryDepartment")
    public Boolean getMilitaryDepartment() {
        return militaryDepartment;
    }

    public void setMilitaryDepartment(Boolean militaryDepartment) {
        this.militaryDepartment = militaryDepartment;
    }

    /**
     * Налчие общежития
     */
    private Boolean hostel;

    @Column(name = "hostel")
    public Boolean getHostel() {
        return hostel;
    }

    public void setHostel(Boolean hostel) {
        this.hostel = hostel;
    }

    /**
     * Наличие последипломного образования
     */
    private Boolean postgraduateEducation;

    @Column(name = "postgraduateEducation")
    public Boolean getPostgraduateEducation() {
        return postgraduateEducation;
    }

    public void setPostgraduateEducation(Boolean postgraduateEducation) {
        this.postgraduateEducation = postgraduateEducation;
    }

    /**
     * Наличие аспирантуры и докторантуры
     */
    private Boolean postgraduateAndDoctoralStudies;

    @Column(name = "postgraduateAndDoctoralStudies")
    public Boolean getPostgraduateAndDoctoralStudies() {
        return postgraduateAndDoctoralStudies;
    }

    public void setPostgraduateAndDoctoralStudies(Boolean postgraduateAndDoctoralStudies) {
        this.postgraduateAndDoctoralStudies = postgraduateAndDoctoralStudies;
    }

    /**
     * Наличие подготовительных курсов
     */
    private Boolean preparatoryDepartment;

    @Column(name = "preparatoryDepartment")
    public Boolean getPreparatoryDepartment() {
        return preparatoryDepartment;
    }

    public void setPreparatoryDepartment(Boolean preparatoryDepartment) {
        this.preparatoryDepartment = preparatoryDepartment;
    }

    /**
     * Кол-во студентов
     */
    private Integer countOfStudents;

    @Column(name = "countOfStudents")
    public Integer getCountOfStudents() {
        return countOfStudents;
    }

    public void setCountOfStudents(Integer countOfStudents) {
        this.countOfStudents = countOfStudents;
    }

    /**
     * Кол-во преподователей
     */
    private Integer countOfTeachers;

    @Column(name = "countOfTeachers")
    public Integer getCountOfTeachers() {
        return countOfTeachers;
    }

    public void setCountOfTeachers(Integer countOfTeachers) {
        this.countOfTeachers = countOfTeachers;
    }

    /**
     * Кол-во кандидатов наук
     */
    private Integer countOfCandidates;

    @Column(name = "countOfCandidates")
    public Integer getCountOfCandidates() {
        return countOfCandidates;
    }

    public void setCountOfCandidates(Integer countOfCandidates) {
        this.countOfCandidates = countOfCandidates;
    }

    /**
     * Кол-во докторов и проффесоров
     */
    private Integer countOfProfessors;

    @Column(name = "countOfProfessors")
    public Integer getCountOfProfessors() {
        return countOfProfessors;
    }

    public void setCountOfProfessors(Integer countOfProfessors) {
        this.countOfProfessors = countOfProfessors;
    }

    private String onGraduation;

    @Column
    public String getOnGraduation() {
        return onGraduation;
    }

    public void setOnGraduation(String onGraduation) {
        this.onGraduation = onGraduation;
    }

    /**
     * Информация абитуриентам
     */
    private String enrollees;

    @Column(name = "enrollees", length = TEXT_LENGTH)
    public String getEnrollees() {
        return enrollees;
    }

    public void setEnrollees(String enrollees) {
        this.enrollees = enrollees;
    }

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

    /**
     * конструктор с параметрами
     *
     * @param topic
     * @param text
     * @param city
     * @param status
     * @param year
     * @param accreditacion
     * @param formOfTraining
     * @param qualificationLevel
     * @param adress
     * @param phone
     * @param phoneAdmissions
     * @param website
     * @param freeTrainig
     * @param paidTrainig
     * @param militaryDepartment
     * @param hostel
     * @param postgraduateEducation
     * @param postgraduateAndDoctoralStudies
     * @param preparatoryDepartment
     * @param countOfStudents
     * @param countOfTeachers
     * @param countOfCandidates
     * @param countOfProfessors
     * @param enrollees
     */
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
