package hello.scheduledevelop.domain.schedule.entity;

import hello.scheduledevelop.global.common.entity.BaseEntity;
import hello.scheduledevelop.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 일정 정보를 저장하는 엔티티
 * 고유 id, 비밀번호, 일정 제목, 일정 내용 등의 필드를 포함한다.
 *
 * @author jiwon jung
 */
@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    private String content;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Schedule(String title, String content, LocalDate startDate, LocalDate endDate, Member member) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;
    }

    public void updateSchedule(String title, String content, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
