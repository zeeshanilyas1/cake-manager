package com.waracle.cakemgr.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waracle.cakemgr.entity.CakeEntity;

public interface CakeEntityRepository extends JpaRepository<CakeEntity, Long> {

}
