package ru.practicum.ewm.stats_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.stats_server.model.HitModel;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<HitModel, Long> {
    @Query("select distinct s.uri " +
            "from HitModel as s")
    List<String> getDistinctUri();

    @Query(value = "select t.uri " +
            "from (select distinct on (s.ip) s.uri from stats as s " +
            "where s.uri in (?1) " +
            "and s.timestamp > ?2 and s.timestamp < ?3) as t",
            nativeQuery = true)
    List<String> findUriByUniqueIp(List<String> uri, LocalDateTime from, LocalDateTime to);

    @Query("select s.uri " +
            "from HitModel as s " +
            "where s.uri in (?1) " +
            "and s.timestamp > ?2 and s.timestamp < ?3")
    List<String> getUrisByUri(List<String> uris, LocalDateTime from, LocalDateTime to);
}