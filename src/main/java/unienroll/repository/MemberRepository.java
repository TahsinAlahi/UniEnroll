package unienroll.repository;

import unienroll.domain.Member;

public interface MemberRepository extends Repository<Member> {
    Member findByEmail(String email);
    boolean existsByEmail(String email);
}
