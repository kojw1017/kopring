package hello.hellospring.controller

import hello.hellospring.domain.Member
import hello.hellospring.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MemberController(private val memberService: MemberService) {
    @GetMapping("/members/new")
    fun createForm()= "member/createMember"

    @PostMapping("/members/new")
    fun create(form: MemberForm): String{
        val member = Member(form.name)
        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        model.addAttribute("members", memberService.findMembers())
        return "member/memberList"
    }
}