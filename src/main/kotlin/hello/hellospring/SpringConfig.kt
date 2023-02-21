package hello.hellospring

import hello.hellospring.repository.JpaMemberRepository
import hello.hellospring.repository.MemberRepository
import hello.hellospring.service.MemberService
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig(
//    val em: EntityManager
    private val memberRepository: MemberRepository
) {
    @Bean
    fun memberService() = MemberService(memberRepository)
//    @Bean
//    fun memberRepository() = JpaMemberRepository(em)
}