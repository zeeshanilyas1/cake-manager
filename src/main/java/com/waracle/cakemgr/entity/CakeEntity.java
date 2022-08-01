package com.waracle.cakemgr.entity;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cake")
@NoArgsConstructor
@Data
public class CakeEntity implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cakeId;

    private String cakeDetail;

    private String description;

    private String image;
    
    private BigDecimal price;
    
    public CakeEntity(final String cakeDetails, final String description, final String image) {
    	this.cakeDetail = cakeDetails;
    	this.description = description;
    	this.image = image;
    }	
}