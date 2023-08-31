package ru.practicum.ewm.stats_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.common_dto.ViewStatDto;
import ru.practicum.ewm.stats_server.model.HitModel;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<HitModel, Long> {

    @Query(value = "SELECT new ru.practicum.ewm.common_dto.ViewStatDto(" +
            "stats.app, stats.uri, COUNT(stats.ip) AS hits) " +
            "FROM HitModel stats " +
            "WHERE stats.timestamp BETWEEN :start AND :end " +
            "GROUP BY stats.app, stats.uri " +
            "ORDER BY hits DESC")
    List<ViewStatDto> findStatsBetweenStartAndEnd(@Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end);

    @Query(value = "SELECT new ru.practicum.ewm.common_dto.ViewStatDto(" +
            "stats.app, stats.uri, COUNT(DISTINCT stats.ip) AS hits) " +
            "FROM HitModel stats " +
            "WHERE stats.timestamp BETWEEN :start AND :end " +
            "GROUP BY stats.app, stats.uri " +
            "ORDER BY hits DESC")
    List<ViewStatDto> findStatsBetweenStartAndEndAndUniqueIp(@Param("start") LocalDateTime start,
                                                            @Param("end") LocalDateTime end);

    @Query(value = "SELECT new ru.practicum.ewm.common_dto.ViewStatDto(" +
            "stats.app, stats.uri, COUNT(stats.ip) AS hits) " +
            "FROM HitModel stats " +
            "WHERE stats.timestamp BETWEEN :start AND :end " +
            "AND uri IN ( :uris ) " +
            "GROUP BY stats.app, stats.uri " +
            "ORDER BY hits DESC")
    List<ViewStatDto> findStatsBetweenStartAndEndByUri(@Param("start") LocalDateTime start,
                                                      @Param("end") LocalDateTime end,
                                                      @Param("uris") List<String> uris);

    @Query(value = "SELECT new ru.practicum.ewm.common_dto.ViewStatDto(" +
            "stats.app, stats.uri, COUNT(DISTINCT stats.ip) AS hits) " +
            "FROM HitModel stats " +
            "WHERE stats.timestamp BETWEEN :start AND :end " +
            "AND uri IN ( :uris ) " +
            "GROUP BY stats.app, stats.uri " +
            "ORDER BY hits DESC")
    List<ViewStatDto> findStatsBetweenStartAndEndByUriAndUniqueIp(@Param("start") LocalDateTime start,
                                                                 @Param("end") LocalDateTime end,
                                                                 @Param("uris") List<String> uris);

}