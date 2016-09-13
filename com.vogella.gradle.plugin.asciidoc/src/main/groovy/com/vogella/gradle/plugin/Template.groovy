package com.vogella.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class Template extends DefaultTask {

	@TaskAction
	def createTemplate() {
		if(project.projectDir.list().length <= 0) {
			new File('img').mkdir()
			new File('res').mkdir()

			createArticleFile()
			createResourcesFile()
			createOverviewFile()
		}else {
			throw new GradleException("The ${project.projectDir} directory, which should be initialized, must be empty")
		}
	}

	def createArticleFile() {
		def date = new Date();
		def now = new java.text.SimpleDateFormat("dd.MM.yyyy").format(date)
		def year = new java.text.SimpleDateFormat("yyyy").format(date)
		project.file('001_article.adoc') << """= ${project.projectDir.name} - Tutorial
:linkcss:
:sectnums:
:experimental:
:icons:
:imagesdir: ./img
(c) ${year} vogella GmbH
Version 0.1, ${now}
:docinfodir: ../
:vgwort: ${project.projectDir.name}

[abstract]
This tutorial contains notes about ${project.projectDir.name}.
				
include::010_overview.adoc[]
include::../web/advertisement/inBetween01.adoc[]
include::../web/advertisement/inBetween02.adoc[]
include::../support.adoc[]
include::008_resourceslocal.adoc[]
include::../copyright.adoc[]	


"""
	}
	def createResourcesFile() {
		project.file('008_resourceslocal.adoc') << """== ${project.projectDir.name} resources

"""
	}
	def createOverviewFile() {
		project.file('010_overview.adoc') << """== ${project.projectDir.name}

"""
	}
}
