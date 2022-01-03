import java.nio.file.Files
import kotlin.io.path.Path

private const val MAIN = "userdata/sea-battle.db"
private const val BACKUP = "userdata/sea-battle-backup.db"

fun backupUserdata() {
    if (Files.exists(Path(MAIN))) {
        Files.deleteIfExists(Path(BACKUP))
        Files.copy(Path(MAIN), Path(BACKUP))
    }
}

fun restoreUserdata() {
    if (Files.exists(Path(BACKUP))) {
        Files.deleteIfExists(Path(MAIN))
        Files.copy(Path(BACKUP), Path(MAIN))
    }
}

fun createDirectory() {
    val userdata = Path("userdata")
    if (!Files.isDirectory(userdata)) Files.createDirectory(userdata)
}
