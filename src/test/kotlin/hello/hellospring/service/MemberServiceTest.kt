package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemoryMemberRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import java.lang.IllegalStateException

class MemberServiceTest {
    private val memberService = MemberService(MemoryMemberRepository())
    @Test
    fun join() {
        val member = Member("spring")
        val saveId = memberService.join(member)
        val findOne = memberService.findOne(saveId)
        Assertions.assertEquals(member.name, findOne?.name)
    }

    @Test
    fun 중복_회원_예외(){
        val member = Member("spring")
        memberService.join(member)
        assertThrows<IllegalStateException>("엥?") { memberService.join(member) }
    }

    @Test
    fun findMembers() {

    }

    @Test
    fun findOne() {

    }
}