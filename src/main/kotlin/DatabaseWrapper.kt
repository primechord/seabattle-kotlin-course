import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseWrapper {
    private val connect by lazy {
        Database.connect("jdbc:sqlite:userdata/sea-battle.db", driver = "org.sqlite.JDBC")
    }

    fun <T> wrap(statement: Transaction.() -> T) {
        connect
        transaction {
            addLogger(StdOutSqlLogger)
            this.statement()
        }
    }
}
