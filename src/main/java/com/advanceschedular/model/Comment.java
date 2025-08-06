package com.advanceschedular.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
@Getter
@ToString(exclude = {"member", "schedule"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = true)
    private Schedule schedule;

    @Column(nullable = false, length = 1000)
    private String content;

    @Builder
    public Comment(Member member, Schedule schedule, String content) {
        this.member = member;
        this.schedule = schedule;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}