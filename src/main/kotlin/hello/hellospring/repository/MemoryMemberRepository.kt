package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.stereotype.Repository

class MemoryMemberRepository: MemberRepository{
    companion object{
        val store = hashMapOf<Long, Member>()
        var sequence = 0L
    }
    override fun save(member: Member): Member {
        member.id = ++sequence
        store[member.id] = member
        return member
    }
    override fun findById(id: Long): Member? = store[id]
    override fun findByName(name: String): Member? = store.values.find { it.name == name }
    override fun findAll() = store.values.toList()
    fun clearStore(){
        store.clear()
    }
}