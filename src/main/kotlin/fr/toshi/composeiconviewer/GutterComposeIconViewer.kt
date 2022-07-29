package fr.toshi.composeiconviewer

import com.intellij.codeHighlighting.Pass
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.navigation.GotoRelatedItem
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.util.NotNullFactory
import com.intellij.psi.PsiElement
import icons.ComposeIcons
import javax.swing.Icon

class GutterComposeIconViewer: RelatedItemLineMarkerProvider() {
    private val regexGetIcons = Regex("(Icons)\\.(.+)\\.([^,)]*)")
    private val ideaVersion = ApplicationInfo.getInstance().build.baselineVersion

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (regexGetIcons.matchEntire(element.text) == null) {
            return
        }

        val iconUsed = regexGetIcons.matchEntire(element.text)?.value?.split(".")?.get(2) ?: ""

        val icon: Icon = getIconFromString(iconUsed)
        /*val logger = Logger.getInstance(this.javaClass)
        logger.error("Icon searched : $iconUsed, Icon got : $icon")*/

        result.add(
            getResult(element, icon)
        )
    }

    private fun getResult(element: PsiElement, icon: Icon): RelatedItemLineMarkerInfo<PsiElement> {
        /* Backward compatibility with older versions of IDEA */
        return if (ideaVersion < 202) {
            object : RelatedItemLineMarkerInfo<PsiElement>(
                element,
                element.textRange,
                icon,
                Pass.UPDATE_ALL,
                null,
                null,
                GutterIconRenderer.Alignment.CENTER,
                emptyList()
            ) { }
        } else {
            object : RelatedItemLineMarkerInfo<PsiElement>(
                element,
                element.textRange,
                icon,
                null,
                null,
                GutterIconRenderer.Alignment.CENTER,
                NotNullFactory<List<GotoRelatedItem>> { emptyList() }
            ) { }
        }
    }

    private fun getIconFromString(icon: String): Icon {
        return ComposeIcons.getIcon(icon)
    }
}