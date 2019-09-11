package com.vogella.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

import groovy.transform.CompileStatic

class Template extends DefaultTask {

	private String projectName;

	public Template() {
		description = 'Creates a template for vogella tutorials'
		group = 'Documentation'
	}

	@TaskAction
	def createTemplate() {
		if (this.projectName != null) {
			new File("${projectName}/img").mkdirs()
			new File("${projectName}/res").mkdirs()

			createArticleFile()
			createResourcesFile()
			createOverviewFile()

			addIncludeToSettings()
		} else if(project.projectDir.list().length <= 0) {
			this.projectName = project.projectDir.name;
			new File('img').mkdir()
			new File('res').mkdir()

			createArticleFile()
			createResourcesFile()
			createOverviewFile()
		} else {
			throw new GradleException("The ${project.projectDir} directory, which should be initialized, must be empty")
		}
	}

	@Option(option = 'name', description = 'Specify a name for the new project')
	void setFile(final String projectName) {
		this.projectName = projectName;
	}

	def createArticleFile() {
		def date = new Date();
		def now = new java.text.SimpleDateFormat("dd.MM.yyyy").format(date)
		def year = new java.text.SimpleDateFormat("yyyy").format(date)
		def userName = project.hasProperty('userName') ? String.valueOf(project.property('userName')) : ''
		project.file("${projectName}/001_article.adoc") << """= ${projectName} - Tutorial
:toc:
:linkcss:
:sectnums:
:experimental:
:icons:
:imagesdir: ./img
${userName}(c) ${year} vogella GmbH
Version 0.1, ${now}
:docinfodir: ../
:vgwort: ${projectName}

[abstract]
This tutorial contains notes about ${projectName}.

include::010_overview.adoc[]
include::../web/advertisement/inBetween01.adoc[]

include::../web/advertisement/inBetween02.adoc[]
include::008_resourceslocal.adoc[]
include::../copyright.adoc[]


"""
	}
	def createResourcesFile() {
		project.file("${projectName}/008_resourceslocal.adoc") << """== ${projectName} resources

"""
	}
	def createOverviewFile() {
		project.file("${projectName}/010_overview.adoc") << """== ${projectName}

"""
	}

	def addIncludeToSettings() {
		def settingsFile = project.file('settings.gradle')
		if(settingsFile) {
			settingsFile << """
include '${projectName}'"""
		} else {
			throw new GradleException("The settings.gradle file does not exist.")
		}
	}
}
