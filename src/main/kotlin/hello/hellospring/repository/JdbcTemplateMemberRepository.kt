package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import javax.sql.DataSource


class JdbcTemplateMemberRepository(dataSource: DataSource): MemberRepository{
    private val jdbcTemplate = JdbcTemplate(dataSource)
    override fun save(member: Member): Member {
        val jdbcInsert = SimpleJdbcInsert(jdbcTemplate)
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id")

        val parameters = hashMapOf<String, Any>()
        parameters["name"] = member.name

        val key = jdbcInsert.executeAndReturnKey(MapSqlParameterSource(parameters))
        member.id = key.toLong()
        return member
    }
    override fun findById(id: Long): Member? {
        val result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id)
        return result.find { it.id == id }
    }
    override fun findByName(name: String): Member? {
        val result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name)
        return result.find { it.name == name }
    }
    override fun findAll(): MutableList<Member> = jdbcTemplate.query("select * from member", memberRowMapper())

    private fun memberRowMapper(): RowMapper<Member>{
        return RowMapper { rs, _ ->
            Member(rs.getLong("id"), rs.getString("name"))
        }
    }
}

