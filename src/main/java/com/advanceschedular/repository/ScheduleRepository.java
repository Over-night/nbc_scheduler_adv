package com.advanceschedular.repository;

import com.advanceschedular.model.Member;
import com.advanceschedular.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByMember(Member member);
    List<Schedule> findByTitle(Schedule schedule);

    Page<Schedule> findAllByDeletedAtIsNull(Pageable pageable);
}