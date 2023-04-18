package com.repository;

import com.dto.report.EventStatisticDto;
import com.entity.Event;
import com.entity.TypeEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    public boolean existsByTypeEvent(TypeEvent typeEvent);

    public boolean existsByName(String name);

    @Query("SELECT e FROM Event e where (e.name like %:search%) order by e.createAt desc")
    public Page<Event> findAllByOrderByCreateAtDesc(Pageable pageable, @Param("search") String search);

    @Query(value = "Select e.name as nameEvent, te.name as eventType," +
            " c.name as commercialName, count(t.id) as totalTiers" +
            " from event e join event_tiers et on e.id = et.event_id" +
            " join type_evenement te on te.id = e.event_type_id" +
            " join tiers t on t.id = et.tiers_id" +
            " left join tiers_commercial tc on tc.tiers_id = t.id" +
            " left join commercial c on c.id = tc.commercial_id" +
            " group by c.id, e.id", nativeQuery = true)
    public List<EventStatisticDto> getVisitorsStatictic();


    @Query(value = "Select e.name as nameEvent, te.name as eventType," +
            " count(case et.type_etablissement when 'prive' then 1 else null end) as prive," +
            " count(case et.type_etablissement when 'public' then 1 else null end) as pub" +
            " from event e join event_tiers et on e.id = et.event_id" +
            " join type_evenement te on te.id = e.event_type_id" +
            " join tiers t on t.id = et.tiers_id" +
            " group by  e.id", nativeQuery = true)
    public List<EventStatisticDto> getTypeEtabStatistics();

    @Query(value = "select * from event e where date(e.end_event_date) >= :toDay", nativeQuery = true)
    public List<Event> getListByEndDate(@Param("toDay") String toDay);
}
