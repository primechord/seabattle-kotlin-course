import models.BattleField
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BattleField::class])
class SpringBootTest {

    // @Autowired
    lateinit var battleField: BattleField

    @Test
    fun rename_me() {
        battleField.getFieldState()
    }
}