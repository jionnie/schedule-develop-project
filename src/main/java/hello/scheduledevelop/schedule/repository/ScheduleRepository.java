package hello.scheduledevelop.schedule.repository;

import hello.scheduledevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 일정(Schedule) 엔티티에 대한 CRUD 작업을 수행하는 리포지토리 인터페이스
 *
 * @author jiwon jung
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByMemberId(Long memberId);
}
