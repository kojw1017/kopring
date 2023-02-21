package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MemoryMemberRepositoryTest {
    private val repository = MemoryMemberRepository()

    @AfterEach
    fun afterEach(){
        repository.clearStore()
    }

    @Test
    fun save(){
        val member = Member(name = "spring")
        repository.save(member)
        val result = member.id?.let { repository.findById(it) }
        Assertions.assertEquals(member, result)
    }

    @Test
    fun findByName(){
        val member = Member(name = "spring")
        repository.save(member)

        val member2 = Member(name = "spring")
        repository.save(member2)
        val result = repository.findByName("spring")
        Assertions.assertEquals(result, member)
    }
}