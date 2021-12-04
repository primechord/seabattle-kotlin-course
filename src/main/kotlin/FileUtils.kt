import java.nio.file.Files
import kotlin.io.path.Path

private const val main = "userdata/sea-battle.db"
private const val backup = "userdata/sea-battle-backup.db"

fun backupUserdata() {
    if (Files.exists(Path(main))) {
        Files.deleteIfExists(Path(backup))
        Files.copy(Path(main), Path(backup))
    }
}

fun restoreUserdata() {
    if (Files.exists(Path(backup))) {
        Files.deleteIfExists(Path(main))
        Files.copy(Path(backup), Path(main))
    }
}

fun createDirectory() {
    val userdata = Path("userdata")
    if (!Files.isDirectory(userdata)) Files.createDirectory(userdata)
}