package hello.hellospring.repository

import hello.hellospring.domain.Member
import jakarta.persistence.EntityManager


class JpaMemberRepository(private val em: EntityManager): MemberRepository{
    override fun save(member: Member): Member {
        em.persist(member)
        return member
    }
    override fun findById(id: Long): Member? = em.find(Member::class.java, id)

    override fun findByName(name: String): Member? {
        val result = em.createQuery("select m from Member m where m.name=: name", Member::class.java)
            .setParameter("name", name)
            .resultList
        return result.find { it.name == name }
    }
    override fun findAll(): List<Member> = em.createQuery("select m from Member m", Member::class.java).resultList
}