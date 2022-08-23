package fr.toshi.composeiconviewer

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.navigation.GotoRelatedItem
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.util.NotNullFactory
import com.intellij.psi.PsiElement
import icons.ComposeIcons
import javax.swing.Icon

class GutterComposeIconViewer: RelatedItemLineMarkerProvider() {
    private val regexGetIcons = Regex("(Icons\\n?)\\.([\\w\\n]*)\\.([\\w]*)")

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (regexGetIcons.matchEntire(element.text) == null) {
            return
        }

        val iconUsed = regexGetIcons.matchEntire(element.text)?.groupValues?.get(2) ?: ""
        val icon: Icon = getIconFromString(iconUsed)

        result.add(
            getResult(element, icon)
        )
    }

    private fun getResult(element: PsiElement, icon: Icon): RelatedItemLineMarkerInfo<PsiElement> {
        return object : RelatedItemLineMarkerInfo<PsiElement>(
            element,
            element.textRange,
            icon,
            null,
            null,
            GutterIconRenderer.Alignment.CENTER,
            NotNullFactory<List<GotoRelatedItem>> { emptyList() }
        ) { }
    }

    private fun getIconFromString(icon: String): Icon {
        return ComposeIcons.getIcon(icon)
    }
}