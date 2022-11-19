package jpabook.jpashop

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository(

    @PersistenceContext // spring boot가 PersistenceContext 어노테이션을 보고 entityManager를 주입해줌
    private val em: EntityManager
) {
    fun save(member: Member): Long {
        em.persist(member)
        return member.id!!
    }

    fun find(id: Long): Member {
        return em.find(Member::class.java, id)
    }
}
