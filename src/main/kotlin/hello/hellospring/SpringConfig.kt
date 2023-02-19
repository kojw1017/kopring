package hello.hellospring

import hello.hellospring.repository.MemoryMemberRepository
import hello.hellospring.service.MemberService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig {
    @Bean
    fun memberService() = MemberService(memberRepository())
    @Bean
    fun memberRepository() = MemoryMemberRepository()
}