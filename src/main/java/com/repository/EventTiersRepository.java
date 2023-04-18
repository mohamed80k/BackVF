package com.repository;

import com.dto.TiersOfEvent;
import com.entity.EventTiers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventTiersRepository extends JpaRepository<EventTiers, Integer> {


    @Query(value = "select * from event_tiers e where e.event_id = :id", nativeQuery = true)
    List<EventTiers> findByEventId(@Param("id") int id);

    @Query(value = "select * from event_tiers et where et.tiers_id = :id", nativeQuery = true)
    EventTiers findByTiersId(@Param("id") int id);

    @Query(value= "delete from event_tiers et where et.tiers_id = :id", nativeQuery = true)
    public void deleteEventTiers(@Param("id") int id);

    @Query("select et from EventTiers et where et.event.id = :id " +
            " and et.tiers.createAt between :startDate and :endDate" +
            " order by et.tiers.createAt desc")
    List<EventTiers> findByEventIdAndDate(@Param("id") int id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select et from EventTiers et where (et.tiers.name like %:search%) " +
            " order by et.tiers.createAt desc")
    Page<EventTiers> getPage(Pageable pageable, @Param("search") String search);
}
