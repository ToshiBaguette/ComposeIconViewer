package fr.toshi.composeiconviewer.services

import com.intellij.openapi.project.Project
import fr.toshi.composeiconviewer.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
