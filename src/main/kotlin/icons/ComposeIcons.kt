package icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object ComposeIcons {
    fun getIcon(icon: String): Icon {
        return IconLoader.getIcon("/icons/$icon.png", ComposeIcons::class.java)
    }
}