package ua.dp.stud.studie.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;

@Entity
@Table(name = "online_course_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class OnlineCourse extends BaseImagesSupport
{
	
}