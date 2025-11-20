package hello.scheduledevelop.domain.member.repository;

import hello.scheduledevelop.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 유저(Member) 엔티티에 대한 CRUD 작업을 수행하는 리포지토리 인터페이스
 *
 * @author jiwon jung
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
}
