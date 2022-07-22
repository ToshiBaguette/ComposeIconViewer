package com.github.toshibane.composeiconviewer.services

import com.intellij.openapi.project.Project
import com.github.toshibane.composeiconviewer.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
