package hello.hellospring.service

import hello.hellospring.domain.Member
import hello.hellospring.repository.MemberRepository
import java.lang.IllegalStateException

@org.springframework.transaction.annotation.Transactional
class MemberService(private val memberRepository:MemberRepository) {
    //회원 가입
    fun join(member: Member): Long?{
        //중복 제거
        valiName(member)
        return member.id
    }

    private fun valiName(member: Member) {
        memberRepository.findByName(member.name)?.let {
            throw IllegalStateException("중복된 이름")
        } ?: memberRepository.save(member)
    }

    fun findMembers() = memberRepository.findAll()
    fun findOne(id: Long) = memberRepository.findById(id)
}