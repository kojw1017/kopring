package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import javax.sql.DataSource


class JdbcTemplateMemberRepository(private val dataSource: DataSource): MemberRepository{
    val jdbcTemplate = JdbcTemplate(dataSource)
    override fun save(member: Member): Member {
        TODO("Not yet implemented")
    }
    override fun findById(id: Long): Member? {
        val result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper())
        result.fin
        TODO("Not yet implemented")
    }
    override fun findByName(name: String): Member? {
        TODO("Not yet implemented")
    }
    override fun findAll(): List<Member> {
        TODO("Not yet implemented")
    }

    private fun memberRowMapper(): RowMapper<Member>{
        return RowMapper { rs, _ ->
            Member(rs.getString("name"), rs.getLong("id"))
        }
    }
}

