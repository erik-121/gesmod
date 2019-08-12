package alfatecsistemas.tdgov.gestionsede.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category {

   @Id
   //@GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "ID")
   private long id;

   @Column(name = "NAME")
   private String name;

   @Column(name = "IMAGE")
   private Blob image;

   //Getters and setters 
   //....
   //....
}