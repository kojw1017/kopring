package hello.hellospring.repository

import hello.hellospring.domain.Member
import org.springframework.jdbc.datasource.DataSourceUtils
import java.sql.*
import javax.sql.DataSource


class JdbcMemberRepository(private val dataSource: DataSource): MemberRepository{
    override fun save(member: Member): Member {
        //language=sql
        val sql = "insert into member(name) values(?)"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        return try {
            conn = getConnection()
            pstmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
            pstmt.setString(1, member.name)
            pstmt.executeUpdate()
            rs = pstmt.generatedKeys
            if (rs.next()) {
                member.id = rs.getLong(1)
            } else {
                throw SQLException("저장 실패")
            }
            member
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }
    override fun findById(id: Long): Member? {
        //language=sql
        val sql = "select from * where id=?"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        return try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setLong(1, id)
            rs = pstmt.executeQuery()
            if (rs.next()) {
                Member(rs.getLong("id"), rs.getString("name"))
            } else {
                null
            }
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }
    override fun findByName(name: String): Member? {
        val sql = "select * from member where name = ?"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        return try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, name)
            rs = pstmt.executeQuery()
            if (rs.next()) {
                Member(rs.getLong("id"), rs.getString("name"))
            }else{
                null
            }
        } catch (e: java.lang.Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }
    override fun findAll(): MutableList<Member>{
        val sql = "select * from member"
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null
        return try {
            conn = getConnection()
            pstmt = conn.prepareStatement(sql)
            rs = pstmt.executeQuery()
            val members = mutableListOf<Member>()
            while (rs.next()) {
                members.add(Member(rs.getLong("id"), rs.getString("name")))
            }
            members
        } catch (e: java.lang.Exception) {
            throw IllegalStateException(e)
        } finally {
            close(conn, pstmt, rs)
        }
    }
    private fun getConnection(): Connection {
        return DataSourceUtils.getConnection(dataSource)
    }
    private fun close(conn: Connection?, pstmt: PreparedStatement?, rs: ResultSet?) {
        try { rs?.close() } catch (e: SQLException) { e.printStackTrace() }
        try { pstmt?.close() } catch (e: SQLException) { e.printStackTrace() }
        try { conn?.let { close(it) } } catch (e: SQLException) { e.printStackTrace() }
    }
    @Throws(SQLException::class)
    private fun close(conn: Connection) {
        DataSourceUtils.releaseConnection(conn, dataSource)
    }
}