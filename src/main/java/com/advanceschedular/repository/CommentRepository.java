package com.advanceschedular.repository;

import com.advanceschedular.model.Comment;
import com.advanceschedular.model.Member;
import com.advanceschedular.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMember(Member member);
    List<Comment> findBySchedule(Schedule schedule);
}
